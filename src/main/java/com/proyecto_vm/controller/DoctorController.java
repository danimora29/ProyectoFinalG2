
package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DoctorController {
    private List<Doctor> doctores = Arrays.asList(
        new Doctor(1L, "Juan", "Cardiología", 10, 50.0, 5, 120, "Disponible", "", "555-1953"),
        new Doctor(2L, "María", "Pediatría", 8, 40.0, 3, 80, "Ocupado", "", "555-5680")
    );


    @GetMapping("/doctores")
    public String listarDoctores(Model model) {
        model.addAttribute("doctores", doctores);
        return "doctores";
    }

    @GetMapping("/doctores/{id}")
    public String verPerfil(@PathVariable Long id, Model model) {
        Doctor doctor = doctores.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("doctor", doctor);
        return "perfil-doctor";
    }
}
