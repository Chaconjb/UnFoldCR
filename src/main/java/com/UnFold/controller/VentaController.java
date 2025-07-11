package com.UnFold.controller;

import com.UnFold.domain.Venta;
import com.UnFold.domain.Venta;
import com.UnFold.service.VentaService;
import com.UnFold.service.VentaService;
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
@RequestMapping("/venta")
@Slf4j
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de categorías para administración.");
        var ventas = ventaService.getVentas(false);
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("venta", new Venta());
        return "venta/admin_listado";
    }

    @PostMapping("/guardar")
    public String guardarVenta(Venta venta,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar categoría: " + venta.getDescripcion());
        try {
            ventaService.save(venta);
            if (!imagenFile.isEmpty()) {
                String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "ventas", venta.getIdVenta());
                venta.setRutaImagen(rutaImagen);
                ventaService.save(venta);
            }
            redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar categoría: " + e.getMessage());
        }
        return "redirect:/venta/listado";
    }

    @PostMapping("/eliminar")
    public String eliminarVenta(Venta venta, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar categoría con ID: " + venta.getIdVenta());
        try {
            ventaService.delete(venta);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar categoría: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar categoría: " + e.getMessage());
        }
        return "redirect:/venta/listado";
    }

    @GetMapping("/modificar/{idVenta}")
    public String modificarVenta(Venta venta, Model model) {
        log.info("Cargando categoría para modificación con ID: " + venta.getIdVenta());
        venta = ventaService.getVenta(venta);
        model.addAttribute("venta", venta);
        return "venta/modifica";
    }

    @GetMapping("/visual")
    public String visualCategories(Model model) {
        log.info("Cargando la vista visual de categorías (pública).");

        List<Venta> allActiveProducts = ventaService.getVentas(true);

        model.addAttribute("ventasHombre", allActiveProducts.stream()
                .filter(p -> p.getVenta() != null && "Hombre".equalsIgnoreCase(p.getVenta().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("ventasMujer", allActiveProducts.stream()
                .filter(p -> p.getVenta() != null && "Mujer".equalsIgnoreCase(p.getVenta().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("ventasNinos", allActiveProducts.stream()
                .filter(p -> p.getVenta() != null && "Niños".equalsIgnoreCase(p.getVenta().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        model.addAttribute("ventasAccesorios", allActiveProducts.stream()
                .filter(p -> p.getVenta() != null && "Accesorios".equalsIgnoreCase(p.getVenta().getDescripcion()))
                .limit(4)
                .collect(Collectors.toList()));

        return "venta/visual";
    }

    @GetMapping("/hombre")
    public String ventaHombre(Model model) {
        log.info("Accediendo a la categoría Hombre.");
        List<Venta> ventas = ventaService.getVentasByVentaDescripcion("Hombre", true);
        model.addAttribute("ventas", ventas);
        model.addAttribute("ventaNombre", "Hombre");
        return "venta/venta_detalle";
    }

    @GetMapping("/mujer")
    public String ventaMujer(Model model) {
        log.info("Accediendo a la categoría Mujer.");
        List<Venta> ventas = ventaService.getVentasByVentaDescripcion("Mujer", true);
        model.addAttribute("ventas", ventas);
        model.addAttribute("ventaNombre", "Mujer");
        return "venta/venta_detalle";
    }

    @GetMapping("/ninos")
    public String ventaNinos(Model model) {
        log.info("Accediendo a la categoría Niños.");
        List<Venta> ventas = ventaService.getVentasByVentaDescripcion("Niños", true);
        model.addAttribute("ventas", ventas);
        model.addAttribute("ventaNombre", "Niños");
        return "venta/venta_detalle";
    }

    @GetMapping("/accesorios")
    public String ventaAccesorios(Model model) {
        log.info("Accediendo a la categoría Accesorios.");
        List<Venta> ventas = ventaService.getVentasByVentaDescripcion("Accesorios", true);
        model.addAttribute("ventas", ventas);
        model.addAttribute("ventaNombre", "Accesorios");
        return "venta/venta_detalle";
    }
}
