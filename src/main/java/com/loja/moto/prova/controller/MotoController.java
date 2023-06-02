package com.loja.moto.prova.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.loja.moto.prova.model.Moto;
import com.loja.moto.prova.service.FileStorageService;
import com.loja.moto.prova.service.MotoService;

import jakarta.validation.Valid;


@Controller
public class MotoController {
    private FileStorageService fileStorageService;
    private MotoService service;
    private List<Moto> carrinho;

    public MotoController(FileStorageService fileStorageService, MotoService service) {
        this.fileStorageService = fileStorageService;
        this.service = service;
        this.carrinho = new ArrayList<Moto>();
    }

    @GetMapping({"/","index"})
    public String home(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);
        model.addAttribute("itensCarrinho", carrinho);
        return "index";
    }

    @GetMapping("/admin/cadastro")
    public String cadastro(Model model){
        Moto moto = new Moto();
        model.addAttribute("moto", moto);
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String cadastrar(@ModelAttribute Moto moto, @RequestParam(name = "chk_nova", required = false) boolean chk_nova, @RequestParam(name = "file") MultipartFile file, Model model){
        Date d = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");

        String dataUpload = formato.format(d);
        dataUpload = dataUpload.replaceAll("/", "_");
        dataUpload = dataUpload.replaceAll(":", "_");

        moto.setImagemURI(dataUpload + file.getOriginalFilename());
        moto.setNova(!chk_nova);
        service.save(moto);

        this.fileStorageService.save(file, dataUpload);

        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String rootListar(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);
        return "listar";
    }

    @GetMapping("/admin/editarCadastro/{id}")
    public String editarCadastro(@PathVariable(name = "id") Integer id, Model model) throws Exception{
        Moto moto = this.service.findById(id);

        model.addAttribute("moto", moto);
        return "editar";
    }

    @PostMapping("/admin/editar")
    public String editar(@ModelAttribute Moto moto){
        this.service.update(moto);
        return "redirect:/admin";
    }

    @GetMapping("/admin/deletar/{id}")
    public String editar(@PathVariable(name = "id") Integer id) throws Exception{

        this.service.delete(id);
        
        return "redirect:/admin";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable("id") Integer id, Model model) throws Exception {
        Moto moto = service.findById(id);

        carrinho.add(moto);
        model.addAttribute("itensCarrinho", carrinho);
        return "index";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model){
        model.addAttribute("itensCarrinho", carrinho);
        return "carrinho";
    }
    
}
