package com.loja.moto.prova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.loja.moto.prova.model.Usuario;
import com.loja.moto.prova.service.UsuarioService;

import java.util.List;


@Controller
public class UsuarioController {

    UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/cadastrarUsuario")
    public String doCadastrarUsuario(Model model){

        Usuario u = new Usuario();
        model.addAttribute("usuario", u);

        return "cadastrarUsuario";
    }

    @PostMapping("/doSalvarUsuario")
    public String doSalvarUsuario(@ModelAttribute Usuario u){
        service.save(u);

        return "redirect:/";
    }

    @GetMapping("/listUsuarios")
    public String listAll(Model model){
        List<Usuario> usuarios = service.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listUsuarios";
    }

}