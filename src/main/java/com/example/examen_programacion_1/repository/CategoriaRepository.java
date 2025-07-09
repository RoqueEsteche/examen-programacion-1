package com.example.examen_programacion_1.repository;

import com.example.examen_programacion_1.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Page<Categoria> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}