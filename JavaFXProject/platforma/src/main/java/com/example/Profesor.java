package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Profesor extends User {
    public Profesor(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profesor WHERE idProfesor = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idProfesor");
            cnp = rs.getString("cnp");
            nume = rs.getString("nume");
            departament = rs.getString("departament");
            nrMinOre = rs.getInt("nrMinOre");
            nrMaxOre = rs.getInt("nrMaxOre");
            adresa = rs.getString("adresa");
            nrTelefon = rs.getString("nrTelefon");
            email = rs.getString("email");
            username = rs.getString("username");
            parola = rs.getString("parola");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public Profesor(int id, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
        this.departament = departament;
        this.nrMinOre = nrMinOre;
        this.nrMaxOre = nrMaxOre;
    }

    public static List<Profesor> getTable() {
        List<Profesor> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profesor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProfesor");
                String cnp = rs.getString("cnp");
                String nume = rs.getString("nume");
                String departament = rs.getString("departament");
                int nrMinOre = rs.getInt("nrMinOre");
                int nrMaxOre = rs.getInt("nrMaxOre");
                String adresa = rs.getString("adresa");
                String nrTelefon = rs.getString("nrTelefon");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String parola = rs.getString("parola");
                int idRol = rs.getInt("idRol");
                list.add(new Profesor(id, cnp, nume, departament, nrMinOre, nrMaxOre, adresa, nrTelefon, email, username, parola, idRol));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public String getDepartament() {
        return departament;
    }

    public int getNrMinOre() {
        return nrMinOre;
    }

    public int getNrMaxOre() {
        return nrMaxOre;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "departament='" + departament + '\'' +
                ", nrMinOre=" + nrMinOre +
                ", nrMaxOre=" + nrMaxOre +
                ", id=" + id +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", idRol=" + idRol +
                '}';
    }

    private String departament;
    private int nrMinOre;
    private int nrMaxOre;
}
