package com.example.examen_programacion_1.service;

import com.example.examen_programacion_1.model.Producto;
import com.example.examen_programacion_1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Transactional(readOnly = true)
    public Page<Producto> obtenerProductosPaginados(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        if (search != null && !search.trim().isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(search, pageable);
        }
        return productoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}