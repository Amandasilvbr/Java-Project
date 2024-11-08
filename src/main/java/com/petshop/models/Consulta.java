package com.petshop.models;

public class Consulta extends Procedimento {
    public Consulta(
            int id,
            String tipo,
            String descricao,
            String date,
            Pet pet,
            Vet vet
    ) {
        super(id, tipo, descricao, date, pet, vet);
    }
}