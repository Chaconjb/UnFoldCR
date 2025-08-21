package com.UnFold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConocenosController {

    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos";
    }
}
