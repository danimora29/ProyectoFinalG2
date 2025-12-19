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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;


@Controller
@RequestMapping("/doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping({"/", "/listado"})
    public String listado(Model model) {
        var doctores = doctorService.getDoctores();
        model.addAttribute("doctores", doctores);
        model.addAttribute("totalDoctores", doctores.size());

        return "doctores/listado";
    }

    @PostMapping("/guardar")
    public String guardarDoctor(Doctor doctor, @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile, RedirectAttributes redirectAttributes) {
        doctorService.save(doctor);

        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));

        return "redirect:/doctores/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarDoctor(@RequestParam Integer idDoctor, RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "mensaje.eliminado";

        try {
            doctorService.delete(idDoctor);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "doctor.error01";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "doctor.error03";
        }

        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(mensaje, null, Locale.getDefault()));

        return "redirect:/doctores/listado";
    }

    @GetMapping("/modificar/{idDoctor}")
    public String modificarDoctor(@PathVariable("idDoctor") Integer idDoctor,
            RedirectAttributes redirectAttributes, Model model) {

        Optional<Doctor> doctorOpt = doctorService.getDoctor(idDoctor);

        if (doctorOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("doctor.error01", null, Locale.getDefault()));
            return "redirect:/doctores/listado";
        }

        model.addAttribute("doctor", doctorOpt.get());
        return "doctores/modifica";
    }
}