package com.example.examen_programacion_1.service;

import com.example.examen_programacion_1.model.Rol;
import com.example.examen_programacion_1.model.Usuario;
import com.example.examen_programacion_1.repository.RolRepository;
import com.example.examen_programacion_1.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Asegurarse de que los roles existan al inicio
        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            rolRepository.save(new Rol(null, "ROLE_USER"));
        }
        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            rolRepository.save(new Rol(null, "ROLE_ADMIN"));
        }

        // Crear un usuario admin por defecto si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // Contraseña para admin
            admin.setEmail("admin@example.com");
            Set<Rol> roles = new HashSet<>();
            rolRepository.findByNombre("ROLE_ADMIN").ifPresent(roles::add);
            rolRepository.findByNombre("ROLE_USER").ifPresent(roles::add);
            admin.setRoles(roles);
            usuarioRepository.save(admin);
        }
    }

    @Transactional
    public Usuario registrarNuevoUsuario(Usuario usuario) {
        // Codificar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar el rol por defecto (ROLE_USER)
        Optional<Rol> userRole = rolRepository.findByNombre("ROLE_USER");
        if (userRole.isPresent()) {
            Set<Rol> roles = new HashSet<>();
            roles.add(userRole.get());
            usuario.setRoles(roles);
        } else {
            // Manejar el caso si ROLE_USER no existe (aunque debería crearse en @PostConstruct)
            throw new RuntimeException("El rol ROLE_USER no está configurado en la base de datos.");
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}