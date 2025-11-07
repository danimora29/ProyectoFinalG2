package com.proyecto_vm.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 *
 * @author Anthony
 */
@Data
@Entity
@Table(name="libro")
@NoArgsConstructor 
@AllArgsConstructor 
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLibro;
    
    private String titulo;
    private String autor;
    
    @Column(unique = true, nullable =false, length=50)
    @NotNull
    @Size(max = 50)
    private String descripcion;
    private double precio;
    
    @Column(length=1024)
    @Size(max = 1024)
    private String rutaImagen;
    private Boolean activo;
}