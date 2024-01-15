package com.example.sql;

import java.sql.*;
import java.time.LocalDate;

public class Insert {

    /***
     * Adauga un Superadministrator
     * @param connection conexiunea la db_platforma
     * @param cnp cnp
     * @param nume nume
     * @param adresa adresa
     * @param nrTelefon numar de telefon
     * @param email email
     * @param username username
     * @param parola parola
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static void addSuperAdmin(Connection connection, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola) throws Exception
    {
        String procedureCall = "{CALL AddNewSuperAdministrator(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, cnp);
        statement.setString(2, nume);
        statement.setString(3, adresa);
        statement.setString(4, nrTelefon);
        statement.setString(5, email);
        statement.setString(6, username);
        statement.setString(7, parola);
        statement.execute();
    }

    /***
     * Adauga un administrator
     * @param connection conexiunea la db_platforma
     * @param cnp cnp
     * @param nume nume
     * @param adresa adresa
     * @param nrTelefon numar de telefon
     * @param email email
     * @param username username
     * @param parola parola
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static void addAdmin(Connection connection, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola) throws Exception
    {
        String procedureCall = "{CALL AddNewAdministrator(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, cnp);
        statement.setString(2, nume);
        statement.setString(3, adresa);
        statement.setString(4, nrTelefon);
        statement.setString(5, email);
        statement.setString(6, username);
        statement.setString(7, parola);
        statement.execute();
    }

    /***
     * Adauga un profesor
     * @param connection conexiunea la db_platforma
     * @param cnp cnp
     * @param nume nume
     * @param departament departament
     * @param nrMinOre numar min de ore sustinute
     * @param nrMaxOre numar max de ore sustinute
     * @param adresa adresa
     * @param nrTelefon numar de telefon
     * @param email email
     * @param username username
     * @param parola parola
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static void addProfesor(Connection connection, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre, String adresa, String nrTelefon, String email, String username, String parola) throws Exception
    {
        String procedureCall = "{CALL AddNewProfesor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, cnp);
        statement.setString(2, nume);
        statement.setString(3, departament);
        statement.setInt(4, nrMinOre);
        statement.setInt(5, nrMaxOre);
        statement.setString(6, adresa);
        statement.setString(7, nrTelefon);
        statement.setString(8, email);
        statement.setString(9, username);
        statement.setString(10, parola);
        statement.execute();
    }

    /***
     * Adauga un student
     * @param connection conexiunea la db_platforma
     * @param cnp cnp
     * @param nume nume
     * @param anStudiu an studiu
     * @param numarOre numarul de ore sustinute
     * @param adresa adresa
     * @param nrTelefon numar de telefon
     * @param email email
     * @param username username
     * @param parola parola
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static void addStudent(Connection connection, String cnp, String nume, int anStudiu, int numarOre, String adresa, String nrTelefon, String email, String username, String parola) throws Exception
    {
        String procedureCall = "{CALL AddNewStudent(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, cnp);
        statement.setString(2, nume);
        statement.setInt(3, anStudiu);
        statement.setInt(4, numarOre);
        statement.setString(5, adresa);
        statement.setString(6, nrTelefon);
        statement.setString(7, email);
        statement.setString(8, username);
        statement.setString(9, parola);
        statement.execute();
    }

    @Deprecated
    public static void addActivitateProfesor(Connection connection, String username, String tipActivitate, String descriere, int nrMaximStudenti) throws Exception
    {
        String procedureCall = "{CALL AddNewActivitateProfesor(?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, username);
        statement.setString(2, tipActivitate);
        statement.setString(3, descriere);
        statement.setInt(4, nrMaximStudenti);
        statement.execute();
    }

    public static void addParticipantActivitate(Connection connection, String usernameProfesor, String usernameStudent, String tip) throws Exception
    {
        int idStudent = Query.getIdByUsername(connection, "Student", usernameStudent);
        int idProfesor = Query.getIdByUsername(connection, "Profesor", usernameProfesor);
        int idActivitate = Query.getIdActivitate(connection, tip, idProfesor);

        if(idProfesor == -1)
        {
            throw new RuntimeException("Could not fetch " + usernameProfesor + "'s id from Profesor table");
        }
        if(idStudent == -1)
        {
            throw new RuntimeException("Could not fetch " + usernameStudent +"'s id from Student table");
        }
        if(idActivitate == -1)
        {
            throw new RuntimeException("Could not fetch " + usernameProfesor + "'s id from ActivitateProfesor table");
        }

        int maxStudents = Query.getNrMaximStudenti(connection, idActivitate);
        int currentStudents = Query.getCurrentParticipants(connection, idActivitate);

        if(currentStudents == maxStudents)
        {
            throw new RuntimeException("Nu mai sunt locuri la activitate!");
        }

        String query = "INSERT INTO ParticipantActivitate (idActivitate, idStudent, idProfesor) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idActivitate);
        preparedStatement.setInt(2, idStudent);
        preparedStatement.setInt(3, idProfesor);
        preparedStatement.executeUpdate();
    }

    public static void addNewCurs(Connection connection, String numeCurs, String descriere, int nrMaxStudenti) throws Exception
    {
        String query = "INSERT INTO Curs(numeCurs, descriere, nrMaximStudenti) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeCurs);
        preparedStatement.setString(2, descriere);
        preparedStatement.setInt(3, nrMaxStudenti);
        preparedStatement.executeUpdate();
    }

    public static void addNewActivitate(Connection connection, String tip, int procentNota, String curs, String username) throws Exception
    {
        int idCurs = Query.getIdByCurs(connection, curs);
        int idProfesor = Query.getIdByUsername(connection, "profesor", username);
        String query = "INSERT INTO Activitate(tip, procentNota, idCurs, idProfesor) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, tip);
        preparedStatement.setInt(2, procentNota);
        preparedStatement.setInt(3, idCurs);
        preparedStatement.setInt(4, idProfesor);
        preparedStatement.executeUpdate();
    }

    public static void addNewGrupStudiu(Connection connection, String numeGrup) throws Exception
    {
        String query = "INSERT INTO GrupStudiu(numeGrup) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, numeGrup);
        preparedStatement.executeUpdate();
    }

    public static void addNewMembruGrup(Connection connection, String numeGrup, String username) throws Exception
    {
        int idGrupStudiu = Query.getIdGrup(connection, numeGrup);
        String query = "INSERT INTO MembruGrupStudiu(username, idGrupStudiu) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idGrupStudiu);
        preparedStatement.executeUpdate();
    }

    public static void addNewMeeting(Connection connection, String numeGrup, LocalDate localDate, int numarMinParticipanti, int ora, int minute) throws Exception
    {
        int idGrupStudiu = Query.getIdGrup(connection, numeGrup);
        Date date = Date.valueOf(localDate);
        String query = "INSERT INTO IntalnireGrupStudiu(dataIntalnire, numarMinParticipanti, ora, minut, numarParticipanti, idGrupStudiu)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, date);
        preparedStatement.setInt(2, numarMinParticipanti);
        preparedStatement.setInt(3, ora);
        preparedStatement.setInt(4, minute);
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, idGrupStudiu);
        preparedStatement.executeUpdate();
    }

    public static void addNewMemberInMeeting(Connection connection, String numeGrup, String username) throws Exception
    {
        int idGrupStudiu = Query.getIdGrup(connection, numeGrup);
        int idIntalnire = Query.getIdIntalnire(connection, numeGrup);
        String insert = "INSERT INTO MembruIntalnireGrupStudiu(username, idIntalnireGrupStudiu, idGrupStudiu)" +
                "VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, idIntalnire);
        preparedStatement.setInt(3, idGrupStudiu);
        preparedStatement.executeUpdate();
    }

    public static void addMessage(Connection connection, String numeGrup, String username, String message) throws Exception
    {
        int idGrup = Query.getIdGrup(connection, numeGrup);
        String insert = "INSERT INTO MesajGrupStudiu(textMesaj, numeUtilizator, idGrupStudiu)" +
                "VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setString(1, message);
        preparedStatement.setString(2, username);
        preparedStatement.setInt(3, idGrup);
        preparedStatement.executeUpdate();
    }
}
