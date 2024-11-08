package com.petshop.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDB extends MethodDB {
    public static void delete (String tableName, String id){
        String execCommand;
        if (tableName.equals("veterinario") || tableName.equals("tutor")){
            execCommand = "DELETE FROM "+tableName+" WHERE cpf= '"+id+"'";
        } else {
            execCommand = "DELETE FROM "+tableName+" WHERE id="+id;
        }
        try(Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(execCommand);
            System.out.println(id + " successfully deleted from " + tableName);
        } catch (SQLException e) {
            System.out.println("Error deleting "+ id + " from " + tableName + " : " + e.getMessage());
        }
    }
}
