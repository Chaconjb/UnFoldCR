package com.UnFold.controller;

import com.UnFold.domain.Producto;
import com.UnFold.service.CategoriaService;
import com.UnFold.service.ProductoService;
import com.UnFold.service.FirebaseStorageService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("totalProductos", lista.size());
        model.addAttribute("productos", lista);
        
        
        var categorias=categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        
        return "/producto/listado";
    }
    
    @Autowired 
    private FirebaseStorageService firebaseStorageService;
    @Autowired 
    private MessageSource messageSource;

    
    @PostMapping("/guardar")
    public String guardar(Producto producto,
            @RequestParam MultipartFile imagenFile,RedirectAttributes redirectAtributes) {
        if (!imagenFile.isEmpty()) { //si no esta vacío
            productoService.save(producto);
            String rutaImagen=firebaseStorageService.cargaImagen(imagenFile,"producto",producto.getIdProducto());
            producto.setRutaImagen(rutaImagen);
        }
        productoService.save(producto);
        redirectAtributes.addFlashAttribute("todoOk",messageSource.getMessage("mensaje.actualizado",null,Locale.getDefault()));
        
        return "redirect:/producto/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(Producto producto,
            RedirectAttributes redirectAtributes) {
        producto = productoService.getProducto(producto);
        if (producto==null) { //La producto no existe... 
            redirectAtributes.addFlashAttribute("error",messageSource.getMessage("producto.error01",null,Locale.getDefault()));
       } else if (false) { //Esto se actualiza en semana 8 
           redirectAtributes.addFlashAttribute("error",messageSource.getMessage("producto.error02",null,Locale.getDefault()));
       } else if (productoService.delete(producto)) {
           redirectAtributes.addFlashAttribute("todoOk",messageSource.getMessage("mensaje.eliminado",null,Locale.getDefault()));
       } else { //Hubo algún error 
           redirectAtributes.addFlashAttribute("error",messageSource.getMessage("producto.error03",null,Locale.getDefault()));
       }
        return "redirect:/producto/listado";
    }
    
    @PostMapping("/modificar")
    public String modificar(Producto producto,
            Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        
        var categorias=categoriaService.getCategorias(true);
        model.addAttribute("categorias",categorias);
        
        return "/producto/modifica";
    }

}
