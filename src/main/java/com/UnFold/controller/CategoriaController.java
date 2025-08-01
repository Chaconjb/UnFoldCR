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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

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

    // Métodos para la administración de categorías
    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        model.addAttribute("categoria", new Categoria());
        return "categoria/admin_listado";
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

    @PostMapping("/eliminar")
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

    @GetMapping("/visual")
    public String visualCategories(Model model) {
        log.info("Cargando la vista visual de categorías (pública).");
        List<Producto> allActiveProducts = productoService.getProductos(true);

        
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);


        model.addAttribute("productosHombre", allActiveProducts.stream()
                .filter(p -> p.getCategoria() != null && "Hombre".equalsIgnoreCase(p.getCategoria().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosMujer", allActiveProducts.stream()
                .filter(p -> p.getCategoria() != null && "Mujer".equalsIgnoreCase(p.getCategoria().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosNinos", allActiveProducts.stream()
                .filter(p -> p.getCategoria() != null && "Niños".equalsIgnoreCase(p.getCategoria().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosAccesorios", allActiveProducts.stream()
                .filter(p -> p.getCategoria() != null && "Accesorios".equalsIgnoreCase(p.getCategoria().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

     
        model.addAttribute("categoriaNombre", null);

        return "categoria/visual";
    }

    
    @GetMapping("/{descripcion}")
    public String categoriaDetalle(@PathVariable("descripcion") String descripcion, Model model) {
        log.info("Accediendo a la categoría: " + descripcion);

        // Carga las categorías para el menú de navegación
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

    
        List<Producto> productos = productoService.getProductosByCategoriaDescripcion(descripcion, true);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaNombre", descripcion);

      
        model.addAttribute("productosHombre", null);
        model.addAttribute("productosMujer", null);
        model.addAttribute("productosNinos", null);
        model.addAttribute("productosAccesorios", null);

        return "categoria/visual";
    }
}
