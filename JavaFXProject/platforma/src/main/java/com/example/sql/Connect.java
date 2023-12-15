package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/db_platforma";
    private static String usernameDB = "root";
    private static String passwordDB = "Sharldevil16!";
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl, usernameDB, passwordDB);
    }
}
