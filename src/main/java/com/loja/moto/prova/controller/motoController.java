package com.loja.moto.prova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class motoController {
    
    @GetMapping("/")
    public String home(){
        return "index";
    }
}
