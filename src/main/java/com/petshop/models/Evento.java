package com.petshop.models;

public class Evento {
    private int id;
    private String date;
    private String descricao;
    private String titulo;
    private Vet responsavel;
    public Evento(int id, String date, String descricao, String titulo, Vet responsavel) {
        this.id = id;
        this.date = date;
        this.descricao = descricao;
        this.titulo = titulo;
        this.responsavel = responsavel;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Vet getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(Vet responsavel) {
        this.responsavel = responsavel;
    }
}
