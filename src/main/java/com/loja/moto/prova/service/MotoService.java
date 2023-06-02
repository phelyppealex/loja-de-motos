package com.loja.moto.prova.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.loja.moto.prova.model.Moto;
import com.loja.moto.prova.repository.MotoRepository;

@Service
public class MotoService {
    
    private MotoRepository repository;

    public MotoService(MotoRepository repository) {
        this.repository = repository;
    }

    public void save(Moto moto){
        this.repository.save(moto);
    }

    public void update(Moto moto){
        this.repository.deleteById(moto.getId());
        save(moto);
    }

    public void delete(Integer id) throws Exception {
        Moto moto = findById(id);
        moto.setDeletado(new Date());

        this.repository.deleteById(id);
        save(moto);
    }

    public List<Moto> findAll(){
        return this.repository.findAll();
    }

    public Moto findById(Integer id) throws Exception {
        Optional<Moto> moto = this.repository.findById(id);
        
        if(moto.isPresent()){
            return moto.get();
        } else{
            throw new Exception("Moto não encontrada");
        }
    }
    
}
