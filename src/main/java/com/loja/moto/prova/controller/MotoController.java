package com.loja.moto.prova.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.loja.moto.prova.model.Moto;
import com.loja.moto.prova.service.FileStorageService;
import com.loja.moto.prova.service.MotoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MotoController {
    private FileStorageService fileStorageService;
    private MotoService service;

    public MotoController(FileStorageService fileStorageService, MotoService service, HttpServletRequest request) {
        this.fileStorageService = fileStorageService;
        this.service = service;
    }

    @GetMapping({"/","index"})
    public String home(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);

        model.addAttribute("itensCarrinho");
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

        if(file != null)
            moto.setImagemURI(dataUpload + file.getOriginalFilename());
        
        moto.setNova(!chk_nova);
        
        if(moto.getId() != null){
            service.update(moto);
            return "/admin";
        }
        
        service.save(moto);

        this.fileStorageService.save(file, dataUpload);

        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String rootListar(Model model, HttpServletRequest request, HttpSession sessao){
        sessao = request.getSession();
        List<Moto> motos = service.findAll();
        
        model.addAttribute("motos", motos);
        model.addAttribute("itensCarrinho",sessao.getAttribute("carrinho"));
        
        return "listar";
    }

    @GetMapping("/editar/{id}")
    public String editarCadastro(@PathVariable(name = "id") Integer id, Model model) throws Exception{
        Moto moto = this.service.findById(id);

        model.addAttribute("moto", moto);
        return "editar";
    }

    @GetMapping("/deletar/{id}")
    public String editar(@PathVariable(name = "id") Integer id) throws Exception{
        this.service.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable("id") Integer id, Model model, HttpServletRequest request, HttpSession sessao) throws Exception {
        List<Moto> motos;
        Moto moto = this.service.findById(id);
        sessao = request.getSession();
        
        if(sessao.getAttribute("carrinho") == null){
            motos = new ArrayList<>();
            sessao.setAttribute("carrinho", motos);
        }else{
            motos = (List)sessao.getAttribute("carrinho");
        }

        if(!motos.contains(moto)){
            motos.add(moto);
            sessao.setAttribute("carrinho", motos);
        }

        model.addAttribute("itensCarrinho", motos);
        model.addAttribute("motos", this.service.findAll());
        return "index";
    }

    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model, HttpServletRequest request, HttpSession sessao){
        sessao = request.getSession();
        model.addAttribute(
            "itensCarrinho",
            sessao.getAttribute("carrinho")
        );
        return "carrinho";
    }
    
}
