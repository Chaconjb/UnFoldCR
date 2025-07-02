

package com.UnFoldCR.domain; 

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
}