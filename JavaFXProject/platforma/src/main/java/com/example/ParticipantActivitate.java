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
            id_activitate_profesor = rs.getInt("id_activitate_profesor");
            id_student = rs.getInt("id_student");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public ParticipantActivitate(int id, int id_activitate_profesor, int id_student) {
        this.id = id;
        this.id_activitate_profesor = id_activitate_profesor;
        this.id_student = id_student;
    }

    public ParticipantActivitate(String usernameProfesor, String numeCurs, String tip, int nrMaximStudenti, int nrParticipanti)
    {
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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ParticipantActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idParticipantActivitate");
                int id_activitate_profesor = rs.getInt("id_activitate_profesor");
                int id_student = rs.getInt("id_student");
                list.add(new ParticipantActivitate(id, id_activitate_profesor, id_student));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public int getId_activitate_profesor() {
        return id_activitate_profesor;
    }

    public int getId_student() {
        return id_student;
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

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    @Override
    public String toString() {
        return "ParticipantActivitate{" +
                "id=" + id +
                ", id_activitate_profesor=" + id_activitate_profesor +
                ", id_student=" + id_student +
                '}';
    }

    private int id;
    private int id_activitate_profesor;
    private int id_student;

    private String usernameProfesor;
    private String numeCurs;
    private String tip;
    private String descriere;
    private int nrMaximStudenti;
    private int nrParticipanti;

}

