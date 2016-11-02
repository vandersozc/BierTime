package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Usuario implements Parseable {
    
    private Long id;
    private String nome;
    private Long idade;

    public Usuario() {
    }
    
    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdade() {
        return idade;
    }

    public void setIdade(Long idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, "
                            + "\"nome\":\"%s\", "
                            + "\"idade\":\"%s\"}", id, nome, idade);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        nome = dados.get("nome");
        idade = Utils.parseLong(dados.get("idade"));
    }
}
