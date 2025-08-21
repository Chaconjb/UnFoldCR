package com.UnFold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformacionDeSoporteController {

    @GetMapping("/informacionDeSoporte")
    public String informacionDeSoporte() {
        return "informacionDeSoporte";
    }
}