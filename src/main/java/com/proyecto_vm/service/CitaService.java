package com.proyecto_vm.service;

import com.proyecto_vm.domain.Cita;
import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.domain.Usuario;
import com.proyecto_vm.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private UsuarioService usuarioService; 

    @Transactional
    public Cita crearYRegistrarCita(Integer idDoctor, Integer idUsuario, LocalDate fecha, LocalTime hora) {
        
        Doctor doctor = doctorService.getDoctor(idDoctor)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado."));
        
        Usuario usuario = usuarioService.getUsuario(idUsuario)
                .orElseThrow(() -> new NoSuchElementException("Usuario con ID " + idUsuario + " no encontrado."));


        if (doctor.getDisponibilidad() == null || doctor.getDisponibilidad() <= 0) {
            throw new IllegalStateException("El doctor no tiene disponibilidad restante.");
        }

        Cita cita = new Cita();
        cita.setDoctor(doctor);
        cita.setUsuario(usuario); 
        cita.setFechaCita(fecha);
        cita.setHoraCita(hora);
        cita.setTarifaAcordada(doctor.getTarifa());
        cita.setEstadoCita("CONFIRMADA");
        
        cita = citaRepository.save(cita);

        doctor.setDisponibilidad(doctor.getDisponibilidad() - 1);
        doctorService.save(doctor);

        return cita;
    }
}