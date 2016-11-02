package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Date;
import java.util.Map;

public class OrdemServico implements Parseable {
    
    private Long id;
    private Date emissao;
    private Cerveja cliente;
    private Date liberado;
    private String descricao;
    private Double valorTotal;
    private String situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Cerveja getCliente() {
        return cliente;
    }

    public void setCliente(Cerveja cliente) {
        this.cliente = cliente;
    }

    public Date getLiberado() {
        return liberado;
    }

    public void setLiberado(Date liberado) {
        this.liberado = liberado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, \"emissao\":\"%s\", \"cliente\": %s, \"liberado\":\"%s\", \"descricao\":\"%s\", \"valorTotal\":%s, \"situacao\":\"%s\"}", id, emissao, cliente, liberado, descricao, valorTotal, situacao);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        emissao = Utils.parseDate(dados.get("emissao"));
        //cliente = new Cerveja(Utils.parseLong(dados.get("cliente")));
        liberado = Utils.parseDate(dados.get("liberado"));
        descricao = dados.get("descricao");
        valorTotal = Utils.parseDouble(dados.get("valortotal"));
        situacao = dados.get("situacao");
    }
    
}
