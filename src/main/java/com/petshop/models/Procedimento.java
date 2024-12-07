package com.petshop.models;

public abstract class Procedimento {
    private int id;
    private String tipo;
    private String descricao;
    private String date;
    private String datahora;
    private Pet pet;
    private Vet veterinario;
    public Procedimento(
            int id,
            String tipo,
            String descricao,
            String date,
            String datahora,
            Pet pet,
            Vet vet
    ) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.date = date;
        this.datahora = datahora;
        this.pet = pet;
        this.veterinario = vet;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public Vet getVeterinario() {
        return veterinario;
    }
    public void setVeterinario(Vet veterinario) {
        this.veterinario = veterinario;
    }
}
