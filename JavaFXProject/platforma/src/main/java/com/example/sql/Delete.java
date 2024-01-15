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

    public static int deleteCurs(Connection connection, int id) throws Exception
    {
        String update = "DELETE FROM Curs WHERE idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }

    public static int deleteActivitate(Connection connection, int id) throws Exception
    {
        String update = "DELETE FROM Activitate WHERE idActivitate = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }
    @Deprecated
    public static int deleteActivitateProfesor(Connection connection, String username, String tipActivitate, String descriereActivitate) throws Exception
    {
        String update = "DELETE FROM activitateprofesor WHERE tipActivitate = ? AND descriere = ? AND id_profesor = (SELECT idProfesor FROM Profesor WHERE username = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, tipActivitate);
        preparedStatement.setString(2, descriereActivitate);
        preparedStatement.setString(3, username);
        return preparedStatement.executeUpdate();
    }

    public static int deleteGrup(Connection connection, String numeGrup) throws Exception
    {
        String update = "DELETE FROM grupstudiu WHERE numeGrup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, numeGrup);
        return preparedStatement.executeUpdate();
    }

    public static int deleteMembruGrup(Connection connection, String username, String numeGrup) throws Exception
    {
        int idGrupStudiu = Query.getIdGrup(connection, numeGrup);
        String update = "DELETE FROM membrugrupstudiu WHERE username = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idGrupStudiu);
        return preparedStatement.executeUpdate();
    }

    public static int deleteMemberFromMeeting(Connection connection, String numeGrup, String username) throws Exception
    {
        int idGrupStudiu = Query.getIdGrup(connection, numeGrup);
        int idIntalnire = Query.getIdIntalnire(connection, numeGrup);
        String delete = "DELETE FROM MembruIntalnireGrupStudiu WHERE username = ? AND idIntalnireGrupStudiu = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idIntalnire);
        preparedStatement.setInt(3, idGrupStudiu);
        return preparedStatement.executeUpdate();
    }

    public static int deleteMeeting(Connection connection, int id) throws Exception
    {
        String delete = "DELETE FROM IntalnireGrupStudiu WHERE idIntalnireGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }
}
