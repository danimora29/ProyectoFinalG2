package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Alimento;
import com.proyecto_vm.service.AlimentoService;
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
public class AlimentacionController {

    @Autowired
    private AlimentoService alimentoService;
    
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/alimentacion")
    public String listarAlimentos(Model model) {
        var alimentos = alimentoService.getAlimentos();
        model.addAttribute("alimentos", alimentos);
        return "alimentacion/listado";
    }

    @GetMapping("/alimentacion/nuevo")
    public String nuevoAlimento(Model model) {
        model.addAttribute("alimento", new Alimento());
        return "alimentacion/modifica";
    }

    @PostMapping("/alimentacion/guardar")
    public String guardarAlimento(Alimento alimento, RedirectAttributes redirectAttributes, Locale locale) {
        alimentoService.save(alimento);

        String toastType = "success";
        String mensajeKey = (alimento.getId() == null) ? "mensaje.creado" : "mensaje.actualizado";
        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/alimentacion";
    }

    @GetMapping("/alimentacion/modificar/{id}")
    public String modificarAlimento(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Alimento> alimentoOpt = alimentoService.getAlimento(id);

        if (alimentoOpt.isPresent()) {
            model.addAttribute("alimento", alimentoOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("toastType", "error");
            redirectAttributes.addFlashAttribute("toastMessage", messageSource.getMessage("alimentacion.error01", null, locale)); 
            return "redirect:/alimentacion";
        }
        return "alimentacion/modifica";
    }

    @PostMapping("/alimentacion/eliminar")
    public String eliminarAlimento(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String mensajeKey;
        String toastType;

        try {
            alimentoService.delete(id);
            toastType = "success";
            mensajeKey = "mensaje.eliminado";
        } catch (Exception e) {
            toastType = "error";
            mensajeKey = "mensaje.error";
        }

        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/alimentacion";
    }
}