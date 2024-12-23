package com.petshop.db;

import com.petshop.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class QueryDB extends MethodDB {
    public static ArrayList<Tutor> getAllTutor() {
        ArrayList<Tutor> tutorArrayList = new ArrayList<>();
        String query = "SELECT cpf, name, born, address, phone, email FROM tutor";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tutor tutor = new Tutor(
                        rs.getString("cpf"),
                        rs.getString("name"),
                        rs.getString("born"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                tutorArrayList.add(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutorArrayList;
    }

    public static ArrayList<Vet> getAllVet() {
        ArrayList<Vet> vetArrayList = new ArrayList<>();
        String query = "SELECT cpf, name, born, address, phone, email, especialidade, crmv FROM veterinario";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vet vet = new Vet(
                        rs.getString("cpf"),
                        rs.getString("name"),
                        rs.getString("born"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("especialidade"),
                        rs.getString("crmv")
                );
                vetArrayList.add(vet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vetArrayList;
    }

    public static ArrayList<Pet> getAllPet() {
        ArrayList<Pet> petArrayList = new ArrayList<>();
        String query = "SELECT id, name, born, raca, especie, filepath, tutor FROM pet";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("born"),
                        rs.getString("raca"),
                        rs.getString("especie"),
                        rs.getString("filepath"),
                        getAllTutor().stream().filter(tutor -> {
                            try {
                                return tutor.getCpf().equals(rs.getString("tutor"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst()
                );
                petArrayList.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return petArrayList;
    }

    public static ArrayList<Evento> getAllEvento() {
        ArrayList<Evento> eventArrayList = new ArrayList<>();
        String query = "SELECT id, date, datahora, descricao, responsavel FROM evento ORDER BY date ASC, datahora ASC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento event = new Evento(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("datahora"),
                        rs.getString("descricao"),
                        getAllVet().stream().filter(vet -> {
                            try {
                                return vet.getCpf().equals(rs.getString("responsavel"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst()
                );
                eventArrayList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventArrayList;
    }

    public static ArrayList<Consulta> getAllConsulta() {
        ArrayList<Consulta> consultaArrayList = new ArrayList<>();
        String query = "SELECT id, tipo, descricao, date, datahora, veterinario, pet FROM consulta ORDER BY date ASC, datahora ASC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("descricao"),
                        rs.getString("date"),
                        rs.getString("datahora"),
                        getAllPet().stream().filter(pet -> {
                            try {
                                return (pet.getId() == rs.getInt("pet"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst(),
                        getAllVet().stream().filter(vet -> {
                            try {
                                return vet.getCpf().equals(rs.getString("veterinario"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst()
                );
                consultaArrayList.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultaArrayList;
    }

    public static ArrayList<Cirurgia> getAllCirurgia() {
        ArrayList<Cirurgia> cirurgiaArrayList = new ArrayList<>();
        String query = "SELECT id, tipo, descricao, date, datahora, veterinario, pet FROM cirurgia ORDER BY date ASC, datahora ASC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cirurgia cirurgia = new Cirurgia(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("descricao"),
                        rs.getString("date"),
                        rs.getString("datahora"),
                        getAllPet().stream().filter(pet -> {
                            try {
                                return (pet.getId() == rs.getInt("pet"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst(),
                        getAllVet().stream().filter(vet -> {
                            try {
                                return vet.getCpf().equals(rs.getString("veterinario"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList().getFirst()
                );
                cirurgiaArrayList.add(cirurgia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cirurgiaArrayList;


    }
}