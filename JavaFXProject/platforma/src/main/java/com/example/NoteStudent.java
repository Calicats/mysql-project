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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent WHERE idNoteStudent = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idNota");
            nota = rs.getInt("nota");
            idStudent = rs.getInt("id_student");
            idActivitate = rs.getInt("idActivitate");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public NoteStudent(int id, int nota, int idStudent, int idActivitate) {
        this.id = id;
        this.nota = nota;
        this.idStudent = idStudent;
        this.idActivitate = idActivitate;
    }

    public static List<NoteStudent> getTable() {
        List<NoteStudent> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idNoteStudent");
                int nota = rs.getInt("nota");
                int idStudent = rs.getInt("idStudent");
                int idActivitate = rs.getInt("idActivitate");
                list.add(new NoteStudent(id, nota, idStudent, idActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static List<NoteStudent> getTable(int id_student) {
        List<NoteStudent> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent WHERE idStudent = ?");
            stmt.setInt(1, id_student);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idNoteStudent");
                int nota = rs.getInt("nota");
                int idActivitate = rs.getInt("idActivitate");
                list.add(new NoteStudent(id, nota, id_student, idActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int getNextId() {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(idNoteStudent) FROM NoteStudent");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) + 1;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdActivitate() {
        return idActivitate;
    }

    @Override
    public String toString() {
        return "NoteStudent{" +
                "id=" + id +
                ", nota=" + nota +
                ", idStudent=" + idStudent +
                ", idActivitate=" + idActivitate +
                '}';
    }

    private int id;
    private int nota;
    private int idStudent;
    private int idActivitate;
}
