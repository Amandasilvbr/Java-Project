package com.petshop.db;

import java.sql.*;

public class SetDB extends MethodDB{
    public SetDB() throws SQLException {
        String execCreateTutor = "CREATE TABLE IF NOT EXISTS tutor ("
                + "cpf TEXT PRIMARY KEY UNIQUE NOT NULL, "
                + "name TEXT NOT NULL, "
                + "born DATE NOT NULL, "
                + "address TEXT NOT NULL, "
                + "phone TEXT NOT NULL, "
                + "email TEXT NOT NULL);";

        String execCreateVeterinario = "CREATE TABLE IF NOT EXISTS veterinario ("
                + "cpf TEXT PRIMARY KEY UNIQUE NOT NULL, "
                + "name TEXT NOT NULL, "
                + "born DATE NOT NULL, "
                + "address TEXT NOT NULL, "
                + "phone TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "especialidade TEXT NOT NULL, "
                + "crmv TEXT NOT NULL);";

        String execCreatePet = "CREATE TABLE IF NOT EXISTS pet ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "born DATE NOT NULL, "
                + "raca TEXT NOT NULL, "
                + "especie TEXT NOT NULL, "
                + "tutor TEXT NOT NULL, "
                + "FOREIGN KEY (tutor) REFERENCES tutor (cpf));";

        String execCreateEvento = "CREATE TABLE IF NOT EXISTS evento ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "date DATE NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "titulo TEXT NOT NULL, "
                + "responsavel TEXT NOT NULL, "
                + "FOREIGN KEY (responsavel) REFERENCES veterinario (crmv));";

        String execCreateConsulta = "CREATE TABLE IF NOT EXISTS consulta ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo TEXT NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "date DATE NOT NULL, "
                + "veterinario TEXT NOT NULL, "
                + "pet INTEGER NOT NULL, "
                + "FOREIGN KEY (veterinario) REFERENCES veterinario (crmv), "
                + "FOREIGN KEY (pet) REFERENCES pet (id));";

        String execCreateCirurgia = "CREATE TABLE IF NOT EXISTS cirurgia ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo TEXT NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "date DATE NOT NULL, "
                + "veterinario TEXT NOT NULL, "
                + "pet INTEGER NOT NULL, "
                + "FOREIGN KEY (veterinario) REFERENCES veterinario (crmv), "
                + "FOREIGN KEY (pet) REFERENCES pet (id));";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(execCreateTutor);
            stmt.execute(execCreateVeterinario);
            stmt.execute(execCreatePet);
            stmt.execute(execCreateEvento);
            stmt.execute(execCreateConsulta);
            stmt.execute(execCreateCirurgia);
            System.out.println("All tables in order.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }

}
