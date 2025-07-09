package com.example.examen_programacion_1.controller;

import com.example.examen_programacion_1.model.Categoria;
import com.example.examen_programacion_1.model.Producto;
import com.example.examen_programacion_1.service.CategoriaService;
import com.example.examen_programacion_1.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "") String search,
                                  Model model) {
        Page<Producto> productosPage = productoService.obtenerProductosPaginados(page, size, search);
        model.addAttribute("productos", productosPage.getContent());
        model.addAttribute("currentPage", productosPage.getNumber());
        model.addAttribute("totalPages", productosPage.getTotalPages());
        model.addAttribute("totalItems", productosPage.getTotalElements());
        model.addAttribute("search", search);

        if (productosPage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, productosPage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
            return "productos/formulario";
        }
        productoService.guardarProducto(producto);
        redirectAttributes.addFlashAttribute("message", "Producto guardado exitosamente!");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
            return "productos/formulario";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Producto no encontrado.");
            return "redirect:/productos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productoService.eliminarProducto(id);
        redirectAttributes.addFlashAttribute("message", "Producto eliminado exitosamente!");
        return "redirect:/productos";
    }
}