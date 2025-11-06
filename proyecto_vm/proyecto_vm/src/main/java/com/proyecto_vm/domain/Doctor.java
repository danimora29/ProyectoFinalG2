
package com.proyecto_vm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor")

public class Doctor implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especialidad;
    private int experiencia;
    private double tarifaConsulta;
    private int disponibilidad;
    private int estado;
    private String contacto;
    private String horario;
    private String descripcion;
    private int pacientes;
    
    public Doctor() {}
    
     public Doctor(Long id, String nombre, String especialidad, int experiencia, 
             double tarifaConsulta, int disponibilidad, int estado, String contacto, 
             String horario, String descripcion, int pacientes) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
        this.tarifaConsulta = tarifaConsulta;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
        this.contacto = contacto;
        this.horario = horario;
        this.descripcion = descripcion;
        this.pacientes = pacientes;
    }
}
