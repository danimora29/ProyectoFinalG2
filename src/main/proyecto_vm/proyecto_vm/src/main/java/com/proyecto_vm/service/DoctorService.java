
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
    public List<Doctor> getDoctores(boolean activos) {
       
        return doctorRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Optional<Doctor> getDoctor(Integer idDoctor) {
        return doctorRepository.findById(idDoctor);
    }

    // 🔹 Guardar o actualizar un doctor
    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    
    @Transactional
    public void delete(Integer idDoctor) {
        if (!doctorRepository.existsById(idDoctor)) {
            throw new IllegalArgumentException("No existe un doctor con el id: " + idDoctor);
        }
        try {
            doctorRepository.deleteById(idDoctor);
        } catch (Exception e) {
            throw new IllegalStateException("No se puede eliminar el doctor con id " + idDoctor + " porque tiene datos asociados.");
        }
    }

 
    @Transactional(readOnly = true)
    public List<Doctor> consultaDerivadaDisponibilidad(int disponibilidadInf, int disponibilidadSup) {
        return doctorRepository.findByDisponibilidadBetween(disponibilidadInf, disponibilidadSup);
    }
}


