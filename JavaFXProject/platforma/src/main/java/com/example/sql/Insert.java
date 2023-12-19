package com.example.sql;

import java.sql.*;

public class Insert {
    public static void addSuperAdmin(Connection connection, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola) throws Exception{
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

    public static void addAdmin(Connection connection, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola) throws Exception{
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

    public static void addProfessor(Connection connection, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre, String adresa, String nrTelefon, String email, String username, String parola) throws Exception{
        String procedureCall = "{CALL AddNewProfessor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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

    public static void addStudent(Connection connection, String cnp, String nume, int anStudiu, int numarOre, String adresa, String nrTelefon, String email, String username, String parola) throws Exception{
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
}
