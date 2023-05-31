package com.loja.moto.prova.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.loja.moto.prova.model.Moto;
import com.loja.moto.prova.service.MotoService;


@Controller
public class MotoController {

    private MotoService service;

    public MotoController(MotoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);
        return "index";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model){
        Moto moto = new Moto();
        model.addAttribute("moto", moto);
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute Moto moto, @RequestParam(name = "chk_nova", required = false) boolean chk_nova, Model model){
        moto.setNova(!chk_nova);
        service.save(moto);

        return "redirect:/";
    }

    @GetMapping("/listarMotos")
    public String listar(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);
        return "listar";
    }

    @GetMapping("/editarMoto/{id}")
    public String editar(@PathVariable(name = "id") String identificador, Model model){
        Integer id = Integer.parseInt(identificador);
        Optional<Moto> moto = service.findById(id);

        if(moto.isPresent()){
            model.addAttribute("moto", moto);
            return "/";
        }
        return "redirect:/";
    }
}
