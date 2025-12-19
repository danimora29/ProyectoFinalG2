package com.proyecto_vm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ejercicio")
@NoArgsConstructor
@AllArgsConstructor
public class Ejercicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "duracion")
    private int duracionMinutos;

    private Double precio;
}