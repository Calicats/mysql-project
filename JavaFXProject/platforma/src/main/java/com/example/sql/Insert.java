package com.example.sql;

import java.sql.*;

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

    public static void addNewActivitateProfesor(Connection connection, String username, String tipActivitate, String descriere) throws Exception
    {
        String procedureCall = "{CALL AddNewActivitateProfesor(?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);
        statement.setString(1, username);
        statement.setString(2, tipActivitate);
        statement.setString(3, descriere);
        statement.execute();
    }
}
