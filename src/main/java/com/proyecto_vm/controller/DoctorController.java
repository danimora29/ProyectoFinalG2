package com.proyecto_vm.controller;

import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/doctores/listado")
    public String listado(Model model) {
        model.addAttribute("doctores", doctorService.getDoctores());
        return "doctores/listado";  // <-- la vista
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

    @PostMapping("/doctores/eliminar")
    public String eliminarDoctor(@RequestParam Integer idDoctor, RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "doctor.eliminado";

        try {
            doctorService.delete(idDoctor);

        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "doctor.error01"; // Por ejemplo: no se encontró el doctor

        } catch (Exception e) {
            titulo = "error";
            mensaje = "doctor.error03"; // Error desconocido
        }

        redirectAttributes.addFlashAttribute("titulo", titulo);
        redirectAttributes.addFlashAttribute("mensaje", mensaje);

        return "redirect:/doctores";
    }
}