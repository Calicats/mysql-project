package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Deprecated

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

    public ParticipantActivitate(String usernameProfesor, String tipActivitate, String descriere, int nrStudenti)
    {
        this.usernameProfesor = usernameProfesor;
        this.tipActivitate = tipActivitate;
        this.descriere = descriere;
        this.nrStudenti = nrStudenti;
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

    public String getTipActivitate() {
        return tipActivitate;
    }

    public void setTipActivitate(String tipActivitate) {
        this.tipActivitate = tipActivitate;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNrStudenti() {
        return nrStudenti;
    }

    public void setNrStudenti(int nrStudenti) {
        this.nrStudenti = nrStudenti;
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
    private String tipActivitate;
    private String descriere;
    private int nrStudenti;

}

