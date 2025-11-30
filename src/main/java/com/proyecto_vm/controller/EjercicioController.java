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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import java.util.Locale;
import java.util.Optional;

@Controller
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/ejercicios")
    public String listarEjercicios(Model model) {
        var ejercicios = ejercicioService.getEjercicios();
        model.addAttribute("ejercicios", ejercicios);
        return "ejercicios/listado";
    }

    @GetMapping("/ejercicios/nuevo")
    public String nuevoEjercicio(Model model) {
        model.addAttribute("ejercicio", new Ejercicio());
        return "ejercicios/modifica";
    }

    @PostMapping("/ejercicios/guardar")
    public String guardarEjercicio(Ejercicio ejercicio, RedirectAttributes redirectAttributes, Locale locale) {
        ejercicioService.save(ejercicio);

        String toastType = "success";
        String mensajeKey = (ejercicio.getId() == null) ? "mensaje.creado" : "mensaje.actualizado";
        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/ejercicios";
    }

    @GetMapping("/ejercicios/modificar/{id}")
    public String modificarEjercicio(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Ejercicio> ejercicioOpt = ejercicioService.getEjercicio(id);

        if (ejercicioOpt.isPresent()) {
            model.addAttribute("ejercicio", ejercicioOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("toastType", "error");
            redirectAttributes.addFlashAttribute("toastMessage", messageSource.getMessage("ejercicio.error01", null, locale));
            return "redirect:/ejercicios";
        }
        return "ejercicios/modifica";
    }

    @PostMapping("/ejercicios/eliminar")
    public String eliminarEjercicio(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String mensajeKey;
        String toastType;

        try {
            ejercicioService.delete(id);
            toastType = "success";
            mensajeKey = "mensaje.eliminado";
        } catch (IllegalArgumentException e) {
            toastType = "error";
            mensajeKey = "ejercicio.error01"; 
        } catch (Exception e) {
            toastType = "error";
            mensajeKey = "mensaje.error";
        }

        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/ejercicios";
    }
}