package com.petshop.dto;

public class LoginResponse {

    private String mensagem;
    private String email;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String mensagem, String email, String token) {
        this.mensagem = mensagem;
        this.email = email;
        this.token = token;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}