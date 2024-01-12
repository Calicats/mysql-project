package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivitateProfesor {

    private int idActivitateProfesor;
    private int idActivitate;
    private int idProfesor;

    public ActivitateProfesor(int idActivitateProfesor, int idActivitate, int idProfesor) {
        this.idActivitateProfesor = idActivitateProfesor;
        this.idActivitate = idActivitate;
        this.idProfesor = idProfesor;
    }

    public ActivitateProfesor(int idActivitateProfesor) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateProfesor WHERE idActivitateProfesor = ?");
            stmt.setInt(1, idActivitateProfesor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.idActivitateProfesor = rs.getInt("idActivitateProfesor");
                this.idActivitate = rs.getInt("idActivitate");
                this.idProfesor = rs.getInt("idProfesor");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }


    public static List<ActivitateProfesor> getTable() {
        List<ActivitateProfesor> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateProfesor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idActivitateProfesor = rs.getInt("idActivitateProfesor");
                list.add(new ActivitateProfesor(idActivitateProfesor));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getIdActivitateProfesor() {
        return idActivitateProfesor;
    }

    public void setIdActivitateProfesor(int idActivitateProfesor) {
        this.idActivitateProfesor = idActivitateProfesor;
    }

    public int getIdActivitate() {
        return idActivitate;
    }

    public void setIdActivitate(int idActivitate) {
        this.idActivitate = idActivitate;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    @Override
    public String toString() {
        return "ActivitateProfesorEntry{" +
                "idActivitateProfesor=" + idActivitateProfesor +
                ", idActivitate=" + idActivitate +
                ", idProfesor=" + idProfesor +
                '}';
    }
}


