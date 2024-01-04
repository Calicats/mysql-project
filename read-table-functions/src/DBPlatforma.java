/* ENTRY POINT */

import java.sql.Connection;

public class DBPlatforma {
    public static void main(String[] args) {
        DBLogin login = new DBLogin("login.txt");
        connection = login.connectToDB();
        // TODO: Populate the tables and test getTable() methods
    }

    public static Connection getConnection() {
        return connection;
    }

    private static Connection connection;
}
