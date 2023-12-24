package com.example.sql;

import java.sql.*;

public class Update {

    /***
     * Modifica informatia de la o tabela si valoarea noua setata
     * @param connection conexiunea la db_platforma
     * @param username username
     * @param tableName numele tabelei utilizatorului
     * @param columnName coloana pe care se doreste a efectua modificarea
     * @param entry informatia noua, sub forma de String
     * @return numarul de date modificate
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static int updateEntry(Connection connection, String username, String tableName, String columnName, String entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setString(2, username);
        return preparedStatement.executeUpdate();
    }

    /***
     * Modifica informatia de la o tabela si valoarea noua setata
     * @param connection conexiunea la db_platforma
     * @param username username
     * @param tableName numele tabelei utilizatorului
     * @param columnName coloana pe care se doreste a efectua modificarea
     * @param entry informatia noua, sub forma de int
     * @return numarul de date modificate
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static int updateEntry(Connection connection, String username, String tableName, String columnName, int entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, entry);
        preparedStatement.setString(2, username);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInActivitateProfesor(Connection connection, String username, String columnName, String oldActivity, String oldDescription, String entry) throws Exception
    {
        String update = "UPDATE activitateprofesor SET " + columnName + " = ? WHERE tipActivitate = ? AND descriere = ? AND id_profesor = (SELECT idProfesor FROM Profesor WHERE username = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setString(2, oldActivity);
        preparedStatement.setString(3, oldDescription);
        preparedStatement.setString(4, username);
        return preparedStatement.executeUpdate();
    }
}
