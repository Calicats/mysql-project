package com.example.sql;

import java.sql.*;
import java.sql.Date;
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

    public static int getIdByCurs(Connection connection, String curs) throws Exception
    {
        String query = "SELECT idCurs FROM Curs WHERE LOWER(numeCurs) = LOWER(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, curs);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }
        throw new RuntimeException("Cannot fetch the id of " + curs + "!");
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

    public static boolean existsGrup(Connection connection, String numeGrup) throws Exception
    {
        String query = "SELECT numeGrup FROM GrupStudiu WHERE LOWER(numeGrup) = LOWER(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
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

    public static boolean existsActivity(Connection connection, String curs, String tip) throws Exception
    {
        int idCurs = getIdByCurs(connection, curs);
        String query = "SELECT tip, idCurs FROM Activitate WHERE tip = ? AND idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tip);
        preparedStatement.setInt(2, idCurs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsActivitySensitive(Connection connection, String curs, String tip) throws Exception
    {
        int idCurs = getIdByCurs(connection, curs);
        String query = "SELECT tip, idCurs FROM Activitate WHERE LOWER(tip) = LOWER(?) AND idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tip);
        preparedStatement.setInt(2, idCurs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsCursLowerCase(Connection connection, String curs) throws Exception
    {
        String query = "SELECT numeCurs FROM Curs WHERE LOWER(numeCurs) = LOWER(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, curs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsCurs(Connection connection, String curs) throws Exception
    {
        String query = "SELECT numeCurs FROM Curs WHERE LOCATE(?, numeCurs) > 0";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, curs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsProfesorInActivity(Connection connection, String username, String curs, String tip) throws Exception
    {
        int idProfesor = getIdByUsername(connection, "profesor", username);
        int idCurs = getIdByCurs(connection, curs);
        String query = "SELECT tip, idCurs, idProfesor FROM Activitate WHERE LOWER(tip) = LOWER(?) AND idCurs = ? AND idProfesor = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tip);
        preparedStatement.setInt(2, idCurs);
        preparedStatement.setInt(3, idProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsProfesorInActivity(Connection connection, String username, String curs) throws Exception
    {
        int idProfesor = getIdByUsername(connection, "profesor", username);
        int idCurs = getIdByCurs(connection, curs);
        String query = "SELECT * FROM Activitate WHERE idProfesor = ? AND idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idProfesor);
        preparedStatement.setInt(2, idCurs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsStudentInParticipant(Connection connection, String usernameProfesor, String usernameStudent, String tip) throws Exception
    {
        int idStudent = getIdByUsername(connection, "Student", usernameStudent);
        int idProfesor = getIdByUsername(connection, "Profesor", usernameProfesor);
        int idActivitate = getIdActivitate(connection, tip, idProfesor);

        String query = "SELECT PA.idStudent " +
                "FROM ParticipantActivitate PA " +
                "JOIN Activitate A ON PA.idActivitate = A.idActivitate " +
                "WHERE PA.idStudent = ? AND PA.idProfesor = ? AND PA.idActivitate = ? AND A.tip = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idStudent);
        preparedStatement.setInt(2, idProfesor);
        preparedStatement.setInt(3, idActivitate);
        preparedStatement.setString(4, tip);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsStudentInParticipantForGroup(Connection connection, String username, String curs) throws Exception
    {
        int idStudent = getIdByUsername(connection, "student", username);
        String query = "SELECT * FROM ParticipantActivitate pa " +
                "JOIN Activitate a ON pa.idActivitate = a.idActivitate " +
                "JOIN Curs c ON a.idCurs = c.idCurs " +
                "WHERE pa.idStudent = ? AND c.numeCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idStudent);
        preparedStatement.setString(2, curs);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static String[][] getMembersStudent(Connection connection)
    {
        String query = "SELECT MG.username " +
                "FROM MembruGrupStudiu MG " +
                "JOIN Student S ON MG.username = S.username";

        return getInfoFromQuery(connection, query);
    }

    public static String[][] getMembersProfesor(Connection connection)
    {
        String query = "SELECT MG.username " +
                "FROM MembruGrupStudiu MG " +
                "JOIN Profesor P ON MG.username = P.username";

        return getInfoFromQuery(connection, query);
    }

    public static String[][] getCatalogOnIdActivitate(Connection connection, int id)
    {
        String query = "SELECT " +
                "    NS.idNoteStudent, " +
                "    NS.nota, " +
                "    S.username AS usernameStudent, " +
                "    NS.idActivitate " +
                "FROM " +
                "    NoteStudent NS " +
                "    JOIN Student S ON NS.idStudent = S.idStudent " +
                "WHERE " +
                "    NS.idActivitate = ?";

        return getInfoFromQueryWithInt(connection, query, id);
    }

    public static String[][] getGrup(Connection connection)
    {
        String query = "SELECT GS.idGrupStudiu, " +
                "GS.numeGrup, " +
                "COALESCE(IG.idIntalnireGrupStudiu, 0), " +
                "COALESCE(IG.dataIntalnire, '1970-01-01') " +
                "FROM GrupStudiu GS " +
                "JOIN IntalnireGrupStudiu IG ON GS.idGrupStudiu = IG.idGrupStudiu";

        return getInfoFromQuery(connection, query);
    }

    public static int getNumberParticipants(Connection connection, String groupName) throws Exception
    {
        int idGrup = getIdGrup(connection, groupName);
        String query = "SELECT numarParticipanti FROM IntalnireGrupStudiu WHERE idGrup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getInt(1);
    }

    public static int getIdGrup(Connection connection, String numeGrup) throws Exception
    {
        String query = "SELECT idGrupStudiu FROM grupstudiu WHERE numeGrup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt("idGrupStudiu");
        }
        throw new RuntimeException("Invalid group name!");
    }

    public static int getIdIntalnire(Connection connection, String numeGrup) throws Exception
    {
        int idGrup = getIdGrup(connection, numeGrup);
        String query = "SELECT idIntalnireGrupStudiu FROM IntalnireGrupStudiu WHERE idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }
        return -1;
    }

    public static boolean existsIntalnire(Connection connection, String numeGrup) throws Exception
    {
        String query = "SELECT COUNT(*) AS meetingCount " +
                "FROM IntalnireGrupStudiu IGS " +
                "JOIN GrupStudiu GS ON IGS.idGrupStudiu = GS.idGrupStudiu " +
                "WHERE GS.numeGrup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsIntalnireForUser(Connection connection, String username) throws Exception
    {
        String query = "SELECT GS.numeGrup, " +
                "COALESCE(IG.dataIntalnire, 'No Meeting') AS dataIntalnire, " +
                "COALESCE(IG.numarParticipanti, 0) AS numarParticipanti " +
                "FROM MembruGrupStudiu MG " +
                "JOIN GrupStudiu GS ON MG.idGrupStudiu = GS.idGrupStudiu " +
                "LEFT JOIN IntalnireGrupStudiu IG ON GS.idGrupStudiu = IG.idGrupStudiu " +
                "WHERE MG.username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static String[][] getMessages(Connection connection, String numeGrup) throws Exception
    {
        int idGrup = getIdGrup(connection, numeGrup);
        String query = "SELECT numeUtilizator, textMesaj FROM MesajGrupStudiu WHERE idGrupStudiu = ?";

        return getInfoFromQueryWithInt(connection, query, idGrup);
    }

    public static String[][] getGroupMeetings(Connection connection, String username)
    {
        String query = "SELECT GS.numeGrup, " +
                "COALESCE(IG.dataIntalnire, 'No Meeting') AS dataIntalnire, " +
                "COALESCE(IG.numarParticipanti, 0) AS numarParticipanti " +
                "FROM MembruGrupStudiu MG " +
                "JOIN GrupStudiu GS ON MG.idGrupStudiu = GS.idGrupStudiu " +
                "LEFT JOIN IntalnireGrupStudiu IG ON GS.idGrupStudiu = IG.idGrupStudiu " +
                "WHERE MG.username = ?";

        return getInfoFromQueryWithString(connection, query, username);
    }

    public static boolean existsInMeet(Connection connection, String numeGrup, String username) throws Exception
    {
        int idGrup = getIdGrup(connection, numeGrup);
        int idIntalnire = getIdIntalnire(connection, numeGrup);
        String query = "SELECT * FROM MembruIntalnireGrupStudiu WHERE username = ? AND idIntalnireGrupStudiu = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idIntalnire);
        preparedStatement.setInt(3, idGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static String getMeetingDate(Connection connection, String numeGrup) throws Exception
    {
        int idIntalnire = getIdIntalnire(connection, numeGrup);
        int idGrup = getIdGrup(connection, numeGrup);
        String query = "SELECT dataIntalnire FROM intalniregrupstudiu WHERE idIntalnireGrupStudiu = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idIntalnire);
        preparedStatement.setInt(2, idGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            Date date = resultSet.getDate(1);
            if(date != null)
            {
                return date.toString();
            }
            return "XXXXX";
        }
        return "NaN";
    }

    public static String getMeetingTime(Connection connection, String numeGrup) throws Exception
    {
        int idIntalnire = getIdIntalnire(connection, numeGrup);
        int idGrup = getIdGrup(connection, numeGrup);
        String query = "SELECT ora, minut FROM intalniregrupstudiu WHERE idIntalnireGrupStudiu = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idIntalnire);
        preparedStatement.setInt(2, idGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            int hour = resultSet.getInt(1);
            int minute = resultSet.getInt(2);
            String hourString = "-1", minuteString = "-1";
            if(hour < 10)
            {
                hourString = "0" + hour;
            }
            if(minute < 10)
            {
                minuteString = "0" + minute;
            }
            return hourString + ":" + minuteString;
        }
        return "NaN";
    }

    public static String getRolFromUser(Connection connection, String username) throws Exception
    {
        String query = "SELECT R.numeRol " +
                "FROM Utilizator U " +
                "JOIN Rol R ON U.id_rol = R.idRol " +
                "WHERE U.username = ?";
        String role = "null";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getString(1);
        }
        return role;
    }

    public static String[][] getSuggestions(Connection connection, String numeCurs)
    {
        String query = "SELECT " +
                "    S.username AS username, " +
                "    'Student' as rol " +
                "FROM " +
                "    Student S " +
                "JOIN " +
                "    Utilizator U ON S.idStudent = U.idUtilizator " +
                "JOIN " +
                "    ParticipantActivitate PA ON U.idUtilizator = PA.idStudent " +
                "JOIN " +
                "    Activitate A ON PA.idActivitate = A.idActivitate " +
                "JOIN " +
                "    Curs C ON A.idCurs = C.idCurs " +
                "LEFT JOIN " +
                "    MembruGrupStudiu MG ON U.idUtilizator = MG.idMembruGrupStudiu " +
                "WHERE " +
                "    MG.idMembruGrupStudiu IS NULL AND C.numeCurs = ? " +
                "UNION " +
                "SELECT " +
                "    P.username AS username, " +
                "    'Profesor' as rol " +
                "FROM " +
                "    Profesor P " +
                "JOIN " +
                "    Utilizator U ON P.idProfesor = U.idUtilizator " +
                "JOIN " +
                "    Activitate A ON P.idProfesor = A.idProfesor " +
                "JOIN " +
                "    Curs C ON A.idCurs = C.idCurs " +
                "LEFT JOIN " +
                "    MembruGrupStudiu MG ON U.idUtilizator = MG.idMembruGrupStudiu " +
                "WHERE " +
                "    MG.idMembruGrupStudiu IS NULL AND C.numeCurs = ?";

        return getInfoFromQueryWithDoubleString(connection, query, numeCurs, numeCurs);
    }

    public static boolean existsUser(Connection connection, String username) throws Exception
    {
        String query = "SELECT * FROM Utilizator WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean existsUserInGroup(Connection connection, String username, String numeGrup) throws Exception
    {
        int idGrupStudiu = getIdGrup(connection, numeGrup);
        String query = "SELECT * FROM MembruGrupStudiu WHERE username = ? AND idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idGrupStudiu);

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
        String query = "SELECT " +
                "A.idActivitate, " +
                "P.username, " +
                "C.numeCurs, " +
                "A.tip, " +
                "C.descriere, " +
                "C.nrMaximStudenti, " +
                "A.procentNota " +
                "FROM " +
                "Curs C " +
                "JOIN Activitate A ON C.idCurs = A.idCurs " +
                "JOIN Profesor P ON A.idProfesor = P.idProfesor";

        return getInfoFromQuery(connection, query);
    }

    public static String[][] getActivitateTableInfoOnUser(Connection connection, String username)
    {
        String query = "SELECT " +
                "A.idActivitate, " +
                "P.username, " +
                "C.numeCurs, " +
                "A.tip, " +
                "C.descriere, " +
                "C.nrMaximStudenti, " +
                "A.procentNota " +
                "FROM " +
                "Curs C " +
                "JOIN Activitate A ON C.idCurs = A.idCurs " +
                "JOIN Profesor P ON A.idProfesor = P.idProfesor " +
                "WHERE LOCATE(?, username) > 0";

        return getInfoFromQueryWithString(connection, query, username);
    }

    public static String[][] getActivitateTableInfoOnActivity(Connection connection, String activity)
    {
        String query = "SELECT " +
                "A.idActivitate, " +
                "P.username, " +
                "C.numeCurs, " +
                "A.tip, " +
                "C.descriere, " +
                "C.nrMaximStudenti, " +
                "A.procentNota " +
                "FROM " +
                "Curs C " +
                "JOIN Activitate A ON C.idCurs = A.idCurs " +
                "JOIN Profesor P ON A.idProfesor = P.idProfesor " +
                "WHERE LOCATE(?, numeCurs) > 0";

        return getInfoFromQueryWithString(connection, query, activity);
    }

    public static String[][] getCursTableInfo(Connection connection)
    {
        String query = "SELECT * FROM Curs";

        return getInfoFromQuery(connection, query);
    }

    public static int getCursTableSize(Connection connection) throws Exception
    {
        String query = "SELECT COUNT(*) FROM Curs";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }

        return 0;
    }

    public static String[][] getParticipantiTableInfo(Connection connection)
    {
        String query = "SELECT " +
                "    Profesor.username AS usernameProfesor, " +
                "    Curs.numeCurs, " +
                "    Activitate.tip, " +
                "    Curs.nrMaximStudenti, " +
                "    COALESCE(ParticipantActivitate.numarParticipanti, 0) AS nrParticipanti " +
                "FROM " +
                "    Profesor " +
                "JOIN Activitate ON Profesor.idProfesor = Activitate.idProfesor " +
                "JOIN Curs ON Activitate.idCurs = Curs.idCurs " +
                "LEFT JOIN ParticipantActivitate ON Activitate.idActivitate = ParticipantActivitate.idActivitate";

        return getInfoFromQuery(connection, query);
    }
    public static String[][] getUsersFromActivityPanel(Connection connection, String username)
    {
        String query = "SELECT " +
                "P.username, " +
                "C.numeCurs, " +
                "A.tip, " +
                "C.descriere, " +
                "C.nrMaximStudenti, " +
                "A.procentNota " +
                "FROM " +
                "Profesor P " +
                "JOIN Curs C ON P.idProfesor = C.idProfesor " +
                "JOIN Activitate A ON C.idCurs = A.idCurs " +
                "WHERE LOCATE(?, username) > 0";
        return getInfoFromQueryWithString(connection, query, username);
    }

    public static String[][] getUsersFromUsersPanel(Connection connection, String username, String tableName)
    {
        String query = getQueryForAllInfoOnUser(tableName);

        return getInfoFromQueryWithString(connection, query, username);
    }

    private static String[][] getInfoFromQueryWithString(Connection connection, String query, String str)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, str);
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

    private static String[][] getInfoFromQueryWithDoubleString(Connection connection, String query, String str1, String str2)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, str1);
            preparedStatement.setString(2, str2);
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

    private static String[][] getInfoFromQueryWithInt(Connection connection, String query, int num)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);
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
    @Deprecated
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

    public static int getIdActivitate(Connection connection, int idProfesor) throws Exception
    {
        String query = "SELECT idActivitate FROM Activitate WHERE idProfesor = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }

        return -1;
    }

    public static int getIdActivitate(Connection connection, String tip, int idProfesor) throws Exception
    {
        String query = "SELECT idActivitate FROM Activitate WHERE tip = ? AND idProfesor = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tip);
        preparedStatement.setInt(2, idProfesor);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return resultSet.getInt(1);
        }

        return -1;
    }

    public static boolean existsMeeting(Connection connection, String numeGrup) throws Exception
    {
        String query = "SELECT * FROM IntalnireGrupStudiu IGS " +
                "JOIN GrupStudiu GS ON GS.idGrupStudiu = IGS.idGrupStudiu " +
                "WHERE GS.numeGrup = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeGrup);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static int getNrMaximStudenti(Connection connection, int idActivitate) throws SQLException {
        String nrMaximStudentiQuery = "SELECT nrMaximStudenti FROM Curs WHERE idCurs = (SELECT idCurs FROM Activitate WHERE idActivitate = ?)";
        try (PreparedStatement nrMaximStudentiStatement = connection.prepareStatement(nrMaximStudentiQuery)) {
            nrMaximStudentiStatement.setInt(1, idActivitate);
            ResultSet nrMaximStudentiResult = nrMaximStudentiStatement.executeQuery();
            return nrMaximStudentiResult.next() ? nrMaximStudentiResult.getInt(1) : 0;
        }
    }

    public static int getCurrentParticipants(Connection connection, int idActivitate) throws SQLException {
        String countQuery = "SELECT numarParticipanti FROM ParticipantActivitate WHERE idActivitate = ?";
        try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            countStatement.setInt(1, idActivitate);
            ResultSet countResult = countStatement.executeQuery();
            return countResult.next() ? countResult.getInt(1) : 0;
        }
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
