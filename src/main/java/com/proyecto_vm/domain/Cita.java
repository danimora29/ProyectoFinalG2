package com.proyecto_vm.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "cita")
@NoArgsConstructor
@AllArgsConstructor
public class Cita implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "id_usuario") 
    private Usuario usuario;

    private LocalDate fechaCita;
    private LocalTime horaCita;
    private Double tarifaAcordada;
    private String estadoCita;
}