package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ConsultasController {
    @GetMapping("/consultas")
    public String mostrarConsultas(Model model) {
        // La clase Doctor tiene 11 propiedades después del ID.
        // Asumiendo el orden: (ID, nombre, especialidad, experiencia, tarifa, disponibilidad, estado, contacto, horario, descripcion, pacientes)
        List<Doctor> doctores = List.of(
            new Doctor(1, "Juan Pérez", "Cardiología", 10, 55000.0, 5, 1, "8888-5555", "L-V 8am-4pm", "Especialista en corazón", "120"),
            new Doctor(2, "Ana Gómez", "Neurología", 7, 60000.0, 3, 1, "8888-6666", "L-V 9am-5pm", "Experta en sistema nervioso", "80"),
            new Doctor(3, "Luis Rojas", "Dermatología", 5, 40000.0, 4, 1, "8888-7777", "L-S 8am-2pm", "Cuidado de piel y alergias", "150")
        );
        
        model.addAttribute("doctores", doctores);
        return "consultas";
    }
}