package com.example.sql;

import java.sql.*;
import java.util.*;

public class Query {

    /***
     *
     * @param connection conexiunea la db_platforma
     * @param username utilizatorul introdus
     * @param password parola introdusa
     * @return tabela din care face parte utilizatorul
     */

    public static String validateUser(Connection connection, String username, String password) {
        String query = "SELECT id_rol FROM utilizator WHERE BINARY username = ? AND BINARY parola = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int id_rol = resultSet.getInt("id_rol");
                return switch (id_rol)
                {
                    case 1 -> "superadministrator";
                    case 2 -> "administrator";
                    case 3 -> "profesor";
                    case 4 -> "student";
                    default -> null;
                };
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /***
     *
     * @param connection conexiunea la db_platforma
     * @param tableName numele tabelei
     * @param username numele utilizatorului
     * @param info informatia pe care vrei sa o returnezi
     * @return rezultatul interogarii, sub forma de String
     */

    public static String getSingleInfo(Connection connection, String tableName, String username, String info)
    {
        String query = "SELECT " + info + " FROM " + tableName + " WHERE username = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getString(info);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /***
     *
     * @param connection conexiunea la db_platforma
     * @param tableName tabela de care apartine utilizatorul
     * @param username utilizator
     * @return toate detaliile utilizatorului
     */

    public static String[] getAllInfoOnUser(Connection connection, String tableName, String username)
    {
        String query = getQueryForAllInfoOnUser(tableName);
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if(resultSet.next())
            {
                String[] userInfo = new String[columnCount];
                for(int i = 1; i <= columnCount; ++i)
                {
                    userInfo[i - 1] = resultSet.getString(i);
                }
                return userInfo;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /***
     *
     * @param connection conexiunea la db_platforma
     * @param tableName numele tabelei
     * @return numele coloanelor tabelei, fara id-uri, sub forma de Array de String-uri
     */

    public static String[] getColumnNames(Connection connection, String tableName)
    {
        try
        {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "db_platforma", tableName, null);

            int columnCount = 0;
            while (resultSet.next())
            {
                String columnName = resultSet.getString("COLUMN_NAME");
                if(!columnName.contains("id"))
                {
                    ++columnCount;
                }
            }

            resultSet.beforeFirst();

            String[] columnNames = new String[columnCount];

            int i = 0;
            while (resultSet.next())
            {
                String columnName = resultSet.getString("COLUMN_NAME");
                if(!columnName.contains("id"))
                {
                    columnNames[i++] = columnName;
                }
            }

            return columnNames;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * Creaza tabela de utilizatori
     * @param connection conexiunea la db_platforma
     * @param tableName numele tabelei
     * @return toata informatia din tabela de utilizatori
     */

    public static String[][] getTableInfo(Connection connection, String tableName)
    {
        String query = getQueryForTableInfo(tableName);

        return getInfoFromQuery(connection, query);
    }

    /***
     * Metoda care returneaza daca exista utilizatorul in tabela specificata
     * @param connection conexiunea la db_platforma
     * @param username username
     * @param tableName numele tabelei
     * @return true (exista user-ul in tabela) / false (nu exista user-ul in tabela)
     * @throws Exception exceptie pe care o poate arunca metoda
     */

    public static boolean userExistsInTable(Connection connection, String username, String tableName) throws Exception
    {
        String query = "SELECT username FROM " + tableName + " WHERE LOCATE(?, username) > 0";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsActivity(Connection connection, String tipActivitate) throws Exception
    {
        String query = "SELECT tipActivitate FROM activitateprofesor WHERE LOCATE(?, tipActivitate) > 0";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tipActivitate);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsActivitySensitive(Connection connection, String tipActivitate) throws Exception
    {
        String query = "SELECT tipActivitate FROM activitateprofesor WHERE tipActivitate = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tipActivitate);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsStudentInActivity(Connection connection, String usernameProfesor, String usernameStudent) throws Exception
    {
        int idStudent = getIdByUsername(connection, "Student", usernameStudent);
        int idProfesor = getIdByUsername(connection, "Profesor", usernameProfesor);
        int idActivitateProfesor = getIdActivitateProfesor(connection, idProfesor);

        String query = "SELECT id_student FROM participantactivitate WHERE id_student = ? AND id_activitate_profesor = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idStudent);
        preparedStatement.setInt(2, idActivitateProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsProfesorInActivity(Connection connection, String usernameProfesor) throws Exception
    {
        int idProfesor = getIdByUsername(connection, "Profesor", usernameProfesor);
        int idActivitateProfesor = getIdActivitateProfesor(connection, idProfesor);

        String query = "SELECT id_profesor FROM participantactivitate WHERE id_profesor = ? AND id_activitate_profesor = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idProfesor);
        preparedStatement.setInt(2, idActivitateProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    /***
     * Creaza tabela de activitate profesor
     * @param connection conexiunea la db_platforma
     * @return tabela cu coloanele nume, username, tipActivitate, descriere
     */

    public static String[][] getActivitateTableInfo(Connection connection)
    {
        String query = "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                "FROM Profesor P JOIN ActivitateProfesor AP ON P.idProfesor = AP.id_profesor";

        return getInfoFromQuery(connection, query);
    }

    /***
     * Creaza tabela de participanti
     * @param connection conexiunea la db_platforma
     * @return tabela sub forma de String[][]
     */

    public static String[][] getParticipantiTableInfo(Connection connection)
    {
        String query = "SELECT " +
                "P.username AS usernameProfesor, " +
                "AP.tipActivitate, " +
                "AP.descriere, " +
                "COUNT(PA.id_student) AS nrStudenti " +
                "FROM " +
                "ActivitateProfesor AP " +
                "JOIN " +
                "Profesor P ON AP.id_profesor = P.idProfesor " +
                "LEFT JOIN " +
                "ParticipantActivitate PA ON AP.idActivitateProfesor = PA.id_activitate_profesor " +
                "GROUP BY " +
                "AP.idActivitateProfesor, P.username, AP.tipActivitate, AP.descriere";

        return getInfoFromQuery(connection, query);
    }

    /***
     * Creaza rezultatul interogarii la tabela de activitati de la un username
     * @param connection conexiunea la db_platforma
     * @return tabela sub forma de String[][]
     */

    public static String[][] getActivityTableFromUsername(Connection connection, String username)
    {
        String query = "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                "FROM " +
                "Profesor P " +
                "JOIN " +
                "ActivitateProfesor AP " +
                "ON P.idProfesor = AP.id_profesor " +
                "WHERE LOCATE(?, username) > 0";
        return getInfoFromQueryWithStringInput(connection, query, username);
    }

    /***
     * Creaza rezultatul interogarii la tabela de activitati de la o activitate
     * @param connection conexiunea la db_platforma
     * @return tabela sub forma de String[][]
     */

    public static String[][] getActivityTableFromActivity(Connection connection, String activity)
    {
        String query = "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                "FROM " +
                "Profesor P " +
                "JOIN " +
                "ActivitateProfesor AP " +
                "ON P.idProfesor = AP.id_profesor " +
                "WHERE LOCATE(?, tipActivitate) > 0";
        return getInfoFromQueryWithStringInput(connection, query, activity);
    }

    public static String[][] getUsersFromUsersPanel(Connection connection, String username, String tableName)
    {
        String query = getQueryForAllInfoOnUser(tableName);

        return getInfoFromQueryWithStringInput(connection, query, username);
    }

    private static String[][] getInfoFromQueryWithStringInput(Connection connection, String query, String string)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, string);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            return generateTable(resultSet, columnCount);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Metoda ajutatoare pentru a crea tabelele de utilizatori si activitate profesor
     * @param connection conexiunea la db_platforma
     * @param query interogarea
     * @return una din tabelele specificate
     */

    private static String[][] getInfoFromQuery(Connection connection, String query)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            return generateTable(resultSet, columnCount);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String[][] getAllActivitiesTableFromSelect(Connection connection, String tipActivitate) throws Exception
    {
        String query = "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                "FROM Profesor P JOIN ActivitateProfesor AP ON P.idProfesor = AP.id_profesor WHERE tipActivitate = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tipActivitate);
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        return generateTable(resultSet, columnCount);
    }

    public static String[][] getAllActivitiesTableFromSearch(Connection connection, String descriere) throws Exception
    {
        String query = "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                "FROM Profesor P JOIN ActivitateProfesor AP ON P.idProfesor = AP.id_profesor WHERE LOCATE(?, AP.descriere) > 0";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, descriere);
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        return generateTable(resultSet, columnCount);
    }

    private static String[][] generateTable(ResultSet resultSet, int columnCount) throws Exception
    {
        List<String[]> rows = new ArrayList<>();

        while(resultSet.next())
        {
            String[] rowData = new String[columnCount];

            for(int i = 1; i <= columnCount; i++)
            {
                rowData[i - 1] = resultSet.getString(i);
            }

            rows.add(rowData);
        }

        String[][] result = new String[rows.size()][];
        rows.toArray(result);

        return result;
    }

    public static int getIdByUsername(Connection connection, String username) throws Exception
    {
        String query = "SELECT idUtilizator FROM utilizator WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
        {
            return resultSet.getInt("idUtilizator");
        }

        return -1;
    }

    public static int getIdByUsername(Connection connection, String tableName, String username) throws Exception
    {
        String query = "SELECT id" + tableName + " FROM " + tableName + " WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }

        return -1;
    }

    public static int getIdActivitateProfesor(Connection connection, int idProfesor) throws Exception
    {
        String query = "SELECT idActivitateProfesor FROM ActivitateProfesor WHERE id_profesor = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }

        return -1;
    }

    /***
     * Metoda ajutatoare pentru getTableInfo
     * @param str numele tabelei
     * @return interogarea necesara pentru getTableInfo
     */

    private static String getQueryForTableInfo(String str)
    {
        return switch(str)
        {
            case "Superadministrator" -> "SELECT * FROM superadministrator";
            case "Administrator" -> "SELECT * FROM administrator";
            case "Profesor" -> "SELECT * FROM profesor";
            case "Student" -> "SELECT * FROM student";
            default -> null;
        };
    }

    /***
     * Metoda ajutatoare pentru getAllInfoOnUser
     * @param tableName numele tabelei
     * @return interogarea necesara pentru getAllInfoOnUser
     */

    private static String getQueryForAllInfoOnUser(String tableName)
    {
        // am pus cu uppercase ca asa ii un combobox
        return switch(tableName)
        {
            case "Superadministrator" -> "SELECT * FROM superadministrator WHERE LOCATE(?, username) > 0";
            case "Administrator" -> "SELECT * FROM administrator WHERE LOCATE(?, username) > 0";
            case "Profesor" -> "SELECT * FROM profesor WHERE LOCATE(?, username) > 0";
            case "Student" -> "SELECT * FROM student WHERE LOCATE(?, username) > 0";
            case "activitateprofesor" -> "SELECT P.nume, P.username, AP.tipActivitate, AP.descriere, AP.nrMaximStudenti " +
                    "FROM Profesor P JOIN ActivitateProfesor AP ON P.idProfesor = AP.id_profesor WHERE username = ?";
            default -> null;
        };
    }
}
