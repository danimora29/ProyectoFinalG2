package com.proyecto_vm.controller;

import com.proyecto_vm.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String correo,
            @RequestParam String contraseña,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (authService.autenticar(correo, contraseña, session)) {
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("error", "Correo o contraseña incorrectos");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al autenticar: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/registro/nuevo")
    public String mostrarRegistro() {
        return "registro/nuevo";
    }

    @PostMapping("/registro/nuevo")
    public String procesarRegistro(
            @RequestParam String correo,
            @RequestParam String contraseña,
            @RequestParam String confirmarContraseña,
            RedirectAttributes redirectAttributes) {
        
        if (correo == null || correo.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El correo es obligatorio");
            return "redirect:/registro/nuevo";
        }

        if (contraseña == null || contraseña.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            return "redirect:/registro/nuevo";
        }

        if (!contraseña.equals(confirmarContraseña)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro/nuevo";
        }

        if (authService.registrar(correo, contraseña)) {
            redirectAttributes.addFlashAttribute("exito", "Usuario registrado correctamente. Por favor inicia sesión.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "El correo ya está registrado");
            return "redirect:/registro/nuevo";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        authService.cerrarSesion(session);
        return "redirect:/login";
    }
    
    @GetMapping("/admin/generar-hash")
    public String generarHash(@RequestParam String password, Model model) {
        String hash = authService.generarHash(password);
        model.addAttribute("password", password);
        model.addAttribute("hash", hash);
        return "hash-result";
    }
}