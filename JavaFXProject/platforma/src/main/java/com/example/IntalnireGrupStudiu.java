package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntalnireGrupStudiu {
    public IntalnireGrupStudiu(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM IntalnireGrupStudiu WHERE idIntalnireGrupStudiu = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idIntalnireGrupStudiu");
                dataIntalnire = rs.getDate("dataIntalnire");
                numarMinParticipanti = rs.getInt("numarMinParticipanti");
                ora = rs.getInt("ora");
                minut = rs.getInt("minut");
                idGrupStudiu = rs.getInt("idGrupStudiu");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private IntalnireGrupStudiu(int id, java.sql.Date dataIntalnire, int numarMinParticipanti, int ora, int minut, int idGrupStudiu) {
        this.id = id;
        this.dataIntalnire = dataIntalnire;
        this.numarMinParticipanti = numarMinParticipanti;
        this.ora = ora;
        this.minut = minut;
        this.idGrupStudiu = idGrupStudiu;
    }

    public static List<IntalnireGrupStudiu> getTable() {
        List<IntalnireGrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idIntalnireGrupStudiu FROM IntalnireGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idIntalnireGrupStudiu = rs.getInt("idIntalnireGrupStudiu");
                list.add(new IntalnireGrupStudiu(idIntalnireGrupStudiu));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idIntalnireGrupStudiu, IntalnireGrupStudiu other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE IntalnireGrupStudiu SET dataIntalnire = ?, numarMinParticipanti = ?, ora = ?, minut = ?, idGrupStudiu = ? WHERE idIntalnireGrupStudiu = ?");
            stmt.setDate(1, other.dataIntalnire);
            stmt.setInt(2, other.numarMinParticipanti);
            stmt.setInt(3, other.ora);
            stmt.setInt(4, other.minut);
            stmt.setInt(5, other.idGrupStudiu);
            stmt.setInt(6, idIntalnireGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idIntalnireGrupStudiu, String column, java.sql.Date value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE IntalnireGrupStudiu SET ? = ? WHERE idIntalnireGrupStudiu = ?");
            stmt.setString(1, column);
            stmt.setDate(2, value);
            stmt.setInt(3, idIntalnireGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idIntalnireGrupStudiu, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE IntalnireGrupStudiu SET ? = ? WHERE idIntalnireGrupStudiu = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idIntalnireGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idIntalnireGrupStudiu) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM IntalnireGrupStudiu WHERE idIntalnireGrupStudiu = ?");
            stmt.setInt(1, idIntalnireGrupStudiu);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public java.sql.Date getDataIntalnire() {
        return dataIntalnire;
    }

    public int getNumarMinParticipanti() {
        return numarMinParticipanti;
    }

    public int getOra() {
        return ora;
    }

    public int getMinut() {
        return minut;
    }

    public int getIdGrupStudiu() {
        return idGrupStudiu;
    }

    @Override
    public String toString() {
        return "IntalnireGrupStudiu{" +
                "id=" + id +
                ", dataIntalnire=" + dataIntalnire +
                ", numarMinParticipanti=" + numarMinParticipanti +
                ", ora=" + ora +
                ", minut=" + minut +
                ", idGrupStudiu=" + idGrupStudiu +
                '}';
    }

    private int id;
    private java.sql.Date dataIntalnire;
    private int numarMinParticipanti;
    private int ora;
    private int minut;
    private int idGrupStudiu;
}
