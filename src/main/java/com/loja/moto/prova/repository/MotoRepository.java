package com.loja.moto.prova.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.loja.moto.prova.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Integer> {
    List<Moto> findByModelo(String modelo);
}
