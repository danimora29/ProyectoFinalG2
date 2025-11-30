package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    
}