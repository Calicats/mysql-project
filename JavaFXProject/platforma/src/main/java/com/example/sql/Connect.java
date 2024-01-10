package com.example.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Connect {

    private static String usernameDB;
    private static String passwordDB;

    private static void parseLoginDetails(String filename) {
        try {
            Scanner contents = new Scanner(new File(filename));
            usernameDB = contents.next();
            passwordDB = contents.next();
        } catch (FileNotFoundException e) {
            System.err.println(filename + ": could not find the file specified");
        } catch (NoSuchElementException e) {
            System.err.println(filename + ": incorrect formatting");
            System.err.println("should be username and password separated by whitespace");
        } catch (Exception e) {
            System.err.println(filename + ": an unknown error occured");
        }
    }

    /***
     * Conectare la db_platforma
     * @return conexiunea la db_platforma
     */
    public static Connection getConnection()
    {
        try
        {
            // The file "login.txt" should sit at the root of the PROJECT directory
            // That is, ./JavaFX/platforma/login.txt
            parseLoginDetails("login.txt");

            String jdbcUrl = "jdbc:mysql://localhost:3306/db_platforma";
            return DriverManager.getConnection(jdbcUrl, usernameDB, passwordDB);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
