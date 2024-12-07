package com.petshop.models;

public abstract class Person {
    private String cpf;
    private String name;
    private String born;
    private String address;
    private String phone;
    private String email;
    public Person(String cpf, String name, String born, String address, String phone, String email) {
        this.cpf = cpf;
        this.name = name;
        this.born = born;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
