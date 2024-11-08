package com.petshop.models;

public class Pet {
    private int id;
    private String name;
    private String born;
    private String raca;
    private String especie;
    private Tutor tutor;
    public Pet(
            int id,
            String name,
            String born,
            String raca,
            String especie,
            Tutor tutor
    ) {
        this.id = id;
        this.name = name;
        this.born = born;
        this.raca = raca;
        this.especie = especie;
        this.tutor = tutor;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBorn() {
        return born;
    }
    public void setBorn(String born) {
        this.born = born;
    }
    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
