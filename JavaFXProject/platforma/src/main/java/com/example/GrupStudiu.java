package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrupStudiu {
    public GrupStudiu(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM GrupStudiu WHERE idGrupStudiu = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idGrupStudiu");
            descriereGrupStudiu = rs.getString("descriereGrupStudiu");
            id_participant_activitate = rs.getInt("id_participant_activitate");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private GrupStudiu(int id, String descriereGrupStudiu, int id_participant_activitate) {
        this.id = id;
        this.descriereGrupStudiu = descriereGrupStudiu;
        this.id_participant_activitate = id_participant_activitate;
    }

    public static List<GrupStudiu> getTable() {
        List<GrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM NoteStudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idGrupStudiu");
                String descriereGrupStudiu = rs.getString("descriereGrupStudiu");
                int id_participant_activitate = rs.getInt("id_participant_activitate");
                list.add(new GrupStudiu(id, descriereGrupStudiu, id_participant_activitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getDescriereGrupStudiu() {
        return descriereGrupStudiu;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    @Override
    public String toString() {
        return "GrupStudiu{" +
                "id=" + id +
                ", descriereGrupStudiu='" + descriereGrupStudiu + '\'' +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

    private int id;
    private String descriereGrupStudiu;
    private int id_participant_activitate;
}
