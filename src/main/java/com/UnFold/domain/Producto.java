package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data;
import java.io.Serializable;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "producto") // Mapea esta entidad a la tabla 'producto' en la base de datos
@Data // Genera getters, setters, equals, hashCode y toString automáticamente
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L; // Necesario para Serializable

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    @Column(name = "id_producto") // Nombre de la columna en BD
    private Long idProducto;

    private String descripcion;
    private double precio;
    private int existencias;

    @Column(name = "ruta_imagen") // Nombre de la columna en BD
    private String rutaImagen;

    private boolean activo; // Si el producto está activo

    // Relación ManyToOne: Muchos productos pertenecen a una categoría
    // @JoinColumn: Especifica la columna de la clave foránea en la tabla 'producto'
    // name = "id_categoria": Nombre de la columna FK en la tabla 'producto' que referencia a 'categoria'
    @ManyToOne
    @JoinColumn(name = "id_categoria") // Columna FK en la tabla 'producto'
    private Categoria categoria; // El campo 'categoria' debe coincidir con el 'mappedBy' en Categoria.java

    // Constructor vacío requerido por JPA
    public Producto() {
    }

    // Constructor con campos básicos (ejemplo)
    public Producto(String descripcion, double precio, int existencias, boolean activo) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
        this.activo = activo;
    }
}