package com.UnFold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UbicacionController {

    @GetMapping("/ubicacion")
    public String ubicacion() {
        return "ubicacion";
    }
}