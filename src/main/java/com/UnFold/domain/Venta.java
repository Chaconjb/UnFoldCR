package com.UnFold.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta; // Este es el ID de la tabla Venta, sin relaciones aquí

    @ManyToOne // Relación Many-to-One con Factura
    @JoinColumn(name = "id_factura") // Esta es la columna FK que apunta a Factura
    private Factura factura; // Este es el campo que representa la relación con la entidad Factura

    @ManyToOne // Relación Many-to-One con Producto
    @JoinColumn(name = "id_producto")
    private Producto producto;
    private double precio;
    private int cantidad;
    private String talla;
    private String color;
}