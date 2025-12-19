package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Cita;
import com.proyecto_vm.service.DoctorService;
import com.proyecto_vm.service.CitaService;
import com.proyecto_vm.service.LibroService;
import com.proyecto_vm.service.EjercicioService;
import com.proyecto_vm.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class IndexController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private LibroService libroService;
    
    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        var doctores = doctorService.getDoctores().stream()
                .filter(d -> Boolean.TRUE.equals(d.getActivo()) && d.getDisponibilidad() > 0)
                .toList();
        
        model.addAttribute("doctores", doctores);
        model.addAttribute("libros", libroService.getLibro(true));
        model.addAttribute("ejercicios", ejercicioService.getEjercicios());
        model.addAttribute("alimentos", alimentoService.getAlimentos());
        
        return "index";
    }

    @PostMapping("/carrito/agregarCita")
    public String agregarCitaAlCarrito(
            @RequestParam("idDoctor") Integer idDoctor,
            @RequestParam("fechaCita") LocalDate fechaCita,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para reservar una cita.");
            return "redirect:/login";
        }

        Integer idUsuarioSimulado = 1; 
        
        try {
            LocalTime horaCita = LocalTime.of(8, 0); 
            
            Cita cita = citaService.crearYRegistrarCita(idDoctor, idUsuarioSimulado, fechaCita, horaCita);
            
            redirectAttributes.addFlashAttribute("todoOk", 
                    "Cita con " + cita.getDoctor().getNombre() + " el " + fechaCita + 
                    " agregada al carrito (ID: " + cita.getIdCita() + "). Dispo. reducida.");
            
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al registrar la cita.");
        }

        return "redirect:/";
    }
}