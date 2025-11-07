package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctores")
    public String listarDoctores(Model model) {
        var doctores = doctorService.getDoctores();
        model.addAttribute("doctores", doctores);
        return "doctores";
    }

    @GetMapping("/doctores/nuevo")
    public String nuevoDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctores/modifica"; 
    }

    @PostMapping("/doctores/guardar")
    public String guardarDoctor(Doctor doctor, RedirectAttributes redirectAttributes) {
        doctorService.save(doctor);
        
        redirectAttributes.addFlashAttribute("toastType", "success");
        redirectAttributes.addFlashAttribute("toastMessage", "Registro actualizado correctamente"); 
        
        return "redirect:/doctores";
    }

    @GetMapping("/doctores/modificar/{idDoctor}")
    public String modificarDoctor(@PathVariable("idDoctor") Integer idDoctor, Model model) {
        var doctorOpt = doctorService.getDoctor(idDoctor);
        if (doctorOpt.isPresent()) {
            model.addAttribute("doctor", doctorOpt.get());
        }
        return "doctores/modifica"; 
    }
    
    @GetMapping("/doctores/eliminar/{idDoctor}")
    public String eliminarDoctor(@PathVariable("idDoctor") Integer idDoctor, RedirectAttributes redirectAttributes) {
        try {
            doctorService.delete(idDoctor);
            redirectAttributes.addFlashAttribute("toastType", "success");
            redirectAttributes.addFlashAttribute("toastMessage", "Registro eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastType", "error");
            redirectAttributes.addFlashAttribute("toastMessage", "Error al intentar eliminar el doctor.");
        }
        
        return "redirect:/doctores";
    }
}