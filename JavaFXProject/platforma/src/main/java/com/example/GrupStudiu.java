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
            numeGrup = rs.getString("numeGrup");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public GrupStudiu(int id, String numeGrup) {
        this.id = id;
        this.numeGrup = numeGrup;
    }

    public GrupStudiu(int id, String numeGrup, int idIntalnire, java.sql.Date dataIntalnire)
    {
        this.id = id;
        this.numeGrup = numeGrup;
        this.idIntalnire = idIntalnire;
        this.dataIntalnire = dataIntalnire;
    }

    public static List<GrupStudiu> getTable() {
        List<GrupStudiu> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM GrupStudiu");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idGrupStudiu");
                String numeGrup = rs.getString("numeGrup");
                list.add(new GrupStudiu(id, numeGrup));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public int getIdIntalnire()
    {
        return idIntalnire;
    }

    public String getNumeGrup()
    {
        return numeGrup;
    }

    public java.sql.Date getDataIntalnire()
    {
        return dataIntalnire;
    }

    @Override
    public String toString() {
        return "GrupStudiu{" +
                "id=" + id +
                "numeGrup=" + numeGrup + '\'' +
                '}';
    }

    private int id;
    private String numeGrup;

    //following fields are used for JOIN DO NOT MODIFY !!!
    private int idIntalnire;
    private java.sql.Date dataIntalnire;
}
