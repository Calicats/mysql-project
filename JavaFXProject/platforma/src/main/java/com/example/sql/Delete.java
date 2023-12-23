package com.example.sql;

import java.sql.*;

public class Delete {
    public static int deleteUser(Connection connection, String username, String tableName) throws Exception
    {
        String update = "DELETE FROM " + tableName + " WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, username);
        return preparedStatement.executeUpdate();
    }
}
