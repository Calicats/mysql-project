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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ProgramareActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProgramareActivitate");
                java.sql.Date dataIncepere = rs.getDate("dataIncepere");
                java.sql.Date dataFinalizare = rs.getDate("dataFinalizare");
                String frecventa = rs.getString("frecventa");
                String zi = rs.getString("zi");
                int ora = rs.getInt("ora");
                int minut = rs.getInt("minut");
                int durata = rs.getInt("durata");
                int idParticipantActivitate = rs.getInt("idParticipantActivitate");
                list.add(new ProgramareActivitate(id, dataIncepere, dataFinalizare, frecventa, zi, ora, minut, durata, idParticipantActivitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
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
