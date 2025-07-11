package com.UnFold.controller;

import com.UnFold.domain.Venta;
import com.UnFold.domain.Usuario; // Necesario si la venta está asociada a un usuario
import com.UnFold.service.VentaService;
import com.UnFold.service.UsuarioService; // Necesario para listar usuarios al crear/modificar ventas
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/venta") // Mapeo para /venta
@Slf4j // Para logging
public class VentaController {

    @Autowired
    private VentaService ventaService; // Inyección del servicio de Venta

    @Autowired(required = false) // Inyección opcional si no siempre se usa
    private UsuarioService usuarioService; // Para seleccionar el usuario asociado a la venta

    // Muestra el listado de ventas (para administración)
    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de ventas para administración.");
        var ventas = ventaService.getVentas(false); // Obtener todas las ventas (activos o no)
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("venta", new Venta()); // Objeto para el formulario de agregar

        // Si necesitas listar usuarios para el formulario de agregar/modificar venta
        if (usuarioService != null) {
            var usuarios = usuarioService.getUsuarios(true); // Obtener solo usuarios activos
            model.addAttribute("usuarios", usuarios);
        }
        
        return "venta/listado"; // Apunta al listado de ventas
    }

    // Guarda una venta (nueva o existente)
    @PostMapping("/guardar")
    public String guardarVenta(Venta venta, RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar venta con ID: " + venta.getIdVenta()); // O alguna otra propiedad de venta
        try {
            ventaService.save(venta);
            redirectAttributes.addFlashAttribute("todoOk", "Venta guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar venta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar venta: " + e.getMessage());
        }
        return "redirect:/venta/listado"; // Redirige al listado de ventas
    }

    // Elimina una venta
    @PostMapping("/eliminar")
    public String eliminarVenta(Venta venta, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar venta con ID: " + venta.getIdVenta());
        try {
            ventaService.delete(venta);
            redirectAttributes.addFlashAttribute("todoOk", "Venta eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar venta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar venta: " + e.getMessage());
        }
        return "redirect:/venta/listado"; // Redirige al listado de ventas
    }

    // Carga una venta para modificación
    @GetMapping("/modificar/{idVenta}")
    public String modificarVenta(Venta venta, Model model) {
        log.info("Cargando venta para modificación con ID: " + venta.getIdVenta());
        venta = ventaService.getVenta(venta); // Obtener la venta por ID
        model.addAttribute("venta", venta);

        // Si necesitas listar usuarios para el formulario de modificar venta
        if (usuarioService != null) {
            var usuarios = usuarioService.getUsuarios(true);
            model.addAttribute("usuarios", usuarios);
        }

        return "venta/modifica"; // Apunta a la vista de modificación de venta
    }
}