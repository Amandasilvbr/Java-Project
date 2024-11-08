package com.petshop;
import com.petshop.core.LoginPage;
import com.petshop.db.SetDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] in) throws SQLException {
        new SetDB();
        new LoginPage().start();
    }
}