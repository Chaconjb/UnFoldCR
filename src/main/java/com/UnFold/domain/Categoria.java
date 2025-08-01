package com.UnFold.domain;

import jakarta.persistence.*; 
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity 
@Table(name = "categoria")
@Data 
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L; 

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_categoria") 
    private Long idCategoria;

    private String descripcion;

    @Column(name = "ruta_imagen") 
    private String rutaImagen;

    private boolean activo; 


    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>(); 
    public Categoria() {
    }

    public Categoria(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
}