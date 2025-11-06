package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.service.DoctorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping
public class ConsultasController {
    @Autowired
    private DoctorService doctorService;

    public ConsultasController(DoctorService doctorservice) {
        this.doctorService = doctorservice;
    }

    @GetMapping("/consultas")
    public String mostrarConsultas(Model model) {
        List<Doctor> doctores = List.of(
                new Doctor(1L, "Juan Pérez", "Cardiología", 10, 55000, 5, 1, "8888-5555", "L-V 8am-4pm", "Especialista en corazón", 50),
                new Doctor(2L, "Ana Gómez", "Neurología", 7, 60000, 3, 1, "8888-6666", "L-V 9am-5pm", "Experta en sistema nervioso", 110),
                new Doctor(3L, "Luis Rojas", "Dermatología", 5, 40000, 4, 1, "8888-7777", "L-S 8am-2pm", "Cuidado de piel y alergias", 70)
        );

        model.addAttribute("doctores", doctores);
        return "/consultas";
    }
    @PostMapping("/consultaDerivada")
    public String consultaDerivada(
            @RequestParam("disponibilidadInf") Integer disponilidadInf,
            @RequestParam("disponibilidadSup") Integer disponibilidadSup,
            Model model) {

        var doctor = doctorService.consultaDerivadaDisponibilidad(disponilidadInf, disponibilidadSup);

        model.addAttribute("doctores", doctor);
        model.addAttribute("salarioInf", disponilidadInf);
        model.addAttribute("salarioSup", disponibilidadSup);

        return "/consultas/listado";
    }
}


  