package com.UnFold.controller;

import com.UnFold.domain.Factura;
import com.UnFold.domain.Usuario; // Necesario si la factura está asociada a un usuario
import com.UnFold.service.FacturaService;
import com.UnFold.service.UsuarioService; // Necesario para listar usuarios al crear/modificar facturas
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
@RequestMapping("/factura") // Mapeo para /factura
@Slf4j // Para logging
public class FacturaController {

    @Autowired
    private FacturaService facturaService; // Inyección del servicio de Factura

    @Autowired(required = false) // Inyección opcional
    private UsuarioService usuarioService; // Para seleccionar el usuario asociado a la factura

    // Muestra el listado de facturas (para administración)
    @GetMapping("/listado")
    public String listado(Model model) {
        log.info("Accediendo al listado de facturas para administración.");
        var facturas = facturaService.getFacturas(false); // Obtener todas las facturas (activos o no)
        model.addAttribute("facturas", facturas);
        model.addAttribute("totalFacturas", facturas.size());
        model.addAttribute("factura", new Factura()); // Objeto para el formulario de agregar

        // Si necesitas listar usuarios para el formulario de agregar/modificar factura
        if (usuarioService != null) {
            var usuarios = usuarioService.getUsuarios(true); // Obtener solo usuarios activos
            model.addAttribute("usuarios", usuarios);
        }
        
        return "factura/listado"; // Apunta al listado de facturas
    }

    // Guarda una factura (nueva o existente)
    @PostMapping("/guardar")
    public String guardarFactura(Factura factura, RedirectAttributes redirectAttributes) {
        log.info("Intentando guardar factura con ID: " + factura.getIdFactura()); // O alguna otra propiedad de factura
        try {
            facturaService.save(factura);
            redirectAttributes.addFlashAttribute("todoOk", "Factura guardada exitosamente!");
        } catch (Exception e) {
            log.error("Error al guardar factura: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar factura: " + e.getMessage());
        }
        return "redirect:/factura/listado"; // Redirige al listado de facturas
    }

    // Elimina una factura
    @PostMapping("/eliminar")
    public String eliminarFactura(Factura factura, RedirectAttributes redirectAttributes) {
        log.info("Intentando eliminar factura con ID: " + factura.getIdFactura());
        try {
            facturaService.delete(factura);
            redirectAttributes.addFlashAttribute("todoOk", "Factura eliminada exitosamente!");
        } catch (Exception e) {
            log.error("Error al eliminar factura: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al eliminar factura: " + e.getMessage());
        }
        return "redirect:/factura/listado"; // Redirige al listado de facturas
    }

    // Carga una factura para modificación
    @GetMapping("/modificar/{idFactura}")
    public String modificarFactura(Factura factura, Model model) {
        log.info("Cargando factura para modificación con ID: " + factura.getIdFactura());
        factura = facturaService.getFactura(factura); // Obtener la factura por ID
        model.addAttribute("factura", factura);

        // Si necesitas listar usuarios para el formulario de modificar factura
        if (usuarioService != null) {
            var usuarios = usuarioService.getUsuarios(true);
            model.addAttribute("usuarios", usuarios);
        }
        
        return "factura/modifica"; // Apunta a la vista de modificación de factura
    }
}