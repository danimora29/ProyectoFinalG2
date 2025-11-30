package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    
}