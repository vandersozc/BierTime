package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Cervejaria implements Parseable {
    
    private Long id;
    private String nome;
    private String localizacao;
    private String estado;
    private String pais;

    public Cervejaria() {
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, \"nome\":\"%s\", \"localizacao\":\"%s\", \"estado\":\"%s\", \"pais\":\"%s\"}"	, 
                id, nome, localizacao, estado, pais);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        nome = dados.get("nome");
        localizacao = dados.get("localizacao");
        estado = dados.get("estado");
        pais = dados.get("pais");
    }
}
