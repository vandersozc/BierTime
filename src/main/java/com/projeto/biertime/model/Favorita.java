package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Favorita implements Parseable {
    
    private Long id;
    private Long usuario;
    private Long cerveja;
    private Long pontuacao;
    private String curtida;
    private String comentario;

    public Favorita() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getCerveja() {
        return cerveja;
    }

    public void setCerveja(Long cerveja) {
        this.cerveja = cerveja;
    }

    public Long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getCurtida() {
        return curtida;
    }

    public void setCurtida(String curtida) {
        this.curtida = curtida;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, "
                + "\"usuario\":\"%s\", "
                + "\"cerveja\":\"%s\", "
                + "\"pontuacao\":\"%s\", "
                + "\"curtida\":\"%s\", "
                + "\"comentario\":\"%s\"}", id, usuario, cerveja, pontuacao, curtida, comentario);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        usuario = Utils.parseLong(dados.get("usuario"));
        cerveja = Utils.parseLong(dados.get("cerveja"));
        pontuacao = Utils.parseLong(dados.get("pontuacao"));
        curtida = dados.get("curtida");
        comentario = dados.get("comentario");
    }
}
