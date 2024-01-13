package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Similarly, you can create classes for ActivitateProfesorEntry, ActivitateStudentEntry, and NoteStudentEntry


public class Activitate {

    private int idActivitate;
    private String tip;
    private String nume;
    private String descriere;
    private int nrMaximStudenti;
    private int idCurs;
    private int idProfesor;

    // these fiels are used in showing the activity table. DO NOT MODIFY THEM!!!!!!!!!!!!!!!
    private String numeCurs;
    private int procentNota;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumeCurs() {
        return numeCurs;
    }

    public void setNumeCurs(String numeCurs) {
        this.numeCurs = numeCurs;
    }

    public int getProcentNota() {
        return procentNota;
    }

    public void setProcentNota(int procentNota) {
        this.procentNota = procentNota;
    }

    public Activitate(int idActivitate, String tip, int procentNota, int idCurs, int idProfesor) {
        this.idActivitate = idActivitate;
        this.tip = tip;
        this.procentNota = procentNota;
        this.idCurs = idCurs;
        this.idProfesor = idProfesor;
    }

    // this is for a JOIN in Query class, DO NOT MODIFY!!!!!!!
    public Activitate(int idActivitate, String username, String numeCurs, String tip, String descriere, int nrMaximStudenti, int procentNota)
    {
        this.idActivitate = idActivitate;
        this.username = username;
        this.numeCurs = numeCurs;
        this.tip = tip;
        this.descriere = descriere;
        this.nrMaximStudenti = nrMaximStudenti;
        this.procentNota = procentNota;
    }

    public Activitate(int idActivitate) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Activitate WHERE idActivitate = ?");
            stmt.setInt(1, idActivitate);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.idActivitate = rs.getInt("idActivitate");
                this.tip = rs.getString("tip");
                this.procentNota = rs.getInt("procentNota");
                this.idCurs = rs.getInt("idCurs");
                this.idProfesor = rs.getInt("idProfesor");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    // Getter methods for ActivitateEntry fields
    // ...

    public static List<Activitate> getTable() {
        List<Activitate> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Activitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idActivitate = rs.getInt("idActivitate");
                list.add(new Activitate(idActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getIdActivitate() {
        return idActivitate;
    }

    public void setIdActivitate(int idActivitate) {
        this.idActivitate = idActivitate;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNrMaximStudenti() {
        return nrMaximStudenti;
    }

    public void setNrMaximStudenti(int nrMaximStudenti) {
        this.nrMaximStudenti = nrMaximStudenti;
    }

    @Override
    public String toString() {
        return "Activitate{" +
                "idActivitate=" + idActivitate +
                ", tip='" + tip + '\'' +
                ", procentNota='" + procentNota + '\'' +
                ", idCurs='" + idCurs + '\'' +
                ", idProfesor=" + idProfesor +
                '}';
    }
}

