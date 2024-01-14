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

            if (rs.next()) {
                this.id = rs.getInt("idGrupStudiu");
                numeGrup = rs.getString("numeGrup");
                descriere = rs.getString("descriere");
                idParticipantActivitate = rs.getInt("idParticipantActivitate");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private GrupStudiu(int id, String numeGrup, String descriere, int idParticipantActivitate) {
        this.id = id;
        this.numeGrup = numeGrup;
        this.descriere = descriere;
        this.idParticipantActivitate = idParticipantActivitate;
    }

    public static List<GrupStudiu> getTable() {
        List<GrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM GrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idGrupStudiu = rs.getInt("idGrupStudiu");
                list.add(new GrupStudiu(idGrupStudiu));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getDescriere() {
        return descriere;
    }

    public int getIdParticipantActivitate() {
        return idParticipantActivitate;
    }

    @Override
    public String toString() {
        return "GrupStudiu{" +
                "id=" + id +
                "numeGrup=" + numeGrup +
                ", descriere='" + descriere + '\'' +
                ", idParticipantActivitate=" + idParticipantActivitate +
                '}';
    }

    private int id;
    private String descriere;
    private String numeGrup;
    private int idParticipantActivitate;
}
