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
            PreparedStatement stmt = connection.prepareStatement("SELECT idMesaj FROM MesajGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idMesaj = rs.getInt("idMesaj");
                list.add(new MesajGrupStudiu(idMesaj));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idMesaj, MesajGrupStudiu other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE MesajGrupStudiu SET textMesaj = ?, numeUtilizator = ?, idGrupStudiu = ? WHERE idMesaj = ?");
            stmt.setString(1, other.textMesaj);
            stmt.setString(2, other.numeUtilizator);
            stmt.setInt(3, other.idGrupStudiu);
            stmt.setInt(4, idMesaj);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idMesaj, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE MesajGrupStudiu SET ? = ? WHERE idMesaj = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idMesaj);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idMesaj, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE MesajGrupStudiu SET ? = ? WHERE idMesaj = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idMesaj);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idMesajGrupStudiu) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM MesajGrupStudiu WHERE idMesaj = ?");
            stmt.setInt(1, idMesajGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(MesajGrupStudiu newMesajGrupStudiu) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO MesajGrupStudiu(textMesaj, numeUtilizator, idGrupStudiu) VALUES (?, ?, ?)");
            stmt.setString(1, newMesajGrupStudiu.textMesaj);
            stmt.setString(2, newMesajGrupStudiu.numeUtilizator);
            stmt.setInt(3, newMesajGrupStudiu.idGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
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
