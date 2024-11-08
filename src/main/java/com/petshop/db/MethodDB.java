package com.petshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MethodDB {
    private static final String url = "jdbc:sqlite:database:db";

    protected Connection getConnection() throws SQLException {
        try {
            System.out.println("Connection started.");
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.out.println("Unable to establish connection to DB: " + ex.getMessage());
            return null;
        }
    }
}
