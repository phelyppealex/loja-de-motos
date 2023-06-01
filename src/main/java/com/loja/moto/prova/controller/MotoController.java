package com.loja.moto.prova.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.loja.moto.prova.service.FileStorageService;
import com.loja.moto.prova.service.MotoService;


@Controller
public class MotoController {
    private FileStorageService fileStorageService;
    private MotoService service;

    public MotoController(FileStorageService fileStorageService, MotoService service) {
        this.fileStorageService = fileStorageService;
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
    public String cadastrar(@ModelAttribute Moto moto, @RequestParam(name = "chk_nova", required = false) boolean chk_nova, @RequestParam(name = "file") MultipartFile file){
        
        Date d = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");

        String dataUpload = formato.format(d);
        dataUpload = dataUpload.replaceAll("/", "_");
        dataUpload = dataUpload.replaceAll(":", "_");

        moto.setImagemURI(dataUpload + file.getOriginalFilename());
        moto.setNova(!chk_nova);
        service.save(moto);

        this.fileStorageService.save(file, dataUpload);

        return "redirect:/";
    }

    @GetMapping("/admin/listarMotos")
    public String rootListar(Model model){
        List<Moto> motos = service.findAll();
        model.addAttribute("motos", motos);
        return "listar";
    }

    @GetMapping("/admin/editarCadastro/{id}")
    public String editarCadastro(@PathVariable(name = "id") Integer id, Model model){
        Optional<Moto> moto = service.findById(id);

        if(moto.isPresent()){
            model.addAttribute("moto", moto.get());
            return "editar";
        }
        return "redirect:/admin/listarMotos";
    }

    @PostMapping("/admin/editar")
    public String editar(@ModelAttribute Moto moto){
        this.service.update(moto);
        return "redirect:/admin/listarMotos";
    }

    @GetMapping("/admin/deletar/{id}")
    public String editar(@PathVariable(name = "id") Integer id){
        Optional<Moto> m = service.findById(id);

        if(m.isPresent()){
            Moto moto = m.get();
            this.service.delete(id);
        }
        return "redirect:/admin/listarMotos";
    }
}
