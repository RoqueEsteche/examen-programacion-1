package com.example.examen_programacion_1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*; // Importar para validaciones

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(nullable = true)
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double precio;

    @ManyToOne(fetch = FetchType.EAGER) // ¡CAMBIADO A EAGER!
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "Debe seleccionar una categoría")
    private Categoria categoria;
}