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
            PreparedStatement stmt = connection.prepareStatement("SELECT idGrupStudiu FROM GrupStudiu");
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

    public static int updateEntry(int idGrupStudiu, GrupStudiu other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE GrupStudiu SET numeGrup = ?, descriere = ?, idParticipantActivitate = ? WHERE idGrupStudiu = ?");
            stmt.setString(1, other.numeGrup);
            stmt.setString(2, other.descriere);
            stmt.setInt(3, other.idParticipantActivitate);
            stmt.setInt(4, idGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idGrupStudiu, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE GrupStudiu SET ? = ? WHERE idGrupStudiu = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idGrupStudiu, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE GrupStudiu SET ? = ? WHERE idGrupStudiu = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idGrupStudiu) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM GrupStudiu WHERE idGrupStudiu = ?");
            stmt.setInt(1, idGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(GrupStudiu newGrupStudiu) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO GrupStudiu(numeGrup, descriere, idParticipantActivitate) VALUES (?, ?, ?)");
            stmt.setString(1, newGrupStudiu.numeGrup);
            stmt.setString(2, newGrupStudiu.descriere);
            stmt.setInt(3, newGrupStudiu.idParticipantActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
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
