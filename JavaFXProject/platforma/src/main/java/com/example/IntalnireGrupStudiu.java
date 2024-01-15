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

    public IntalnireGrupStudiu(int id, java.sql.Date dataIntalnire, int numarMinParticipanti, int ora, int minut, int numarParticipanti, int idGrupStudiu) {
        this.id = id;
        this.dataIntalnire = dataIntalnire;
        this.numarMinParticipanti = numarMinParticipanti;
        this.ora = ora;
        this.minut = minut;
        this.numarParticipanti = numarParticipanti;
        this.idGrupStudiu = idGrupStudiu;
    }

    public IntalnireGrupStudiu(String numeGrup, java.sql.Date dataIntalnire, int numarParticipanti)
    {
        this.numeGrup = numeGrup;
        this.dataIntalnire = dataIntalnire;
        this.numarParticipanti = numarParticipanti;
    }

    public static List<IntalnireGrupStudiu> getTable() {
        List<IntalnireGrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM IntalnireGrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idIntalnireGrupStudiu");
                java.sql.Date dataIntalnire = rs.getDate("dataIntalnire");
                int numarMinParticipanti = rs.getInt("numarMinParticipanti");
                int ora = rs.getInt("ora");
                int minut = rs.getInt("minut");
                int numarParticipanti = rs.getInt("numarParticipanti");
                int idGrupStudiu = rs.getInt("idGrupStudiu");
                list.add(new IntalnireGrupStudiu(id, dataIntalnire, numarMinParticipanti, ora, minut, numarParticipanti, idGrupStudiu));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
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

    public int getNumarParticipanti()
    {
        return numarParticipanti;
    }

    public int getIdGrupStudiu() {
        return idGrupStudiu;
    }

    public String getNumeGrup()
    {
        return numeGrup;
    }

    @Override
    public String toString() {
        return "IntalnireGrupStudiu{" +
                "id=" + id +
                ", dataIntalnire=" + dataIntalnire +
                ", numarMinParticipanti=" + numarMinParticipanti +
                ", ora=" + ora +
                ", minut=" + minut +
                ", numarParticipanti=" + numarParticipanti +
                ", idGrupStudiu=" + idGrupStudiu +
                '}';
    }

    private int id;
    private java.sql.Date dataIntalnire;
    private int numarMinParticipanti;
    private int ora;
    private int minut;
    private int numarParticipanti;
    private int idGrupStudiu;

    // folowing fields are used in a JOIN DO NOT MODIFY !!!
    private String numeGrup;
}
