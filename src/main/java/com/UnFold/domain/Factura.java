package com.UnFold.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime; 
import lombok.Data;

@Data
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    
    @ManyToOne // Relación Many-to-One con Usuario
    @JoinColumn(name = "id_usuario") 
    private Usuario usuario; 
    private LocalDateTime fecha; 
    private double total;
    private String estado;
}