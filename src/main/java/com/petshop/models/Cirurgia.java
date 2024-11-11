package com.petshop.models;

public class Cirurgia extends Procedimento {
    public Cirurgia(
            int id,
            String tipo,
            String descricao,
            String date,
            String datahora,
            Pet pet,
            Vet vet
    ) {
        super(id, tipo, descricao, date, datahora, pet, vet);
    }
}
