package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
}