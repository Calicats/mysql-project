package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Curs {
    public Curs(int idCurs, String numeCurs, String descriere, int nrMaximStudenti) {
        this.idCurs = idCurs;
        this.numeCurs = numeCurs;
        this.descriere = descriere;
        this.nrMaximStudenti = nrMaximStudenti;
    }

    public Curs(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Curs WHERE idCurs = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCurs = rs.getInt("idCurs");
                numeCurs = rs.getString("numeCurs");
                descriere = rs.getString("descriere");
                nrMaximStudenti = rs.getInt("nrMaximStudenti");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<Curs> getTable() {
        List<Curs> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idCurs FROM Curs");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCurs = rs.getInt("idCurs");
                list.add(new Curs(idCurs));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idCurs, Curs other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Curs SET numeCurs = ?, descriere = ?, nrMaximStudenti = ? WHERE idCurs = ?");
            stmt.setString(1, other.numeCurs);
            stmt.setString(2, other.descriere);
            stmt.setInt(3, other.nrMaximStudenti);
            stmt.setInt(4, idCurs);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idCurs, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Curs SET ? = ? WHERE idCurs = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idCurs);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idCurs, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Curs SET ? = ? WHERE idCurs = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idCurs);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idCurs) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Curs WHERE idCurs = ?");
            stmt.setInt(1, idCurs);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public String getNumeCurs() {
        return numeCurs;
    }

    public void setNumeCurs(String numeCurs) {
        this.numeCurs = numeCurs;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNrMaximStudenti() {
        return nrMaximStudenti;
    }

    public void setNrMaximStudenti(int nrMaximStudenti) {
        this.nrMaximStudenti = nrMaximStudenti;
    }

    private int idCurs;
    private String numeCurs;
    private String descriere;
    private int nrMaximStudenti;
}
