package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Alimento;
import com.proyecto_vm.domain.Ejercicio;
import com.proyecto_vm.domain.Libro;
import com.proyecto_vm.service.AlimentoService;
import com.proyecto_vm.service.EjercicioService;
import com.proyecto_vm.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private LibroService libroService; 

    @Autowired
    private AlimentoService alimentoService; 

    @Autowired
    private EjercicioService ejercicioService; 

    @GetMapping("/")
    public String index(Model model) {
        
        List<Libro> libros = libroService.getLibro(true); 
        model.addAttribute("libros", libros);

        List<Alimento> alimentos = alimentoService.getAlimentos();
        model.addAttribute("alimentos", alimentos);

        List<Ejercicio> ejercicios = ejercicioService.getEjercicios();
        model.addAttribute("ejercicios", ejercicios);

        return "index";
    }
}