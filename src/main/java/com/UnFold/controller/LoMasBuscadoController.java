package com.UnFold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoMasBuscadoController {

    @GetMapping("/loMasBuscado")
    public String loMasBuscado() {
        return "loMasBuscado";
    }
}