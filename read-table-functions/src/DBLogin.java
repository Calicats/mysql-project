/* ENTRY POINT: DBPlatforma.java */

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DBLogin {
    public DBLogin(String filename) {
        try {
            Scanner contents = new Scanner(new File(filename));
            username = contents.next();
            password = contents.next();
            contents.close();
        } catch (FileNotFoundException e) {
            System.err.println(filename + ": no such file");
        } catch (NoSuchElementException e) {
            System.err.println(filename + ": incorrect formatting");
            System.err.println("should be username and password separated by whitespace");
        } catch (Exception e) {
            System.err.println("an error occured while reading " + filename);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection connectToDB() {
        if (didConnect) {
            return DBPlatforma.getConnection();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Error loading JDBC Driver: Driver is missing or faulty");
        }
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/db_platforma";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Could not connect to database with URL " + url);
            System.err.println("URL is faulty or access is denied");
        }
        didConnect = true;
        return connection;
    }

    @Override
    public String toString() {
        return "DBLogin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private String username = null;
    private String password = null;
    private static boolean didConnect = false;
}
