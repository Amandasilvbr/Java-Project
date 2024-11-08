package com.petshop.models;

public abstract class Procedimento {
    private int id;
    private String tipo;
    private String descricao;
    private String date;
    private Pet pet;
    private Vet veterinario;
    public Procedimento(
            int id,
            String tipo,
            String descricao,
            String date,
            Pet pet,
            Vet vet
    ) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.date = date;
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
