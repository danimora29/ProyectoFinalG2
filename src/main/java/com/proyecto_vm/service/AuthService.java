package com.proyecto_vm.service;

import com.proyecto_vm.domain.Rol;
import com.proyecto_vm.repository.RolRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // Método auxiliar para generar hash (puedes usarlo para actualizar la BD)
    public String generarHash(String contraseña) {
        return passwordEncoder.encode(contraseña);
    }

    public boolean autenticar(String correo, String contraseña, HttpSession session) {
        Optional<Rol> rolOpt = rolRepository.findByCorreo(correo);
        
        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            
            // Verificar que el usuario esté activo (si es null, considerarlo como activo)
            Boolean activo = rol.getActivo();
            if (activo == null) {
                activo = true; // Si es null, considerar como activo
            }
            
            // Verificar contraseña
            if (activo && passwordEncoder.matches(contraseña, rol.getContraseña())) {
                // Guardar información del usuario en la sesión
                session.setAttribute("usuario", rol);
                session.setAttribute("rol", rol.getNombreRol());
                session.setAttribute("correo", rol.getCorreo());
                return true;
            }
        }
        return false;
    }

    public boolean registrar(String correo, String contraseña) {
        // Verificar si el correo ya existe
        if (rolRepository.existsByCorreo(correo)) {
            return false;
        }

        // Crear nuevo usuario con rol "usuario"
        Rol nuevoRol = new Rol();
        nuevoRol.setCorreo(correo);
        nuevoRol.setContraseña(passwordEncoder.encode(contraseña));
        nuevoRol.setNombreRol("usuario");
        nuevoRol.setActivo(true);

        try {
            rolRepository.save(nuevoRol);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cerrarSesion(HttpSession session) {
        session.invalidate();
    }
}
