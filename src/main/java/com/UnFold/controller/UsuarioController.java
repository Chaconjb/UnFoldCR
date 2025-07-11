package com.UnFold.controller;

import com.UnFold.domain.Usuario;
import com.UnFold.domain.Usuario;
import com.UnFold.service.UsuarioService;
import com.UnFold.service.UsuarioService;
import com.UnFold.service.FirebaseStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensajes de éxito/error en redirecciones

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var usuarios = usuarioService.getUsuarios(false);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("usuario", new Usuario());
        return "usuario/admin_listado";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Usuario usuario,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar categoría: " + usuario.getDescripcion());
        try {
            usuarioService.save(usuario);
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "usuarios", usuario.getIdUsuario());
                usuario.setRutaImagen(rutaImagen);
                usuarioService.save(usuario);
            }
            redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar categoría: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarUsuario(Usuario usuario, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar categoría con ID: " + usuario.getIdUsuario());
        try {
            usuarioService.delete(usuario);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar categoría: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/modificar/{idUsuario}")
    public String modificarUsuario(Usuario usuario, Model model) {
        log.info("Cargando categoría para modificación con ID: " + usuario.getIdUsuario());
        usuario = usuarioService.getUsuario(usuario);
        model.addAttribute("usuario", usuario);
        return "usuario/modifica";
    }

    @GetMapping("/visual")
    public String visualCategories(Model model) {
        log.info("Cargando la vista visual de categorías (pública).");

        List<Usuario> allActiveProducts = usuarioService.getUsuarios(true);

        model.addAttribute("usuariosHombre", allActiveProducts.stream()
                .filter(p -> p.getUsuario() != null && "Hombre".equalsIgnoreCase(p.getUsuario().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("usuariosMujer", allActiveProducts.stream()
                .filter(p -> p.getUsuario() != null && "Mujer".equalsIgnoreCase(p.getUsuario().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("usuariosNinos", allActiveProducts.stream()
                .filter(p -> p.getUsuario() != null && "Niños".equalsIgnoreCase(p.getUsuario().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("usuariosAccesorios", allActiveProducts.stream()
                .filter(p -> p.getUsuario() != null && "Accesorios".equalsIgnoreCase(p.getUsuario().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        return "usuario/visual";
    }

    @GetMapping("/hombre")
    public String usuarioHombre(Model model) {
        log.info("Accediendo a la categoría Hombre.");
        List<Usuario> usuarios = usuarioService.getUsuariosByUsuarioDescripcion("Hombre", true);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioNombre", "Hombre");
        return "usuario/usuario_detalle";
    }

    @GetMapping("/mujer")
    public String usuarioMujer(Model model) {
        log.info("Accediendo a la categoría Mujer.");
        List<Usuario> usuarios = usuarioService.getUsuariosByUsuarioDescripcion("Mujer", true);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioNombre", "Mujer");
        return "usuario/usuario_detalle";
    }

    @GetMapping("/ninos")
    public String usuarioNinos(Model model) {
        log.info("Accediendo a la categoría Niños.");
        List<Usuario> usuarios = usuarioService.getUsuariosByUsuarioDescripcion("Niños", true);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioNombre", "Niños");
        return "usuario/usuario_detalle";
    }

    @GetMapping("/accesorios")
    public String usuarioAccesorios(Model model) {
        log.info("Accediendo a la categoría Accesorios.");
        List<Usuario> usuarios = usuarioService.getUsuariosByUsuarioDescripcion("Accesorios", true);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioNombre", "Accesorios");
        return "usuario/usuario_detalle";
    }
}
