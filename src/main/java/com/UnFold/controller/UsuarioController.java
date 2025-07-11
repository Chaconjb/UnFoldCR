package com.UnFold.controller;

import com.UnFold.domain.Usuario;
import com.UnFold.service.UsuarioService;
import com.UnFold.service.FirebaseStorageService; // Si los usuarios tendrán foto de perfil
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/usuario") // Mapeo para /usuario
@Slf4j // Para logging
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Inyección del servicio de Usuario

    @Autowired
    private FirebaseStorageService firebaseStorageService; // Si los usuarios pueden tener imagen/foto de perfil

    // Muestra el listado de usuarios (para administración)
    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de usuarios para administración.");
        var usuarios = usuarioService.getUsuarios(false); // Obtener todos los usuarios (activos o no)
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("usuario", new Usuario()); // Objeto para el formulario de agregar
        return "usuario/listado"; // Apunta al listado de usuarios
    }

    // Guarda un usuario (nuevo o existente)
    @PostMapping("/guardar")
    public String guardarUsuario(Usuario usuario,
            @RequestParam("imagenFile") MultipartFile imagenFile, // Si los usuarios pueden tener imagen
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar usuario: " + usuario.getUsername()); // Usamos username como identificador
        try {
            usuarioService.save(usuario); // Guarda el usuario primero para obtener un ID si es nuevo

            if (!imagenFile.isEmpty()) {
                // Genera la ruta de la imagen usando el ID del usuario
                // Asumo que tienes un idUsuario o un username único para usar en la ruta
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "usuarios", usuario.getIdUsuario());
                usuario.setRutaImagen(rutaImagen);
                usuarioService.save(usuario); // Vuelve a guardar para actualizar la ruta de la imagen
            }
            redirectAttributes.addFlashAttribute("todoOk", "Usuario guardado exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado"; // Redirige al listado de usuarios
    }

    // Elimina un usuario
    @PostMapping("/eliminar")
    public String eliminarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar usuario con ID: " + usuario.getIdUsuario());
        try {
            usuarioService.delete(usuario);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario eliminado exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado"; // Redirige al listado de usuarios
    }

    // Carga un usuario para modificación
    @GetMapping("/modificar/{idUsuario}")
    public String modificarUsuario(Usuario usuario, Model model) {
        log.info("Cargando usuario para modificación con ID: " + usuario.getIdUsuario());
        usuario = usuarioService.getUsuario(usuario); // Obtener el usuario por ID
        model.addAttribute("usuario", usuario);
        return "usuario/modifica"; // Apunta a la vista de modificación de usuario
    }
}