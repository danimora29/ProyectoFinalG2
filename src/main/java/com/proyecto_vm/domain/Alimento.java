package com.proyecto_vm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Alimento {
    
    private Long id;
    private String nombre;
    private int calorias;
    private String tipo;
}