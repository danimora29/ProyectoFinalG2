package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.service.DoctorService;
import com.proyecto_vm.service.LibroService;
import com.proyecto_vm.service.EjercicioService;
import com.proyecto_vm.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ConsultasController {

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private LibroService libroService;

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/consultas")
    public String mostrarConsultas(Model model) {
        var doctores = doctorService.getDoctores();
        var libros = libroService.getLibro(true);
        var ejercicios = ejercicioService.getEjercicios();
        var alimentos = alimentoService.getAlimentos();

        model.addAttribute("doctores", doctores);
        model.addAttribute("libros", libros);
        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("alimentos", alimentos);
        
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("consultas", List.of()); 
        
        return "consultas";
    }
}