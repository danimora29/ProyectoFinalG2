package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Libro;
import com.proyecto_vm.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Anthony
 */
@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var libro = libroService.getLibro(false);
        model.addAttribute("libros", libro);
        model.addAttribute("totalLibros", libro.size());

        return "libro/listado";
    }
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(Libro libro, @RequestParam MultipartFile imagenFile, RedirectAttributes redirectAttributes) {
        libroService.save(libro, imagenFile);
        redirectAttributes.addFlashAttribute("todoOK",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/libro/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idLibro, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";
        try {
            libroService.delete(idLibro);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "libro.error01";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "libro.error03";

        }

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/libro/listado";
    }
    
    @GetMapping("/modificar/{idLibro}")
    public String modificar (@PathVariable("idLibro") Integer idLibro,
            RedirectAttributes redirectAttributes, Model model){
        Optional<Libro> libroOpt = libroService.getLibro(idLibro);
        if (libroOpt.isEmpty()){
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("libro.error01", null, Locale.getDefault()));
            return "redirect:/libro/listado";
        }
        
        model.addAttribute("libro", libroOpt.get());
        return "/libro/modifica";
    }
}
