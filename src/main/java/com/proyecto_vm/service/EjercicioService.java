package com.proyecto_vm.service;

import com.proyecto_vm.domain.Ejercicio;
import com.proyecto_vm.repository.EjercicioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Transactional(readOnly = true)
    public List<Ejercicio> getEjercicios() {
        return ejercicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Ejercicio> getEjercicio(Long idEjercicio) {
        return ejercicioRepository.findById(idEjercicio);
    }

    @Transactional
    public void save(Ejercicio ejercicio) {
        ejercicioRepository.save(ejercicio);
    }

    @Transactional
    public void delete(Long idEjercicio) {
        ejercicioRepository.deleteById(idEjercicio);
    }
    
}