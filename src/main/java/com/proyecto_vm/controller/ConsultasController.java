
package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultasController {
    @GetMapping("/consultas")
    public String mostrarConsultas(Model model) {
        List<Doctor> doctores = List.of(
            new Doctor(1L, "Juan Pérez", "Cardiología", 10, 55000, 5, 1, "8888-5555", "L-V 8am-4pm", "Especialista en corazón"),
            new Doctor(2L, "Ana Gómez", "Neurología", 7, 60000, 3, 1, "8888-6666", "L-V 9am-5pm", "Experta en sistema nervioso"),
            new Doctor(3L, "Luis Rojas", "Dermatología", 5, 40000, 4, 1, "8888-7777", "L-S 8am-2pm", "Cuidado de piel y alergias")
        );
        
        model.addAttribute("doctores", doctores);
        return "consultas";
    }
}
