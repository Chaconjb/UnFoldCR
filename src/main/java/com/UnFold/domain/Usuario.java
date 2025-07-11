// Ubicado en src/main/java/com/UnFold/domain/Usuario.java

package com.UnFold.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true) // Asegura que el username sea único en la DB
    private Long idUsuario; 
    private String username;

    private String password;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String rutaImagen; 
    private boolean activo;
}