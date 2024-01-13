package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class ActivitateStudent {

    private int idActivitateStudent;
    private int idActivitate;
    private int idStudent;

    public ActivitateStudent(int idActivitateStudent) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateStudent WHERE idActivitateStudent = ?");
            stmt.setInt(1, idActivitateStudent);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.idActivitateStudent = rs.getInt("idActivitateStudent");
                this.idActivitate = rs.getInt("idActivitate");
                this.idStudent = rs.getInt("idStudent");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public ActivitateStudent(int idActivitateStudent, int idActivitate, int idStudent) {
        this.idActivitateStudent = idActivitateStudent;
        this.idActivitate = idActivitate;
        this.idStudent = idStudent;
    }

    // Getter methods for ActivitateStudentEntry fields
    // ...

    public static List<ActivitateStudent> getTable() {
        List<ActivitateStudent> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateStudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idActivitateStudent = rs.getInt("idActivitateStudent");
                int idActivitate = rs.getInt("idActivitate");
                int idStudent = rs.getInt("idStudent");
                list.add(new ActivitateStudent(idActivitateStudent, idActivitate, idStudent));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getIdActivitateStudent() {
        return idActivitateStudent;
    }

    public void setIdActivitateStudent(int idActivitateStudent) {
        this.idActivitateStudent = idActivitateStudent;
    }

    public int getIdActivitate() {
        return idActivitate;
    }

    public void setIdActivitate(int idActivitate) {
        this.idActivitate = idActivitate;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public String toString() {
        return "ActivitateStudent{" +
                "idActivitateStudent=" + idActivitateStudent +
                ", idActivitate=" + idActivitate +
                ", idStudent=" + idStudent +
                '}';
    }
}
