package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Categoria;
import com.proyecto_vm.domain.Producto;
import com.proyecto_vm.service.CategoriaService;
import com.proyecto_vm.service.ProductoService;
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
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(true);
        
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        
        return "producto/listado";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model) {
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);
        return "producto/modifica";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(Producto producto, RedirectAttributes redirectAttributes, Locale locale) {
        productoService.save(producto);

        String toastType = "success";
        String mensajeKey = (producto.getIdProducto() == null) ? "mensaje.creado" : "mensaje.actualizado";
        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/productos";
    }

    @GetMapping("/productos/modificar/{idProducto}")
    public String modificarProducto(@PathVariable("idProducto") Integer idProducto, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Optional<Producto> productoOpt = productoService.getProducto(idProducto);
        var categorias = categoriaService.getCategorias(true);
        
        if (productoOpt.isPresent()) {
            model.addAttribute("producto", productoOpt.get());
            model.addAttribute("categorias", categorias);
        } else {
            redirectAttributes.addFlashAttribute("toastType", "error");
            redirectAttributes.addFlashAttribute("toastMessage", messageSource.getMessage("producto.error01", null, locale));
            return "redirect:/productos";
        }
        return "producto/modifica";
    }

    @PostMapping("/productos/eliminar")
    public String eliminarProducto(@RequestParam Integer idProducto, RedirectAttributes redirectAttributes, Locale locale) {
        String mensajeKey;
        String toastType;

        try {
            productoService.delete(idProducto);
            toastType = "success";
            mensajeKey = "mensaje.eliminado";
        } catch (Exception e) {
            toastType = "error";
            mensajeKey = "producto.error03"; 
        }

        String mensajeTraducido = messageSource.getMessage(mensajeKey, null, locale);

        redirectAttributes.addFlashAttribute("toastType", toastType);
        redirectAttributes.addFlashAttribute("toastMessage", mensajeTraducido);

        return "redirect:/productos";
    }
}