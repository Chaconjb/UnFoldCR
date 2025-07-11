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

import java.util.List;

@Controller
@RequestMapping("/producto")
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de productos para administración.");
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());

        List<Categoria> categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        model.addAttribute("producto", new Producto());
        return "producto/admin_listado";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar producto: " + producto.getDescripcion());
        try {

            productoService.save(producto);

            if (!imagenFile.isEmpty()) {

                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "productos", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);

                productoService.save(producto);
            }
            redirectAttributes.addFlashAttribute("todoOk", "Producto guardado exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar producto: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar producto con ID: " + producto.getIdProducto());
        try {
            productoService.delete(producto);
            redirectAttributes.addFlashAttribute("todoOk", "Producto eliminado exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar producto: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificarProducto(Producto producto, Model model) {
        log.info("Cargando producto para modificación con ID: " + producto.getIdProducto());
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);

        List<Categoria> categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        return "producto/modifica";
    }

    @GetMapping("/detalle/{idProducto}")
    public String detalleProducto(Producto producto, Model model) {
        log.info("Cargando detalle para producto con ID: " + producto.getIdProducto());
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "producto/detalle";
    }
}
