package com.petshop.db;

import java.sql.*;

public class SetDB extends MethodDB{
    public SetDB() throws SQLException {
        String execCreateTutor = "CREATE TABLE IF NOT EXISTS tutor ("
                + "cpf VARCHAR(11) PRIMARY KEY UNIQUE NOT NULL, "
                + "name VARCHAR(150) NOT NULL, "
                + "born DATE NOT NULL, "
                + "address VARCHAR(200) NOT NULL, "
                + "phone VARCHAR(20) NOT NULL, "
                + "email VARCHAR(100) NOT NULL);";

        String execCreateVeterinario = "CREATE TABLE IF NOT EXISTS veterinario ("
                + "cpf VARCHAR(11) PRIMARY KEY UNIQUE NOT NULL, "
                + "name VARCHAR(150) NOT NULL, "
                + "born DATE NOT NULL, "
                + "address VARCHAR(200) NOT NULL, "
                + "phone VARCHAR(20) NOT NULL, "
                + "email VARCHAR(100) NOT NULL, "
                + "especialidade VARCHAR(60) NOT NULL, "
                + "crmv VARCHAR(30) NOT NULL);";

        String execCreatePet = "CREATE TABLE IF NOT EXISTS pet ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name VARCHAR(150) NOT NULL, "
                + "born DATE NOT NULL, "
                + "raca VARCHAR(150) NOT NULL, "
                + "especie VARCHAR(150) NOT NULL, "
                + "filepath VARCHAR(200) NOT NULL, "
                + "tutor VARCHAR(11) NOT NULL, "
                + "FOREIGN KEY (tutor) REFERENCES tutor (cpf));";

        String execCreateEvento = "CREATE TABLE IF NOT EXISTS evento ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "date DATE NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "responsavel VARCHAR(11) NOT NULL, "
                + "FOREIGN KEY (responsavel) REFERENCES veterinario (cpf));";

        String execCreateConsulta = "CREATE TABLE IF NOT EXISTS consulta ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo VARCHAR(80) NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "date DATE NOT NULL, "
                + "veterinario VARCHAR(11) NOT NULL, "
                + "pet INTEGER NOT NULL, "
                + "FOREIGN KEY (veterinario) REFERENCES veterinario (cpf), "
                + "FOREIGN KEY (pet) REFERENCES pet (id));";

        String execCreateCirurgia = "CREATE TABLE IF NOT EXISTS cirurgia ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo VARCHAR(80) NOT NULL, "
                + "descricao TEXT NOT NULL, "
                + "date DATE NOT NULL, "
                + "veterinario VARCHAR(11) NOT NULL, "
                + "pet INTEGER NOT NULL, "
                + "FOREIGN KEY (veterinario) REFERENCES veterinario (cpf), "
                + "FOREIGN KEY (pet) REFERENCES pet (id));";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
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
