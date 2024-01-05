package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteStudent {
    public NoteStudent(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent WHERE idNota = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idNota");
            nota = rs.getInt("nota");
            id_student = rs.getInt("id_student");
            id_participant_activitate = rs.getInt("id_participant_activitate");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public NoteStudent(int id, int nota, int id_student, int id_participant_activitate) {
        this.id = id;
        this.nota = nota;
        this.id_student = id_student;
        this.id_participant_activitate = id_participant_activitate;
    }

    public static List<NoteStudent> getTable() {
        List<NoteStudent> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idNota");
                int nota = rs.getInt("nota");
                int id_student = rs.getInt("id_student");
                int id_participant_activitate = rs.getInt("id_participant_activitate");
                list.add(new NoteStudent(id, nota, id_student, id_participant_activitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public int getId_student() {
        return id_student;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    @Override
    public String toString() {
        return "NoteStudent{" +
                "id=" + id +
                ", nota=" + nota +
                ", id_student=" + id_student +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

    private int id;
    private int nota;
    private int id_student;
    private int id_participant_activitate;
}
