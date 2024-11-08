package com.petshop.models;

import java.util.ArrayList;

public class Vet extends Person {
    private String especialidade;
    private String crmv;
    public Vet(
            String cpf,
            String name,
            String born,
            String address,
            String phone,
            String email,
            String especialidade,
            String crmv
    ) {
        super(cpf,name, born, address, phone, email);
        this.especialidade = especialidade;
        this.crmv = crmv;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public String getCrmv() {
        return crmv;
    }
    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }
}
