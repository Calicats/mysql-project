package com.example.sql;

import java.sql.*;

public class Update {
    // TODO: Update an entry from a table
    public static int updateEntry(Connection connection, String username, String tableName, String columnName, String entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setString(2, username);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public static void updateEntry(Connection connection, String username, String tableName, String columnName, int entry) throws Exception
    {
        String query = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entry);
        preparedStatement.setString(2, username);
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected);
    }
}
