package com.proyecto_vm.repository;

import com.proyecto_vm.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreoAndActivoTrue(String correo);

    List<Usuario> findByActivoTrue();

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCorreoOrCorreo(String correo1, String correo2);

    boolean existsByCorreo(String correo);
}