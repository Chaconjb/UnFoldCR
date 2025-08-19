package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data;
import java.io.Serializable;

@Entity 
@Table(name = "producto") 
@Data 
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L; 

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_producto") 
    private Long idProducto;

    private String descripcion;
    private double precio;
    private int existencias;

    @Column(name = "ruta_imagen") 
    private String rutaImagen;

    private boolean activo; 

    
    @ManyToOne
    @JoinColumn(name = "id_categoria") 
    private Categoria categoria; 


    public Producto() {
    }


    public Producto(String descripcion, double precio, int existencias, boolean activo) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
        this.activo = activo;
    }
}