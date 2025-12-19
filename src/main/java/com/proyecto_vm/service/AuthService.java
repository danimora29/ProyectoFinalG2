package com.proyecto_vm.service;

import com.proyecto_vm.domain.Rol;
import com.proyecto_vm.domain.Usuario;
import com.proyecto_vm.repository.RolRepository;
import com.proyecto_vm.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public String generarHash(String contraseña) {
        return passwordEncoder.encode(contraseña);
    }

    public boolean autenticar(String correo, String contraseña, HttpSession session) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            boolean activo = usuario.isActivo();
            
            if (activo && passwordEncoder.matches(contraseña, usuario.getPassword())) {
                session.setAttribute("usuario", usuario);
                session.setAttribute("idUsuario", usuario.getIdUsuario());
                session.setAttribute("correo", usuario.getCorreo());
                
                if (!usuario.getRoles().isEmpty()) {
                    session.setAttribute("rol", usuario.getRoles().iterator().next().getNombre());
                }
                
                return true;
            }
        }
        return false;
    }

    public boolean registrar(String correo, String contraseña) {
        if (usuarioRepository.existsByCorreo(correo)) {
            return false;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setPassword(passwordEncoder.encode(contraseña));
        nuevoUsuario.setActivo(true);

        Optional<Rol> rol = rolRepository.findByNombre("ROLE_USER");
        if (rol.isPresent()) {
            nuevoUsuario.getRoles().add(rol.get());
        }

        try {
            usuarioRepository.save(nuevoUsuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cerrarSesion(HttpSession session) {
        session.invalidate();
    }
}