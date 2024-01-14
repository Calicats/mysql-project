package com.example.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
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
            contents.close();
        } catch (FileNotFoundException e) {
            System.err.println(filename + ": could not find the file specified");
            // create a new file with the specified name
            try {
                File loginFile = new File(filename);
                loginFile.createNewFile();
                // add some default values
                usernameDB = "root";
                passwordDB = "root";
                // write the default values to the file
                Writer contents = new java.io.FileWriter(loginFile);
                String defaultContents = usernameDB + " " + passwordDB;
                contents.write(defaultContents);
                contents.close();

            } catch (IOException ex) {
                System.err.println(filename + ": could not create the file specified. You may need to create it manually");
            }
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
