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

    public void delete(Integer id){
        Optional<Moto> opMoto = findById(id);

        if(opMoto.isPresent()){
            Moto moto = opMoto.get();
            moto.setDate(new Date());
            this.repository.deleteById(id);
            save(moto);
        }
    }

    public List<Moto> findAll(){
        return this.repository.findAll();
    }

    public Optional<Moto> findById(Integer id){
        Optional<Moto> moto = this.repository.findById(id);
        return moto;
    }
    
}
