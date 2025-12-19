package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    Ruta findByRuta(String ruta);
}
