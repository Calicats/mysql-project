package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ParticipantActivitate {
    public ParticipantActivitate(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ParticipantActivitate WHERE idParticipantActivitate = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idParticipantActivitate");
            numarParticipanti = rs.getInt("numarParticipanti");
            idActivitate = rs.getInt("idActivitate");
            idStudent = rs.getInt("idStudent");
            idProfesor = rs.getInt("idProfesor");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public ParticipantActivitate(int id, int numarParticipanti, int idActivitate, int idStudent, int idProfesor) {
        this.id = id;
        this.numarParticipanti = numarParticipanti;
        this.idActivitate = idActivitate;
        this.idStudent = idStudent;
        this.idProfesor = idProfesor;
    }

    public ParticipantActivitate(String usernameProfesor, String numeCurs, String tip, int nrMaximStudenti, int nrParticipanti) {
        this.usernameProfesor = usernameProfesor;
        this.numeCurs = numeCurs;
        this.tip = tip;
        this.nrMaximStudenti = nrMaximStudenti;
        this.nrParticipanti = nrParticipanti;
    }

    public static List<ParticipantActivitate> getTable() {
        List<ParticipantActivitate> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idParticipantActivitate FROM ParticipantActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idParticipantActivitate = rs.getInt("idParticipantActivitate");
                list.add(new ParticipantActivitate(idParticipantActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public int getIdActivitate() {
        return idActivitate;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public String getUsernameProfesor() {
        return usernameProfesor;
    }

    public void setUsernameProfesor(String usernameProfesor) {
        this.usernameProfesor = usernameProfesor;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

    public String getNumeCurs() {
        return numeCurs;
    }

    public void setNumeCurs(String numeCurs) {
        this.numeCurs = numeCurs;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public int getNumarParticipanti() {
        return numarParticipanti;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    @Override
    public String toString() {
        return "ParticipantActivitate{" +
                "id=" + id +
                ", id_activitate_profesor=" + idActivitate +
                ", id_student=" + idStudent +
                '}';
    }

    private int id;
    private int numarParticipanti;
    private int idActivitate;
    private int idStudent;
    private int idProfesor;

    private String usernameProfesor;
    private String numeCurs;
    private String tip;
    private String descriere;
    private int nrMaximStudenti;
    private int nrParticipanti;

}

