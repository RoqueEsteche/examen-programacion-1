package com.example.examen_programacion_1.controller;

import com.example.examen_programacion_1.model.Usuario;
import com.example.examen_programacion_1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Nombre de usuario o contraseña inválidos.");
        }
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión exitosamente.");
        }
        return "login"; // Nombre del archivo HTML para el login
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Nombre del archivo HTML para el registro
    }

    @PostMapping("/registro")
    public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registro"; // Volver al formulario si hay errores de validación
        }

        // Verificar si el username ya existe
        Optional<Usuario> existingUser = usuarioService.buscarPorUsername(usuario.getUsername());
        if (existingUser.isPresent()) {
            model.addAttribute("usernameError", "El nombre de usuario ya está en uso.");
            return "registro";
        }

        // Puedes añadir más validaciones aquí, como la fortaleza de la contraseña o la unicidad del email

        usuarioService.registrarNuevoUsuario(usuario);
        model.addAttribute("successMessage", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "redirect:/login?registered"; // Redirigir al login con un mensaje de éxito
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Página principal después del login
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Página para acceso denegado
    }
}