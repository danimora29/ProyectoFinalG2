package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Optional<Rol> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);
}
