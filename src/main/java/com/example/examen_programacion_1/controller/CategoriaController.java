package com.example.examen_programacion_1.controller;

import com.example.examen_programacion_1.model.Categoria;
import com.example.examen_programacion_1.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarCategorias(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "") String search,
                                   Model model) {
        Page<Categoria> categoriasPage = categoriaService.obtenerCategoriasPaginadas(page, size, search);
        model.addAttribute("categorias", categoriasPage.getContent());
        model.addAttribute("currentPage", categoriasPage.getNumber());
        model.addAttribute("totalPages", categoriasPage.getTotalPages());
        model.addAttribute("totalItems", categoriasPage.getTotalElements());
        model.addAttribute("search", search);

        if (categoriasPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, categoriasPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "categorias/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categorias/formulario";
        }
        categoriaService.guardarCategoria(categoria);
        redirectAttributes.addFlashAttribute("message", "Categoría guardada exitosamente!");
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Categoria> categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "categorias/formulario";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Categoría no encontrada.");
            return "redirect:/categorias";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminarCategoria(id);
            redirectAttributes.addFlashAttribute("message", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se puede eliminar la categoría porque tiene productos asociados.");
        }
        return "redirect:/categorias";
    }
}