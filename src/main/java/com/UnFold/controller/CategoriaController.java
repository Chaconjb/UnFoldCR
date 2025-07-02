package com.UnFold.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import com.tienda.service.FirebaseStorageService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = categoriaService.getCategoria(false);

        model.addAttribute("totalCategorias", lista.size());
        model.addAttribute("categorias", lista);

        return "/categoria/listado";
    }

    @Autowired
    private FirebaseStorageService FirebaseStorageService;
    @Autowired
    private MessageSource MessageSource;

    @PostMapping("/guardar")
    public String guardar(Categoria categoria,
            @RequestParam MultipartFile imagenFile,
                      RedirectAttributes redirectAttributes) {

        if (!imagenFile.isEmpty()) {//! sirve para decir por ejemplo(si no)
            categoriaService.save(categoria);
            String rutaImagen = FirebaseStorageService.cargaImagen(imagenFile, "categoria", categoria.getIdCategoria());
            categoria.setRutaImagen(rutaImagen);

        }
        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute("todoOK", MessageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Categoria categoria,
            RedirectAttributes redirectAttributes) {
        categoria = categoriaService.getCategoria(categoria);
        if (categoria == null) {
            redirectAttributes.addFlashAttribute("error", MessageSource.getMessage("categoria.error01", null, Locale.getDefault()));

        }
        else if (false){ 
            redirectAttributes.addFlashAttribute("error", MessageSource.getMessage("categoria.error02", null, Locale.getDefault()));
        
        }
        else if (categoriaService.delete(categoria)){ 
            redirectAttributes.addFlashAttribute("todoOk", MessageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        
        }
        else{ 
            redirectAttributes.addFlashAttribute("error", MessageSource.getMessage("categoria.error04", null, Locale.getDefault()));
        
        }
        categoriaService.delete(categoria);

        return "redirect:/categoria/listado";
    }

    @PostMapping("/modificar")
    public String modificarPost(@RequestParam("idCategoria") Long idCategoria, Model model) {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);

        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);

        return "/categoria/modifica";
    }

}
