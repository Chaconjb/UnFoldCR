package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime; // Para manejar fechas y horas
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
@Data
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    private double total; // Total de la venta

    // Estado de la venta (ej. "completada", "pendiente", "cancelada")
    private String estado;

    // Relación ManyToOne: Muchas ventas pueden ser realizadas por un usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario") // Columna FK en la tabla 'venta'
    private Usuario usuario;

    // Relación OneToMany: Una venta puede tener muchos detalles de venta (productos específicos)
    // Necesitarías una clase 'DetalleVenta' o 'LineaVenta' para esto.
    // @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<DetalleVenta> detallesVenta = new ArrayList<>();

    // Constructor vacío
    public Venta() {
    }

    // Constructor ejemplo
    public Venta(LocalDateTime fechaVenta, double total, String estado, Usuario usuario) {
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.usuario = usuario;
    }
}