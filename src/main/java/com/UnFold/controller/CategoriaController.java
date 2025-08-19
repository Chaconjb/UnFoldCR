package com.UnFold.controller;

import com.UnFold.domain.Categoria;
import com.UnFold.domain.Producto;
import com.UnFold.service.CategoriaService;
import com.UnFold.service.ProductoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categoria")
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        model.addAttribute("categoria", new Categoria());
        return "categoria/listado"; // Ya corregido para apuntar a listado.html
    }

    @PostMapping("/guardar")
    public String guardarCategoria(Categoria categoria,
                                   @RequestParam("imagenFile") MultipartFile imagenFile,
                                   RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar categoría: " + categoria.getDescripcion());
        try {
            categoriaService.save(categoria); 
            
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "categorias", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
                categoriaService.save(categoria); 
            }
            redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}") 
    public String eliminarCategoria(Categoria categoria, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar categoría con ID: " + categoria.getIdCategoria());
        try {
            categoriaService.delete(categoria);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar categoría: " + e.getMessage());
        }
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String modificarCategoria(Categoria categoria, Model model) {
        log.info("Cargando categoría para modificación con ID: " + categoria.getIdCategoria());
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "categoria/modifica";
    }

}