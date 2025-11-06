package com.proyecto_vm.service;

import com.proyecto_vm.repository.LibroRepository;
import com.proyecto_vm.domain.Libro;
import jakarta.validation.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Anthony
 */
@Service
public class LibroService {

    @Autowired

    private LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> getLibro(boolean activo) {
        if (activo) {
            return libroRepository.findByActivoTrue();
        }
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Libro> getLibro(Integer idLibro) {

        return libroRepository.findById(idLibro);
    }

    @Transactional
    public void delete(Integer idLibro) {

        if (!libroRepository.existsById(idLibro)) {
            throw new IllegalArgumentException("NO existe un libro con el id: " + idLibro);
        }
        try {
            libroRepository.deleteById(idLibro);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el libro: " + idLibro + e);
        }

    }

    @Transactional
    public void save(Libro libro, MultipartFile imagenFile) {
        if (imagenFile != null && !imagenFile.isEmpty()) {
            
        

        libroRepository.save(libro);
    }

}
}