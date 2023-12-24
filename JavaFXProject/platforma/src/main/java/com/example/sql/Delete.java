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

    public static int deleteActivitateProfesor(Connection connection, String username, String tipActivitate, String descriereActivitate) throws Exception
    {
        String update = "DELETE FROM activitateprofesor WHERE tipActivitate = ? AND descriere = ? AND id_profesor = (SELECT idProfesor FROM Profesor WHERE username = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, tipActivitate);
        preparedStatement.setString(2, descriereActivitate);
        preparedStatement.setString(3, username);
        return preparedStatement.executeUpdate();
    }
}
