package com.UnFold.controller;

import com.UnFold.service.CategoriaService;
import com.UnFold.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String inicio(Model model) {
        log.info("Accediendo a la página de inicio.");

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        var productos = productoService.getProductos(true);
        model.addAttribute("productos", productos);

        return "index";
    }
}
