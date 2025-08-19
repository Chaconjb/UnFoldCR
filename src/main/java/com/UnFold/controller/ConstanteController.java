package com.UnFold.controller;

import com.UnFold.domain.Constante;
import com.UnFold.service.ConstanteService;
import com.UnFold.service.FirebaseStorageService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/constante")
public class ConstanteController {

    @Autowired
    private ConstanteService constanteService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = constanteService.getConstantes();
        model.addAttribute("totalConstantes", lista.size());
        model.addAttribute("constantes", lista);
        return "/constante/listado";
    }

    @Autowired
    private MessageSource messageSource ;
    
    
    
    @PostMapping("/guardar")
    public String guardar(Constante constante,RedirectAttributes redirectAtributes) {
        constanteService.save(constante);
        redirectAtributes.addFlashAttribute("todoOk",messageSource.getMessage("mensaje.actualizado",null,Locale.getDefault()));
        
        return "redirect:/constante/listado";
    }
    
    @PostMapping("/eliminar")
    public String eliminar(Constante constante,
            RedirectAttributes redirectAtributes) {
        constante = constanteService.getConstante(constante);
        if (constante==null) { //La Constante no existe... 
            redirectAtributes.addFlashAttribute("error",messageSource.getMessage("constante.error01",null,Locale.getDefault()));
       } else if (false) { 
           redirectAtributes.addFlashAttribute("error",messageSource.getMessage("constante.error02",null,Locale.getDefault()));
       } else if (constanteService.delete(constante)) {
           redirectAtributes.addFlashAttribute("todoOk",messageSource.getMessage("mensaje.eliminado",null,Locale.getDefault()));
       } else { //Hubo algún error 
           redirectAtributes.addFlashAttribute("error",messageSource.getMessage("constante.error03",null,Locale.getDefault()));
       }
        return "redirect:/Constante/listado";
    }
    
    @PostMapping("/modificar")
    public String modificar(Constante constante,
            Model model) {
        constante = constanteService.getConstante(constante);
        model.addAttribute("constante", constante);
        return "/Constante/modifica";
    }

}
