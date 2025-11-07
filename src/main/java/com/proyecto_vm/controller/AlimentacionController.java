package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Alimento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/alimentacion") 
public class AlimentacionController {

    private List<Alimento> obtenerAlimentosSimulados() {
        return Arrays.asList(
            new Alimento(1L, "Manzana", 95, "Fruta"),
            new Alimento(2L, "Pechuga de Pollo", 165, "Proteína"),
            new Alimento(3L, "Arroz Integral", 216, "Carbohidrato"),
            new Alimento(4L, "Aguacate", 160, "Grasa")
        );
    }

    @GetMapping("/listado")
    public String listarAlimentos(Model model) {
        model.addAttribute("alimentos", obtenerAlimentosSimulados());
        
        return "alimentacion/listado"; 
    }
}