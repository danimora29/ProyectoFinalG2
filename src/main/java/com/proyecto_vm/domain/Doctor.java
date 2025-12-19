package com.proyecto_vm.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDoctor;

    private String nombre;
    private String especialidad;
    private Integer experiencia;
    private Double tarifa;
    private Integer disponibilidad;
    private Integer estado;
    private String contacto;
    private String horario;
    private String descripcion;
    private String pacientes;
    private String telefono;
    private String correo;
    private String rutaImagen;
    private Boolean activo;
}