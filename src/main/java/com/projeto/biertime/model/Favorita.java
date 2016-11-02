package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Favorita implements Parseable {
    
    private Long id;
    private Usuario usuario;
    private Cerveja cerveja;
    private Long pontuacao;
    private String curtida;
    private String comentario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cerveja getCerveja() {
        return cerveja;
    }

    public void setCerveja(Cerveja cerveja) {
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
        return String.format("{\"id\":%s, \"pontuacao\":\"%s\", \"cerveja\": %s,\"usuario\": %s, \"curtida\":\"%s\", \"comentario\":\"%s\"}", id, pontuacao, cerveja, usuario, curtida, comentario);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        usuario = new Usuario(Utils.parseLong(dados.get("usuario")));
        cerveja = new Cerveja(Utils.parseLong(dados.get("cerveja")));
        pontuacao = Utils.parseLong(dados.get("pontuacao"));
        curtida = dados.get("curtida");
        comentario = dados.get("comentario");
    }
}
