package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime; // Para manejar fechas y horas

@Entity
@Table(name = "factura")
@Data
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    // Fecha y hora de emisión de la factura
    @Column(name = "fecha_facturacion")
    private LocalDateTime fechaFacturacion;

    private double total; // Monto total de la factura

    // Estado de la factura (ej. "pagada", "pendiente", "cancelada")
    private String estado;

    // Relación ManyToOne: Muchas facturas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario") // Columna FK en la tabla 'factura'
    private Usuario usuario; // Campo 'usuario' debe coincidir con el 'mappedBy' en Usuario.java

    // Podrías tener una relación OneToMany con 'DetalleFactura' si tienes líneas de factura
    // @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<DetalleFactura> detalles = new ArrayList<>();

    // Constructor vacío
    public Factura() {
    }

    // Constructor ejemplo
    public Factura(LocalDateTime fechaFacturacion, double total, String estado, Usuario usuario) {
        this.fechaFacturacion = fechaFacturacion;
        this.total = total;
        this.estado = estado;
        this.usuario = usuario;
    }
}