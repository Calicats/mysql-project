package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Rol {
    public Rol(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Rol WHERE idRol = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idRol");
                numeRol = rs.getString("numeRol");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public Rol(int id, String numeRol) {
        this.id = id;
        this.numeRol = numeRol;
    }

    public static List<Rol> getTable() {
        List<Rol> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idRol FROM Rol");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRol = rs.getInt("idRol");
                list.add(new Rol(idRol));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    // TODO: Are update methods needed for this class?
    // Seems redundant since it's only used internally

    public int getId() {
        return id;
    }

    public String getNumeRol() {
        return numeRol;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", numeRol='" + numeRol + '\'' +
                '}';
    }

    int id;
    String numeRol;
}
