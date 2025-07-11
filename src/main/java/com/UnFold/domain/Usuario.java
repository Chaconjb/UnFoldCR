package com.UnFold.domain;

import jakarta.persistence.*; // Usa jakarta.persistence para Spring Boot 3+
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(unique = true) // El username debe ser único
    private String username;

    private String password; // Considera encriptar contraseñas
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;

    @Column(name = "ruta_imagen")
    private String rutaImagen; // Para foto de perfil del usuario

    private boolean activo;

    // Relación OneToMany: Un usuario puede tener muchas facturas (o ventas)
    // Asumo que 'Factura' tiene un campo 'usuario' que mapea a esta relación.
    // También podrías tener una relación con Venta si tu diseño lo requiere.
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas = new ArrayList<>(); // O List<Venta> si mapeas ventas directamente

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con username y password (ejemplo)
    public Usuario(String username, String password, boolean activo) {
        this.username = username;
        this.password = password;
        this.activo = activo;
    }
}