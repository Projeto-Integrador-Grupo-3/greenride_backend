package com.backend.greenride.model;

public class MotoristaLogin {
    private Long id;
    private String nome;
    private String motorista;
    private String senha;
    private String foto;
    private String token;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMotorista() {
        return this.motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

