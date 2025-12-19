package com.proyecto_vm.controller;

import com.proyecto_vm.domain.*;
import com.proyecto_vm.service.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final UsuarioService usuarioService;
    private final FacturaService facturaService; 

    public CarritoController(CarritoService carritoService, UsuarioService usuarioService, FacturaService facturaService) { 
        this.carritoService = carritoService;
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
    }
    
    private void actualizarContadorSesion(HttpSession session) {
        List<Item> carrito = carritoService.obtenerCarrito(session);
        int totalItems = carritoService.contarUnidades(carrito);
        session.setAttribute("itemsEnCarrito", totalItems);
    }

    @GetMapping("/listado")
    public String listado(HttpSession session, org.springframework.ui.Model model) {
        List<Item> items = carritoService.obtenerCarrito(session);
        model.addAttribute("carritoItems", items);
        model.addAttribute("totalCarrito", carritoService.calcularTotal(items));
        actualizarContadorSesion(session); 
        
        return "/carrito/listado";
    }

    @PostMapping("/agregar")
    public String agregar(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("tipoItem") String tipoItem,
            @RequestParam(value = "cantidad", required = false) Integer cantidad,
            @RequestParam(value = "fechaCita", required = false) LocalDate fechaCita,
            @RequestParam(value = "horaCita", required = false) LocalTime horaCita,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        try {
            List<Item> carrito = carritoService.obtenerCarrito(session);
            carritoService.agregarItem(carrito, idEntidad, tipoItem, cantidad, fechaCita, horaCita);
            carritoService.guardarCarrito(session, carrito);
            
            actualizarContadorSesion(session);
            
            redirectAttributes.addFlashAttribute("mensaje", "Ítem agregado con éxito.");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar: " + e.getMessage());
        }
        return "redirect:/"; 
    }

    @GetMapping("/eliminar/{tipoItem}/{idEntidad}")
    public String eliminarItem(
            @PathVariable("idEntidad") Long idEntidad,
            @PathVariable("tipoItem") String tipoItem,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<Item> carrito = carritoService.obtenerCarrito(session);
        carritoService.eliminarItem(carrito, idEntidad, tipoItem);
        carritoService.guardarCarrito(session, carrito);
        
        actualizarContadorSesion(session);

        redirectAttributes.addFlashAttribute("mensaje", "Ítem eliminado del carrito.");
        return "redirect:/carrito/listado";
    }

    @PostMapping("/actualizar")
    public String actualizarCantidad(
            @RequestParam("idEntidad") Long idEntidad,
            @RequestParam("tipoItem") String tipoItem,
            @RequestParam("cantidad") int nuevaCantidad,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            List<Item> carrito = carritoService.obtenerCarrito(session);
            
            carritoService.actualizarCantidad(carrito, idEntidad, tipoItem, nuevaCantidad);
            carritoService.guardarCarrito(session, carrito);
            
            actualizarContadorSesion(session);
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada.");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/carrito/listado";
    }

    @GetMapping("/facturar")
    public String facturar(HttpSession session, RedirectAttributes redirectAttributes) {
        
        try {
            List<Item> carrito = carritoService.obtenerCarrito(session);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName(); 
            
            Usuario usuario = usuarioService.getUsuarioPorCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no autenticado"));

            Factura factura = (Factura) carritoService.procesarCompra(carrito, usuario);

            carritoService.limpiarCarrito(session);
            
            actualizarContadorSesion(session);
            
            if (factura != null && factura.getIdFactura() != null) {
                 redirectAttributes.addFlashAttribute("idFactura", factura.getIdFactura());
                 redirectAttributes.addFlashAttribute("mensaje", "Compra procesada con éxito. Factura Nro: " + factura.getIdFactura());
                 return "redirect:/carrito/verFactura";
            } else {
                 redirectAttributes.addFlashAttribute("mensaje", "Citas procesadas con éxito.");
                 return "redirect:/usuario/historial"; 
            }

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la compra: " + e.getMessage());
            return "redirect:/carrito/listado";
        }
    }
    
    @GetMapping("/verFactura")
    public String verFactura(@ModelAttribute("idFactura") Integer idFactura, org.springframework.ui.Model model) {
        if (idFactura == null) {
             return "redirect:/index";
        }
        
        try {
            Factura factura = facturaService.getFacturaConDetalle(idFactura); 
            
            model.addAttribute("factura", factura);
            return "/carrito/verFactura";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Factura no encontrada.");
            return "/errores/errorGenerico";
        }
    }
}