package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Ejercicio;
import com.proyecto_vm.service.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping({"/", "/listado"})
    public String listado(Model model) {
        var ejercicios = ejercicioService.getEjercicios();
        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("totalEjercicios", ejercicios.size());
        model.addAttribute("ejercicio", new Ejercicio());

        return "ejercicios/listado";
    }

    @PostMapping("/guardar")
    public String guardarEjercicio(Ejercicio ejercicio, RedirectAttributes redirectAttributes, Locale locale) {
        ejercicioService.save(ejercicio);

        String titulo = "todoOk";
        String mensajeKey = (ejercicio.getId() == null) ? "mensaje.creado" : "mensaje.actualizado";

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensajeKey, null, locale));

        return "redirect:/ejercicios/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificarEjercicio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Ejercicio> ejercicioOpt = ejercicioService.getEjercicio(id);

        if (ejercicioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("ejercicio.error01", null, locale));
            return "redirect:/ejercicios/listado";
        }
        model.addAttribute("ejercicio", ejercicioOpt.get());
        
        return "ejercicios/modifica";
    }

    @PostMapping("/eliminar")
    public String eliminarEjercicio(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String titulo = "todoOk";
        String mensajeKey = "mensaje.eliminado";

        try {
            ejercicioService.delete(id);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensajeKey = "ejercicio.error01";
        } catch (Exception e) {
            titulo = "error";
            mensajeKey = "mensaje.error";
        }

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensajeKey, null, locale));

        return "redirect:/ejercicios/listado";
    }
}