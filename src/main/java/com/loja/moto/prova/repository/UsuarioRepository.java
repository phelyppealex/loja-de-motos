package com.loja.moto.prova.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.loja.moto.prova.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
