package com.example.examen_programacion_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirectToProductos() {
        // Redirige al navegador a la URL /productos
        return "redirect:/productos";
    }

    /*
    // Opcional: Si quieres una página de inicio HTML específica (ej. index.html)
    // en lugar de redirigir directamente, puedes hacer esto:
    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Esto buscaría src/main/resources/templates/index.html
    }
    */
}