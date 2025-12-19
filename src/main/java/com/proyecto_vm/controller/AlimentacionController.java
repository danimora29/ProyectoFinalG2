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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/alimentacion")
public class AlimentacionController {

    @Autowired
    private AlimentoService alimentoService;
    
    @Autowired
    private MessageSource messageSource;

    @GetMapping({"/", "/listado"})
    public String listado(Model model) {
        var alimentos = alimentoService.getAlimentos();
        model.addAttribute("alimentos", alimentos);
        model.addAttribute("totalAlimentos", alimentos.size());
        model.addAttribute("alimento", new Alimento());

        return "alimentacion/listado";
    }

    @PostMapping("/guardar")
    public String guardarAlimento(Alimento alimento, RedirectAttributes redirectAttributes, Locale locale) {
        alimentoService.save(alimento);

        String titulo = "todoOk";
        String mensajeKey = (alimento.getId() == null) ? "mensaje.creado" : "mensaje.actualizado";

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensajeKey, null, locale));

        return "redirect:/alimentacion/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAlimento(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Alimento> alimentoOpt = alimentoService.getAlimento(id);

        if (alimentoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("alimentacion.error01", null, locale));
            return "redirect:/alimentacion/listado";
        }
        model.addAttribute("alimento", alimentoOpt.get());
        
        return "alimentacion/modifica";
    }

    @PostMapping("/eliminar")
    public String eliminarAlimento(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        String titulo = "todoOk";
        String mensajeKey = "mensaje.eliminado";

        try {
            alimentoService.delete(id);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensajeKey = "alimentacion.error01";
        } catch (Exception e) {
            titulo = "error";
            mensajeKey = "mensaje.error";
        }

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensajeKey, null, locale));

        return "redirect:/alimentacion/listado";
    }
}