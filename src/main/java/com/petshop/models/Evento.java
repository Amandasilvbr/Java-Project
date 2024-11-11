package com.petshop.models;

public class Evento {
    private int id;
    private String date;
    private String datahora;
    private String descricao;
    private Vet responsavel;
    public Evento(int id, String date, String datahora, String descricao, Vet responsavel) {
        this.id = id;
        this.date = date;
        this.datahora = datahora;
        this.descricao = descricao;
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
    public String getDatahora() {
        return datahora;
    }
    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Vet getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(Vet responsavel) {
        this.responsavel = responsavel;
    }
}
