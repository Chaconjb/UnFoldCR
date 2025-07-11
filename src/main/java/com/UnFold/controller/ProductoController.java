package com.UnFold.controller;

import com.UnFold.domain.Categoria; // Puede que necesites Categoria si Producto tiene una relación
import com.UnFold.domain.Producto;
import com.UnFold.service.CategoriaService; // Posiblemente necesites este servicio si vas a listar categorías para asignar a productos
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
import java.util.stream.Collectors; // Es posible que no sea necesario si no filtras como en visualCategories

@Controller
@RequestMapping("/producto") // Cambiado de "/categoria" a "/producto"
@Slf4j // Para usar 'log'
public class ProductoController {

    @Autowired
    private ProductoService productoService; // Inyección del servicio de Producto

    @Autowired(required = false) // Hacemos la inyección opcional si no siempre se usa
    private CategoriaService categoriaService; // Posiblemente necesites este servicio para seleccionar categorías al agregar/modificar productos

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    // Método para mostrar el listado de productos (para administración)
    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de productos para administración.");
        var productos = productoService.getProductos(false); // Obtener todos los productos (activos o no)
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("producto", new Producto()); // Objeto para el formulario de agregar
        
        // Si necesitas listar categorías para el formulario de agregar/modificar producto
        if (categoriaService != null) {
            var categorias = categoriaService.getCategorias(true); // Obtener solo categorías activas
            model.addAttribute("categorias", categorias);
        }
        
        return "producto/listado"; // Apunta al listado de productos
    }

    // Método para guardar un producto (nuevo o existente)
    @PostMapping("/guardar")
    public String guardarProducto(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar producto: " + producto.getDescripcion());
        try {
            // Guarda el producto primero para obtener un ID si es nuevo
            productoService.save(producto); 

            if (!imagenFile.isEmpty()) {
                // Genera la ruta de la imagen usando el ID del producto
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "productos", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoService.save(producto); // Vuelve a guardar para actualizar la ruta de la imagen
            }
            redirectAttributes.addFlashAttribute("todoOk", "Producto guardado exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar producto: " + e.getMessage());
        }
        return "redirect:/producto/listado"; // Redirige al listado de productos
    }

    // Método para eliminar un producto
    @PostMapping("/eliminar") // O si prefieres, @GetMapping("/eliminar/{idProducto}") y pasas el ID en la URL
    public String eliminarProducto(Producto producto, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar producto con ID: " + producto.getIdProducto());
        try {
            productoService.delete(producto);
            redirectAttributes.addFlashAttribute("todoOk", "Producto eliminado exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar producto: " + e.getMessage());
        }
        return "redirect:/producto/listado"; // Redirige al listado de productos
    }

    // Método para cargar un producto para modificación
    @GetMapping("/modificar/{idProducto}") // Cambiado de idCategoria a idProducto
    public String modificarProducto(Producto producto, Model model) {
        log.info("Cargando producto para modificación con ID: " + producto.getIdProducto());
        producto = productoService.getProducto(producto); // Obtener el producto por ID
        model.addAttribute("producto", producto);
        
        // Si necesitas listar categorías para el formulario de modificar producto
        if (categoriaService != null) {
            var categorias = categoriaService.getCategorias(true);
            model.addAttribute("categorias", categorias);
        }
        
        return "producto/modifica"; // Apunta a la vista de modificación de producto
    }

    // --- Métodos para vistas públicas de productos por categorías ---
    // (Estos métodos asumen que Producto tiene una relación con Categoria
    // y que Categoria tiene una 'descripcion' para filtrar)
    
    @GetMapping("/visual")
    public String visualProducts(Model model) {
        log.info("Cargando la vista visual de productos (pública).");
        // Optimizamos obteniendo todos los productos activos una vez
        List<Producto> allActiveProducts = productoService.getProductos(true);

        // Filtrar productos por descripción de categoría y limitar a 4
        // Esta lógica de filtrado puede ser mejor si se hace en el servicio/repositorio
        // con métodos como productoService.getProductosByCategoriaDescripcion("Hombre", true)
        // pero se mantiene aquí para reflejar el original.
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

        return "producto/visual"; // Apunta al visual de productos
    }

    @GetMapping("/detalle/{idProducto}") // Nuevo método para ver detalle de un producto específico
    public String detalleProducto(Producto producto, Model model) {
        log.info("Accediendo al detalle del producto con ID: " + producto.getIdProducto());
        producto = productoService.getProducto(producto);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "producto/detalle"; // Crea una vista "detalle.html" en templates/producto
        } else {
            return "redirect:/producto/visual"; // Redirige si el producto no se encuentra
        }
    }

    // Métodos para ver todos los productos de una categoría específica
    // Estos métodos requieren que ProductoService tenga un método getProductosByCategoriaDescripcion
    // como el que estabas usando en CategoriaController para los listados.
    @GetMapping("/hombre")
    public String categoriaHombre(Model model) {
        log.info("Accediendo a la categoría Hombre (todos los productos).");
        List<Producto> productos = productoService.getProductosByCategoriaDescripcion("Hombre", true);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaNombre", "Hombre");
        return "producto/categoria_detalle"; // Apunta a una vista de detalle de categoría de productos
    }

    @GetMapping("/mujer")
    public String categoriaMujer(Model model) {
        log.info("Accediendo a la categoría Mujer (todos los productos).");
        List<Producto> productos = productoService.getProductosByCategoriaDescripcion("Mujer", true);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaNombre", "Mujer");
        return "producto/categoria_detalle";
    }

    @GetMapping("/ninos")
    public String categoriaNinos(Model model) {
        log.info("Accediendo a la categoría Niños (todos los productos).");
        List<Producto> productos = productoService.getProductosByCategoriaDescripcion("Niños", true);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaNombre", "Niños");
        return "producto/categoria_detalle";
    }

    @GetMapping("/accesorios")
    public String categoriaAccesorios(Model model) {
        log.info("Accediendo a la categoría Accesorios (todos los productos).");
        List<Producto> productos = productoService.getProductosByCategoriaDescripcion("Accesorios", true);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaNombre", "Accesorios");
        return "producto/categoria_detalle";
    }
}