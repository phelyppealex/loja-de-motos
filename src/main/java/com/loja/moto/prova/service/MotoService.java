package com.loja.moto.prova.service;

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
        repository.save(moto);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

    public List<Moto> findAll(){
        return repository.findAll();
    }

    public Optional<Moto> findById(Integer id){
        Optional<Moto> moto = repository.findById(id);

        return moto;
    }
}
