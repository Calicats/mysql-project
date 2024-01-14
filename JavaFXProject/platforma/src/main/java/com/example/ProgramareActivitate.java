package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramareActivitate {
    public ProgramareActivitate(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ProgramareActivitate WHERE idProgramareActivitate = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idProgramareActivitate");
                dataIncepere = rs.getDate("dataIncepere");
                dataFinalizare = rs.getDate("dataFinalizare");
                frecventa = rs.getString("frecventa");
                zi = rs.getString("zi");
                ora = rs.getInt("ora");
                minut = rs.getInt("minut");
                durata = rs.getInt("durata");
                idParticipantActivitate = rs.getInt("idParticipantActivitate");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    private ProgramareActivitate(int id, java.sql.Date dataIncepere, java.sql.Date dataFinalizare, String frecventa,
                                 String zi, int ora, int minut, int durata, int idParticipantActivitate) {
        this.id = id;
        this.dataIncepere = dataIncepere;
        this.dataFinalizare = dataFinalizare;
        this.frecventa = frecventa;
        this.zi = zi;
        this.ora = ora;
        this.minut = minut;
        this.durata = durata;
        this.idParticipantActivitate = idParticipantActivitate;
    }

    public static List<ProgramareActivitate> getTable() {
        List<ProgramareActivitate> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idProgramareActivitate FROM ProgramareActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProgramareActivitate = rs.getInt("idProgramareActivitate");
                list.add(new ProgramareActivitate(idProgramareActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idProgramareActivitate, ProgramareActivitate other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE ProgramareActivitate SET dataIncepere = ?, dataFinalizare = ?, frecventa = ?, zi = ?, ora = ?, minut = ?, durata = ?, idParticipantActivitate = ? WHERE idProgramareActivitate = ?");
            stmt.setDate(1, other.dataIncepere);
            stmt.setDate(2, other.dataFinalizare);
            stmt.setString(3, other.frecventa);
            stmt.setString(4, other.zi);
            stmt.setInt(5, other.ora);
            stmt.setInt(6, other.minut);
            stmt.setInt(7, other.durata);
            stmt.setInt(8, other.idParticipantActivitate);
            stmt.setInt(9, idProgramareActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idProgramareActivitate, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE ProgramareActivitate SET ? = ? WHERE idProgramareActivitate = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idProgramareActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idProgramareActivitate, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE ProgramareActivitate SET ? = ? WHERE idProgramareActivitate = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idProgramareActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idProgramareActivitate, String column, java.sql.Date value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE ProgramareActivitate SET ? = ? WHERE idProgramareActivitate = ?");
            stmt.setString(1, column);
            stmt.setDate(2, value);
            stmt.setInt(3, idProgramareActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idProgramareActivitate) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM ProgramareActivitate WHERE idProgramareActivitate = ?");
            stmt.setInt(1, idProgramareActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public java.sql.Date getDataIncepere() {
        return dataIncepere;
    }

    public java.sql.Date getDataFinalizare() {
        return dataFinalizare;
    }

    public String getFrecventa() {
        return frecventa;
    }

    public String getZi() {
        return zi;
    }

    public int getOra() {
        return ora;
    }

    public int getMinut() {
        return minut;
    }

    public int getDurata() {
        return durata;
    }

    public int getIdParticipantActivitate() {
        return idParticipantActivitate;
    }

    @Override
    public String toString() {
        return "ProgramareActivitate{" +
                "id=" + id +
                ", dataIncepere=" + dataIncepere +
                ", dataFinalizare=" + dataFinalizare +
                ", frecventa='" + frecventa + '\'' +
                ", zi='" + zi + '\'' +
                ", ora=" + ora +
                ", minut=" + minut +
                ", durata=" + durata +
                ", idParticipantActivitate=" + idParticipantActivitate +
                '}';
    }

    private int id;
    private java.sql.Date dataIncepere;
    private java.sql.Date dataFinalizare;
    private String frecventa;
    private String zi;
    private int ora;
    private int minut;
    private int durata;
    private int idParticipantActivitate;
}
