package com.UnFold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MisOpcionesController {

    @GetMapping("/mis_opciones")
    public String misOpciones() {
        return "mis_opciones";
    }
}