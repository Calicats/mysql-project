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

            if (rs.next()) {
                this.id = rs.getInt("idNoteStudent");
                nota = rs.getInt("nota");
                idStudent = rs.getInt("idStudent");
                idActivitate = rs.getInt("idActivitate");
            }
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
            PreparedStatement stmt = connection.prepareStatement("SELECT idNoteStudent FROM NoteStudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idNoteStudent = rs.getInt("idNoteStudent");
                list.add(new NoteStudent(idNoteStudent));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static List<NoteStudent> getTable(int idStudent) {
        List<NoteStudent> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent WHERE idStudent = ?");
            stmt.setInt(1, idStudent);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idNoteStudent");
                int nota = rs.getInt("nota");
                int idActivitate = rs.getInt("idActivitate");
                list.add(new NoteStudent(id, nota, idStudent, idActivitate));
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

    public static int updateEntry(int idNoteStudent, NoteStudent other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE NoteStudent SET nota = ?, idStudent = ?, idActivitate = ? WHERE idNoteStudent = ?");
            stmt.setInt(1, other.nota);
            stmt.setInt(2, other.idStudent);
            stmt.setInt(3, other.idActivitate);
            stmt.setInt(4, idNoteStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idNoteStudent, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE NoteStudent SET ? = ? WHERE idNoteStudent = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idNoteStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idNoteStudent) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM NoteStudent WHERE idNoteStudent = ?");
            stmt.setInt(1, idNoteStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(NoteStudent newNoteStudent) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO NoteStudent(nota, idStudent, idActivitate) VALUES (?, ?, ?)");
            stmt.setInt(1, newNoteStudent.nota);
            stmt.setInt(2, newNoteStudent.idStudent);
            stmt.setInt(3, newNoteStudent.idActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateOrInsert(NoteStudent newNoteStudent) {
        Connection connection = Connect.getConnection();
        try {
            if (NoteStudent.getTable().stream().anyMatch(noteStudent -> noteStudent.idStudent == newNoteStudent.idStudent)) {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE NoteStudent SET nota = ? WHERE idStudent = ? AND idActivitate = ?");
                stmt.setInt(1, newNoteStudent.nota);
                stmt.setInt(2, newNoteStudent.idStudent);
                stmt.setInt(3, newNoteStudent.idActivitate);
                return stmt.executeUpdate();
            }
            return NoteStudent.insert(newNoteStudent);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
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
