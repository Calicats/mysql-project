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
    private String ziua;
    private String frecventa;
    private int oraIncepere;
    private int minuteIncepere;
    private int durata;
    private int procentNotaFinala;

    public Activitate(int idActivitate, String tip, String nume, String descriere, int nrMaximStudenti) {
        this.idActivitate = idActivitate;
        this.tip = tip;
        this.nume = nume;
        this.descriere = descriere;
        this.nrMaximStudenti = nrMaximStudenti;
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
                this.nume = rs.getString("nume");
                this.descriere = rs.getString("descriere");
                this.nrMaximStudenti = rs.getInt("nrMaximStudenti");
                this.ziua = rs.getString("ziua");
                this.frecventa = rs.getString("frecventa");
                this.oraIncepere = rs.getInt("oraIncepere");
                this.minuteIncepere = rs.getInt("minuteIncepere");
                this.durata = rs.getInt("durata");
                this.procentNotaFinala = rs.getInt("procentNotaFinala");
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

    public String getZiua() {
        return ziua;
    }

    public void setZiua(String ziua) {
        this.ziua = ziua;
    }

    public String getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(String frecventa) {
        this.frecventa = frecventa;
    }

    public int getOraIncepere() {
        return oraIncepere;
    }

    public void setOraIncepere(int oraIncepere) {
        this.oraIncepere = oraIncepere;
    }

    public int getMinuteIncepere() {
        return minuteIncepere;
    }

    public void setMinuteIncepere(int minuteIncepere) {
        this.minuteIncepere = minuteIncepere;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public int getProcentNotaFinala() {
        return procentNotaFinala;
    }

    public void setProcentNotaFinala(int procentNotaFinala) {
        this.procentNotaFinala = procentNotaFinala;
    }

    @Override
    public String toString() {
        return "Activitate{" +
                "idActivitate=" + idActivitate +
                ", tip='" + tip + '\'' +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", nrMaximStudenti=" + nrMaximStudenti +
                ", ziua='" + ziua + '\'' +
                ", frecventa='" + frecventa + '\'' +
                ", oraIncepere=" + oraIncepere +
                ", minuteIncepere=" + minuteIncepere +
                ", durata=" + durata +
                ", procentNotaFinala=" + procentNotaFinala +
                '}';
    }
}


