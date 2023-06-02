package com.loja.moto.prova.model;

import java.util.Collection;
import java.util.Collections;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cpf;
    private String username;
    private String password;
    private boolean isAdmin;

    public String getSenha(){
        return this.password;
    }

    public void setSenha(String password){
        this.password = password;
    }
}