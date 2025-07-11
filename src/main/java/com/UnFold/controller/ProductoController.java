package com.UnFold.controller;

import com.UnFold.domain.Producto;
import com.UnFold.domain.Producto;
import com.UnFold.service.ProductoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensajes de éxito/error en redirecciones

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/producto")
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("producto", new Producto());
        return "producto/admin_listado";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar categoría: " + producto.getDescripcion());
        try {
            productoService.save(producto);
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "productos", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoService.save(producto);
            }
            redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar categoría: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar categoría con ID: " + producto.getIdProducto());
        try {
            productoService.delete(producto);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar categoría: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String modificarProducto(Producto producto, Model model) {
        log.info("Cargando categoría para modificación con ID: " + producto.getIdProducto());
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "producto/modifica";
    }

    @GetMapping("/visual")
    public String visualCategories(Model model) {
        log.info("Cargando la vista visual de categorías (pública).");

        List<Producto> allActiveProducts = productoService.getProductos(true);

        model.addAttribute("productosHombre", allActiveProducts.stream()
                .filter(p -> p.getProducto() != null && "Hombre".equalsIgnoreCase(p.getProducto().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosMujer", allActiveProducts.stream()
                .filter(p -> p.getProducto() != null && "Mujer".equalsIgnoreCase(p.getProducto().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosNinos", allActiveProducts.stream()
                .filter(p -> p.getProducto() != null && "Niños".equalsIgnoreCase(p.getProducto().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosAccesorios", allActiveProducts.stream()
                .filter(p -> p.getProducto() != null && "Accesorios".equalsIgnoreCase(p.getProducto().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        return "producto/visual";
    }

    @GetMapping("/hombre")
    public String productoHombre(Model model) {
        log.info("Accediendo a la categoría Hombre.");
        List<Producto> productos = productoService.getProductosByProductoDescripcion("Hombre", true);
        model.addAttribute("productos", productos);
        model.addAttribute("productoNombre", "Hombre");
        return "producto/producto_detalle";
    }

    @GetMapping("/mujer")
    public String productoMujer(Model model) {
        log.info("Accediendo a la categoría Mujer.");
        List<Producto> productos = productoService.getProductosByProductoDescripcion("Mujer", true);
        model.addAttribute("productos", productos);
        model.addAttribute("productoNombre", "Mujer");
        return "producto/producto_detalle";
    }

    @GetMapping("/ninos")
    public String productoNinos(Model model) {
        log.info("Accediendo a la categoría Niños.");
        List<Producto> productos = productoService.getProductosByProductoDescripcion("Niños", true);
        model.addAttribute("productos", productos);
        model.addAttribute("productoNombre", "Niños");
        return "producto/producto_detalle";
    }

    @GetMapping("/accesorios")
    public String productoAccesorios(Model model) {
        log.info("Accediendo a la categoría Accesorios.");
        List<Producto> productos = productoService.getProductosByProductoDescripcion("Accesorios", true);
        model.addAttribute("productos", productos);
        model.addAttribute("productoNombre", "Accesorios");
        return "producto/producto_detalle";
    }
}
