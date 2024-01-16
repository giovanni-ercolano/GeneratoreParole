package com.example.generatoreparole.model;

public class Squadra {

    private String nome;
    private String giocatori;

    public Squadra(String nome, String giocatori) {
        this.nome = nome;
        this.giocatori = giocatori;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(String giocatori) {
        this.giocatori = giocatori;
    }
}
