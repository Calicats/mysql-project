package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembruIntalnireGrupStudiu {
    public MembruIntalnireGrupStudiu(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MembruIntalnireGrupStudiu WHERE idMembruIntalnireGrupStudiu = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idMembruIntalnireGrupStudiu");
                this.username = rs.getString("username");
                this.idIntalnireGrupStudiu = rs.getInt("idIntalnireGrupStudiu");
                this.idGrupStudiu = rs.getInt("idGrupStudiu");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public MembruIntalnireGrupStudiu(int id, String username, int idIntalnireGrupStudiu, int idGrupStudiu) {
        this.id = id;
        this.username = username;
        this.idIntalnireGrupStudiu = idIntalnireGrupStudiu;
        this.idGrupStudiu = idGrupStudiu;
    }

    public static List<MembruIntalnireGrupStudiu> getTable() {
        List<MembruIntalnireGrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MembruIntalnireGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idMembruIntalnireGrupStudiu");
                String username = rs.getString("username");
                int idIntalnireGrupStudiu = rs.getInt("idIntalnireGrupStudiu");
                int idGrupStudiu = rs.getInt("idGrupStudiu");
                list.add(new MembruIntalnireGrupStudiu(id, username, idIntalnireGrupStudiu, idGrupStudiu));
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

    public int getIdIntalnireGrupStudiu() {
        return idIntalnireGrupStudiu;
    }

    public int getIdGrupStudiu() {
        return idGrupStudiu;
    }

    @Override
    public String toString() {
        return "MembruIntalnireGrupStudiu{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", idIntalnireGrupStudiu=" + idIntalnireGrupStudiu +
                ", idGrupStudiu=" + idGrupStudiu +
                '}';
    }

    private int id;
    private String username;
    private int idIntalnireGrupStudiu;
    private int idGrupStudiu;
}

