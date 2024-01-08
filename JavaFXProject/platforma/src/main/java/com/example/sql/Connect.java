package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {

    private static String jdbcUrl = "jdbc:mysql://localhost:3306/db_platforma";
    private static String usernameDB = "root";
    private static String passwordDB = "durdeu10";

    /***
     * Conectare la db_platforma
     * @return conexiunea la db_platforma
     */
    public static Connection getConnection()
    {
        if(passwordDB.isEmpty())
        {
            throw new RuntimeException("Please enter the password for the database! in src/main/java/com/example/sql/Connect.java");
        }
        try
        {
            return DriverManager.getConnection(jdbcUrl, usernameDB, passwordDB);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
