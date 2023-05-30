package com.loja.moto.prova.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean deletado;
    @Column(name = "imagem-uri")
    private String imagemURI;
    @NotBlank
    private String modelo;
    @NotBlank
    private String ano;
    private String placa;
    @NotBlank
    private String localidade;
    @NotBlank
    private String cor;
    @NotBlank
    private String preco;
    private boolean nova;
    private int quilometragem;
}