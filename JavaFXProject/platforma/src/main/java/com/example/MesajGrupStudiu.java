package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesajGrupStudiu {
    public MesajGrupStudiu(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MesajGrupStudiu WHERE idMesaj = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idMesaj");
                textMesaj = rs.getString("textMesaj");
                numeUtilizator = rs.getString("numeUtilizator");
                idGrupStudiu = rs.getInt("idGrupStudiu");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private MesajGrupStudiu(int id, String textMesaj, String numeUtilizator, int idGrupStudiu) {
        this.id = id;
        this.textMesaj = textMesaj;
        this.numeUtilizator = numeUtilizator;
        this.idGrupStudiu = idGrupStudiu;
    }

    public MesajGrupStudiu(String numeUtilizator, String textMesaj)
    {
        this.numeUtilizator = numeUtilizator;
        this.textMesaj = textMesaj;
    }

    public static List<MesajGrupStudiu> getTable() {
        List<MesajGrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MesajGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idMesaj");
                String textMesaj = rs.getString("textMesaj");
                String numeUtilizator = rs.getString("numeUtilizator");
                int idGrupStudiu = rs.getInt("idGrupStudiu");
                list.add(new MesajGrupStudiu(id, textMesaj, numeUtilizator, idGrupStudiu));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getTextMesaj() {
        return textMesaj;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public int getIdGrupStudiu() {
        return idGrupStudiu;
    }

    @Override
    public String toString() {
        return "MesajGrupStudiu{" +
                "id=" + id +
                ", textMesaj='" + textMesaj + '\'' +
                ", numeUtilizator='" + numeUtilizator + '\'' +
                ", idGrupStudiu=" + idGrupStudiu +
                '}';
    }

    private int id;
    private String textMesaj;
    private String numeUtilizator;
    private int idGrupStudiu;
}
