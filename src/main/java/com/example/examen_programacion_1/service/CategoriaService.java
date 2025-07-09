package com.example.examen_programacion_1.service;

import com.example.examen_programacion_1.model.Categoria;
import com.example.examen_programacion_1.repository.CategoriaRepository;
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
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Transactional(readOnly = true)
    public Page<Categoria> obtenerCategoriasPaginadas(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        if (search != null && !search.trim().isEmpty()) {
            return categoriaRepository.findByNombreContainingIgnoreCase(search, pageable);
        }
        return categoriaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}