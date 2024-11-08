package com.petshop.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDB extends MethodDB{
    public static void insertTutor(String cpf, String name, String born, String address, String phone, String email) {
        String insertCommand = "INSERT INTO tutor (cpf, name, born, address, phone, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, born);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Tutor " + name + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        }
    }

    public static void insertVeterinario(String cpf, String name, String born, String address,
                                         String phone, String email, String especialidade, String crmv) {
        String insertCommand = "INSERT INTO veterinario (cpf, name, born, address, phone, email, especialidade, crmv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, born);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            stmt.setString(6, email);
            stmt.setString(7, especialidade);
            stmt.setString(8, crmv);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Vet " + name + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        }
    }

    public static void insertPet(String name, String born, String raca, String especie, String tutor) {
        String insertCommand = "INSERT INTO pet (name, born, raca, especie, tutor) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, name);
            stmt.setString(2, born);
            stmt.setString(3, raca);
            stmt.setString(4, especie);
            stmt.setString(5, tutor);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Pet " + name + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + name + ": " + e.getMessage());
        }
    }

    public static void insertEvento(String date, String descricao, String titulo, String responsavel) {
        String insertCommand = "INSERT INTO evento (date, descricao, titulo, responsavel) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, date);
            stmt.setString(2, descricao);
            stmt.setString(3, titulo);
            stmt.setString(4, responsavel);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Event " + titulo + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + titulo + ": " + e.getMessage());
        }
    }

    public static void insertConsulta(String tipo, String descricao, String date, String veterinario, int pet) {
        String insertCommand = "INSERT INTO consulta (tipo, descricao, date, veterinario, pet) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, tipo);
            stmt.setString(2, descricao);
            stmt.setString(3, date);
            stmt.setString(4, veterinario);
            stmt.setInt(5, pet);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Consulta " + tipo + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting " + tipo + ": " + e.getMessage());
        }
    }

    public static void insertCirurgia(String tipo, String descricao, String date, String veterinario, int pet) {
        String insertCommand = "INSERT INTO cirurgia (tipo, descricao, date, veterinario, pet) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(insertCommand)) {
            // Set parameters for the query
            stmt.setString(1, tipo);
            stmt.setString(2, descricao);
            stmt.setString(3, date);
            stmt.setString(4, veterinario);
            stmt.setInt(5, pet);

            // Execute the insert command
            stmt.executeUpdate();
            System.out.println("Cirurgia " + tipo + " successfully inserted.");
        } catch (SQLException e) {
            System.out.println("Error inserting  " + tipo + ": " + e.getMessage());;
        }
    }


}
