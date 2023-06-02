package com.loja.moto.prova.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.loja.moto.prova.model.Moto;
import com.loja.moto.prova.service.FileStorageService;
import com.loja.moto.prova.service.MotoService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@Validated
public class MotoController {
    private FileStorageService fileStorageService;
    private MotoService service;

    public MotoController(FileStorageService fileStorageService, MotoService service) {
        this.fileStorageService = fileStorageService;
        this.service = service;
    }

    @GetMapping({"/","index"})
    public String home(Model model, HttpSession sessao, HttpServletRequest request){
        List<Moto> motos = this.service.findAll();
        sessao = request.getSession();

        model.addAttribute("motos", motos);
        if(sessao.getAttribute("carrinho") != null){
            model.addAttribute(
                "itensCarrinho",
                sessao.getAttribute("carrinho")
            );
        }
        return "index";
    }

    @GetMapping("/admin/cadastro")
    public String cadastro(Model model){
        Moto moto = new Moto();
        model.addAttribute("moto", moto);
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String cadastrar(@ModelAttribute @Valid Moto moto, @RequestParam(name = "chk_nova", required = false) boolean chk_nova, @RequestParam(name = "file") MultipartFile file, Model model, Errors errors) {

        if(errors.hasErrors())return "redirect:/index";
        
        String dataUpload = getData(new Date());
        
        dataUpload = dataUpload.replaceAll("/", "_");
        dataUpload = dataUpload.replaceAll(":", "_");
        
        if(file != null){
            moto.setImagemURI(dataUpload + file.getOriginalFilename());
            this.fileStorageService.save(file, dataUpload);
        }
        
        moto.setNova(!chk_nova);
        
        if(moto.getId() != null){
            service.update(moto);
            List<Moto> motos = service.findAll();
            model.addAttribute("motos", motos);
            model.addAttribute("mensagem", "A atualização ocorreu com Sucesso");
            return "listar";
        }
        
        service.save(moto);

        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String rootListar(Model model, HttpSession sessao, HttpServletRequest request){
        sessao = request.getSession();
        List<Moto> motos = service.findAll();
        
        model.addAttribute("motos", motos);
        model.addAttribute("itensCarrinho", sessao.getAttribute("carrinho"));
        
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
    public String adicionarCarrinho(@PathVariable("id") Integer id, Model model, HttpSession sessao, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    public String verCarrinho(Model model, HttpSession sessao, HttpServletRequest request, HttpServletResponse response){
        sessao = request.getSession();
        criarCoockie(response);

        if(sessao.getAttribute("carrinho") == null){
            List<Moto> motos = service.findAll();
            model.addAttribute("motos", motos);
            model.addAttribute("mensagem", "Não existe item no carrinho");
            return "index";
        }
        
        model.addAttribute(
            "itensCarrinho",
            sessao.getAttribute("carrinho")
        );
        
        return "carrinho";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession sessao, HttpServletRequest request, Model model){
        sessao = request.getSession();
        List<Moto> motosCarrinho = (List)sessao.getAttribute("carrinho");
        
        if(!motosCarrinho.isEmpty()){
            for(Moto m : motosCarrinho){
                m.setDate(new Date());
                this.service.update(m);
            }
        }

        sessao.invalidate();

        List<Moto> motos = this.service.findAll();
        model.addAttribute("motos", motos);
        return "index";
    }
    
    public String getData(Date d){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        return formato.format(d);
    }

    public void criarCoockie(HttpServletResponse response){
        Cookie c = new Cookie("visita", getData(new Date()));
        c.setMaxAge(60*60*24);
        response.addCookie(c);
    }
}
