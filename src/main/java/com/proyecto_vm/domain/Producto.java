package com.proyecto_vm.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "producto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include 
    private Integer idProducto;
    
    @Column(unique = true, nullable = false, length = 50)
    @NotNull(message="La descripcion no puede ser vacia")
    @Size(max = 50, message="La descripcion no puuede contener mas de 50 caracteres")
    private String descripcion;
    
    @Column(columnDefinition="TEXT")
    private String detalle;
    
    @Column(precision = 12, scale =2)
    @NotNull(message="El precio debe de estar definido")
    @DecimalMin (value="0.00", inclusive=true, message="El precio debe ser mayor o igual a 0. 00")
    private BigDecimal precio;
    
    @NotNull(message="Las existencias deben ser mayor o igual a 0")
    @Min(value=0, message="las existencias deben ser mayor o igual a 0")
    private Integer existencias;
    
    @Column(length=1024)
    @Size(max = 1024)
    private String rutaImagen;
    private boolean activo;
    
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
}