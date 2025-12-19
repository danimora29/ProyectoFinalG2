package com.proyecto_vm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idEntidad; 
    private String tipoItem; 
    private String nombre; 
    
    private Double precioUnitario; 
    private int cantidad; 
    private Double precioHistorico; 
    
    private LocalDate fechaCita;
    private LocalTime horaCita;
    
    public Double getSubTotal() {
        if (precioUnitario == null) return 0.0;
        return precioUnitario * cantidad;
    }
}