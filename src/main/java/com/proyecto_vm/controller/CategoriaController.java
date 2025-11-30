package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Categoria;
import com.proyecto_vm.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;
import java.util.Optional;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "categoria/listado";
    }

    @GetMapping("/categorias/nuevo")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/modifica";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(Categoria categoria, RedirectAttributes redirectAttributes, Locale locale) {
        categoriaService.save(categoria);

        String toastType = "success";
        String mensajeKey = (categoria.getIdCategoria() == null) ? "mensaje.creado" : "mensaje.actualizado";
        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/categorias";
    }

    @GetMapping("/categorias/modificar/{idCategoria}")
    public String modificarCategoria(@PathVariable("idCategoria") Integer idCategoria, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Categoria> categoriaOpt = categoriaService.getCategoria(idCategoria);
        
        if (categoriaOpt.isPresent()) {
            model.addAttribute("categoria", categoriaOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("toastType", "error");
            redirectAttributes.addFlashAttribute("toastMessage", messageSource.getMessage("categoria.error01", null, locale));
            return "redirect:/categorias";
        }
        return "categoria/modifica";
    }

    @PostMapping("/categorias/eliminar")
    public String eliminarCategoria(@RequestParam Integer idCategoria, RedirectAttributes redirectAttributes, Locale locale) {
        String mensajeKey;
        String toastType;

        try {
            categoriaService.delete(idCategoria);
            toastType = "success";
            mensajeKey = "mensaje.eliminado";
        } catch (Exception e) {
            toastType = "error";
            mensajeKey = "categoria.error03"; 
        }

        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/categorias";
    }
}