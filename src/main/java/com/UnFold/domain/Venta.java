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
    @ManyToOne// Relación Many-to-One con Factura
    @JoinColumn(name = "id_factura") 
    private Long idVenta;
    private Factura factura; 
    @ManyToOne // Relación Many-to-One con Producto
    @JoinColumn(name = "id_producto") 
    private Producto producto; 
    private double precio;
    private int cantidad;
    private String talla;
    private String color;
}