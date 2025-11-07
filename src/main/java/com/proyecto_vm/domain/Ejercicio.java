package com.proyecto_vm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Ejercicio {
    
    private Long id;
    private String nombre;
    private int duracionMinutos;

}