package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Factura;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturaRepository extends JpaRepository<Factura, Integer>{
    @Query("SELECT f FROM Factura f " +
           "LEFT JOIN FETCH f.usuario u " + 
           "LEFT JOIN FETCH f.ventas v " +
           "WHERE f.idFactura = :idFactura")
    Optional<Factura> findByIdFacturaConDetalle(@Param("idFactura") Integer idFactura);
}