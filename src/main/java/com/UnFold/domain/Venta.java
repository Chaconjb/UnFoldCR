package com.UnFold.domain;

import jakarta.persistence.*; 
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Data
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "id_factura") 
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "id_producto") 
    private Producto producto; // Asumiendo que tienes una clase Producto

    private double precio; 
    private int cantidad;
    private String talla;
    private String color;

    public Venta() {
    }
}