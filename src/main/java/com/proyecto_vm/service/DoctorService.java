package com.proyecto_vm.service;

import com.proyecto_vm.domain.Doctor;
import com.proyecto_vm.repository.DoctorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public List<Doctor> getDoctores() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Doctor> getDoctor(Integer idDoctor) {
        return doctorRepository.findById(idDoctor);
    }

    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    public void delete(Integer idDoctor) {
        doctorRepository.deleteById(idDoctor);
    }
}