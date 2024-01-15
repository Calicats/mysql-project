package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembruGrupStudiu {
    public MembruGrupStudiu(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MembruGrupStudiu WHERE idMembruGrupStudiu = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idMembruGrupStudiu");
            username = rs.getString("username");
            // Add other fields as needed
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public MembruGrupStudiu(int id, String username, int idGrupStudiu) {
        this.id = id;
        this.username = username;
        this.idGrupStudiu = idGrupStudiu;
        // Add other fields as needed
    }

    // following constructor is used in populating the table in CreazaModificaGrupDeStudii DO NOT MODIFY!!!!!
    public MembruGrupStudiu(String username)
    {
        this.username = username;
    }

    public static List<MembruGrupStudiu> getTable() {
        List<MembruGrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MembruGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idMembruGrupStudiu");
                String username = rs.getString("username");
                int idGrupStudiu = rs.getInt("idGrupStudiu");
                list.add(new MembruGrupStudiu(id, username, idGrupStudiu));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Add getters for other fields as needed

    @Override
    public String toString() {
        return "MembruGrupStudiu{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", idGrupStudiu=" + idGrupStudiu +
                // Add other fields to the toString representation
                '}';
    }

    private int id;
    private String username;
    private int idGrupStudiu;
    // Add other fields as needed
}
