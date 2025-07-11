package com.UnFold.controller;

import com.UnFold.domain.Factura;
import com.UnFold.domain.Producto;
import com.UnFold.service.FacturaService;
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
@RequestMapping("/factura")
@Slf4j
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var facturas = facturaService.getFacturas(false);
        model.addAttribute("facturas", facturas);
        model.addAttribute("totalFacturas", facturas.size());
        model.addAttribute("factura", new Factura());
        return "factura/admin_listado";
    }

    @PostMapping("/guardar")
    public String guardarFactura(Factura factura,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar categoría: " + factura.getDescripcion());
        try {
            facturaService.save(factura);
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "facturas", factura.getIdFactura());
                factura.setRutaImagen(rutaImagen);
                facturaService.save(factura);
            }
            redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar categoría: " + e.getMessage());
        }
        return "redirect:/factura/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarFactura(Factura factura, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar categoría con ID: " + factura.getIdFactura());
        try {
            facturaService.delete(factura);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar categoría: " + e.getMessage());
        }
        return "redirect:/factura/listado";
    }

    @GetMapping("/modificar/{idFactura}")
    public String modificarFactura(Factura factura, Model model) {
        log.info("Cargando categoría para modificación con ID: " + factura.getIdFactura());
        factura = facturaService.getFactura(factura);
        model.addAttribute("factura", factura);
        return "factura/modifica";
    }

    @GetMapping("/visual")
    public String visualCategories(Model model) {
        log.info("Cargando la vista visual de categorías (pública).");

        List<Producto> allActiveProducts = productoService.getProductos(true);

        model.addAttribute("productosHombre", allActiveProducts.stream()
                .filter(p -> p.getFactura() != null && "Hombre".equalsIgnoreCase(p.getFactura().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosMujer", allActiveProducts.stream()
                .filter(p -> p.getFactura() != null && "Mujer".equalsIgnoreCase(p.getFactura().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosNinos", allActiveProducts.stream()
                .filter(p -> p.getFactura() != null && "Niños".equalsIgnoreCase(p.getFactura().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("productosAccesorios", allActiveProducts.stream()
                .filter(p -> p.getFactura() != null && "Accesorios".equalsIgnoreCase(p.getFactura().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        return "factura/visual";
    }

    @GetMapping("/hombre")
    public String facturaHombre(Model model) {
        log.info("Accediendo a la categoría Hombre.");
        List<Producto> productos = productoService.getProductosByFacturaDescripcion("Hombre", true);
        model.addAttribute("productos", productos);
        model.addAttribute("facturaNombre", "Hombre");
        return "factura/factura_detalle";
    }

    @GetMapping("/mujer")
    public String facturaMujer(Model model) {
        log.info("Accediendo a la categoría Mujer.");
        List<Producto> productos = productoService.getProductosByFacturaDescripcion("Mujer", true);
        model.addAttribute("productos", productos);
        model.addAttribute("facturaNombre", "Mujer");
        return "factura/factura_detalle";
    }

    @GetMapping("/ninos")
    public String facturaNinos(Model model) {
        log.info("Accediendo a la categoría Niños.");
        List<Producto> productos = productoService.getProductosByFacturaDescripcion("Niños", true);
        model.addAttribute("productos", productos);
        model.addAttribute("facturaNombre", "Niños");
        return "factura/factura_detalle";
    }

    @GetMapping("/accesorios")
    public String facturaAccesorios(Model model) {
        log.info("Accediendo a la categoría Accesorios.");
        List<Producto> productos = productoService.getProductosByFacturaDescripcion("Accesorios", true);
        model.addAttribute("productos", productos);
        model.addAttribute("facturaNombre", "Accesorios");
        return "factura/factura_detalle";
    }
}
