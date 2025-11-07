package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Ejercicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ejercicios") 
public class EjercicioController {

    private List<Ejercicio> obtenerEjerciciosSimulados() {
        return Arrays.asList(
            new Ejercicio(1L, "Trotar", 30),
            new Ejercicio(2L, "Lagartijas", 10),
            new Ejercicio(3L, "Saltos de Tijera", 5),
            new Ejercicio(4L, "Estiramiento", 15)
        );
    }

    @GetMapping("/listado")
    public String listarEjercicios(Model model) {
        model.addAttribute("ejercicios", obtenerEjerciciosSimulados());
        
        return "ejercicios/listado"; 
    }
}