package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Cerveja implements Parseable {
    
    private Long id;
    private String nome;
    private String tipo;
    private String familia;
    private Long amargor;
    private Long cor;
    private Long teor;
    private String observacao;

    public Cerveja() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public Long getAmargor() {
        return amargor;
    }

    public void setAmargor(Long amargor) {
        this.amargor = amargor;
    }

    public Long getCor() {
        return cor;
    }

    public void setCor(Long cor) {
        this.cor = cor;
    }

    public Long getTeor() {
        return teor;
    }

    public void setTeor(Long teor) {
        this.teor = teor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, \"nome\":\"%s\", \"tipo\":\"%s\", \"familia\":\"%s\", \"amargor\":\"%s\", \"cor\":\"%s\", \"teor\":\"%s\", \"observacao\":\"%s\"}"	, 
                id, nome, tipo, familia, amargor, cor, teor, observacao);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        nome = dados.get("nome");
        tipo = dados.get("tipo");
        familia = dados.get("familia");
        amargor = Utils.parseLong(dados.get("amargor"));
        cor = Utils.parseLong(dados.get("cor"));
        teor = Utils.parseLong(dados.get("teor"));
        observacao = dados.get("observacao");
    }
}
