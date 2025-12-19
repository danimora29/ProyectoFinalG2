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
@Table(name = "alimentacion")
@NoArgsConstructor
@AllArgsConstructor
public class Alimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "calorias")
    private int calorias;
    
    @Column(name = "tipo")
    private String tipo;

    private Double precio; 
}