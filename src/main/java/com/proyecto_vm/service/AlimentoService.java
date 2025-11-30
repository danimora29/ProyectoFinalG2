package com.proyecto_vm.service;

import com.proyecto_vm.domain.Alimento;
import com.proyecto_vm.repository.AlimentoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Transactional(readOnly = true)
    public List<Alimento> getAlimentos() {
        return alimentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Alimento> getAlimento(Long idAlimento) {
        return alimentoRepository.findById(idAlimento);
    }

    @Transactional
    public void save(Alimento alimento) {
        alimentoRepository.save(alimento);
    }

    @Transactional
    public void delete(Long idAlimento) {
        alimentoRepository.deleteById(idAlimento);
    }
}