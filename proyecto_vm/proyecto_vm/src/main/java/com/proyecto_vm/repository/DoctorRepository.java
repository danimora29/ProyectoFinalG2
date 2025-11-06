package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
 
    
    List<Doctor> findByEstado(int estado);
    
    public List<Doctor> findByDisponibilidadBetween(
            Integer disponilidadInfInteger, int disponibilidadSup
    );
    
}
