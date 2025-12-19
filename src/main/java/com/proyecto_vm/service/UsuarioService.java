package com.proyecto_vm.service;

import com.proyecto_vm.domain.Usuario;
import com.proyecto_vm.domain.Rol;
import com.proyecto_vm.repository.UsuarioRepository;
import com.proyecto_vm.repository.RolRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> 1. INTENTO DE LOGIN RECIBIDO. Username/Correo: [" + username + "]");

        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> {
                    System.out.println(">>> 2. ERROR: No se encontró usuario con correo: " + username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        System.out.println(">>> 2. USUARIO ENCONTRADO EN BD. ID: " + usuario.getIdUsuario());
        System.out.println(">>> 3. CONTRASEÑA EN BD: [" + usuario.getPassword() + "]");
        System.out.println(">>> 4. ¿ESTÁ ACTIVO?: " + usuario.isActivo());

        if (!usuario.isActivo()) {
            System.out.println(">>> ERROR: Usuario inactivo.");
            throw new UsernameNotFoundException("El usuario no está activo");
        }

        var roles = new ArrayList<GrantedAuthority>();
        if (usuario.getRoles() != null) {
            System.out.println(">>> 5. ROLES ENCONTRADOS: " + usuario.getRoles().size());
            for (Rol rol : usuario.getRoles()) {
                System.out.println("   -> ROL: " + rol.getNombre());
                roles.add(new SimpleGrantedAuthority(rol.getNombre()));
            }
        } else {
            System.out.println(">>> 5. ALERTA: Lista de roles es NULL.");
        }

        System.out.println(">>> 6. RETORNANDO USERDETAILS A SPRING SECURITY...");
        return new User(usuario.getCorreo(), usuario.getPassword(), roles);
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios(boolean activo) {
        if (activo) {
            return usuarioRepository.findByActivoTrue();
        }
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    
    @Transactional
    public void save(Usuario usuario, boolean encriptaClave) {
        boolean esNuevo = (usuario.getIdUsuario() == null);
        
        Optional<Usuario> existente = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existente.isPresent() && (esNuevo || !existente.get().getIdUsuario().equals(usuario.getIdUsuario()))) {
             throw new DataIntegrityViolationException("El correo ya está en uso por otro usuario.");
        }

        if (esNuevo) {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                throw new IllegalArgumentException("La contraseña es obligatoria.");
            }
            usuario.setPassword(usuario.getPassword());
        } else {
            Usuario usuarioActual = usuarioRepository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(usuarioActual.getPassword());
            } else {
                usuario.setPassword(usuario.getPassword());
            }
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        if (esNuevo) {
            asignarRolAUser(usuarioGuardado.getCorreo(), "ROLE_USER");
        }
    }
    
    @Transactional
    public void delete(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("El usuario no existe.");
        }
        usuarioRepository.deleteById(idUsuario);
    }

    @Transactional
    public void asignarRolAUser(String correoUsuario, String nombreRol) {
        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado."));

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario); 
    }
}