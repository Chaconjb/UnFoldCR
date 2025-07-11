package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data; // Para @Data, @Getter, @Setter, etc.
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "categoria") // Mapea esta entidad a la tabla 'categoria' en la base de datos
@Data // Genera getters, setters, equals, hashCode y toString automáticamente
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L; // Necesario para Serializable

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es autoincremental por la BD
    @Column(name = "id_categoria") // Mapea el campo al nombre de la columna en la BD
    private Long idCategoria;

    private String descripcion;

    @Column(name = "ruta_imagen") // Si la columna en BD se llama ruta_imagen
    private String rutaImagen;

    private boolean activo; // Campo para indicar si la categoría está activa o no

    // Relación OneToMany: Una categoría puede tener muchos productos
    // mappedBy = "categoria": Indica el nombre del campo en la clase Producto que posee la relación.
    // cascade = CascadeType.ALL: Todas las operaciones (persist, merge, remove) se propagan a los productos.
    // orphanRemoval = true: Si un producto se desvincula de una categoría, se elimina.
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>(); // Importante inicializar la lista para evitar NullPointerException

    // Constructor vacío requerido por JPA
    public Categoria() {
    }

    // Constructor con descripción y activo (útil para inicializaciones rápidas)
    public Categoria(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
}