package com.projeto.biertime.model;

import com.projeto.biertime.util.Utils;
import java.util.Map;

public class Acesso implements Parseable {
    
    private Long id;
    private Long usuario;
    private String login;
    private String senha;

    public Acesso() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s, "
                            + "\"usuario\":\"%s\", "
                            + "\"login\":\"%s\", "
                            + "\"senha\":\"%s\"}", id, usuario, login, senha);
    }

    @Override
    public void parse(Map<String, String> dados) {
        id = Utils.parseLong(dados.get("id"));
        usuario = Utils.parseLong(dados.get("usuario"));
        login = dados.get("login");
        senha = dados.get("senha");
    }
}