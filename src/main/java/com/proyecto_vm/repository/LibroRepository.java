package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Anthony
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer>{
    public List<Libro> findByActivoTrue();
    
}