package com.example;

import com.example.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramareActivitate {


    public ProgramareActivitate() {
        // default data
        this.id = -1;
        this.dataIncepere = new Date(0);
        this.dataFinalizare = new Date(0);
        this.frecventa = "";
        this.zi = "";
        this.ora = 0;
        this.minut = 0;
        this.durata = 0;
        this.idActivitate = -1;
    }

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
                idActivitate = rs.getInt("idActivitate");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }



    private ProgramareActivitate(int id, java.sql.Date dataIncepere, java.sql.Date dataFinalizare, String frecventa,
                                 String zi, int ora, int minut, int durata, int idActivitate) {
        this.id = id;
        this.dataIncepere = dataIncepere;
        this.dataFinalizare = dataFinalizare;
        this.frecventa = frecventa;
        this.zi = zi;
        this.ora = ora;
        this.minut = minut;
        this.durata = durata;
        this.idActivitate = idActivitate;
    }

    public ProgramareActivitate(int id, Date date, Date date1, String string, String string1, int i, int i1, int i2, int i3, int i4) {
        this.id = id;
        this.dataIncepere = date;
        this.dataFinalizare = date1;
        this.frecventa = string;
        this.zi = string1;
        this.ora = i;
        this.minut = i1;
        this.durata = i2;
        this.idActivitate = i3;
    }

    public static List<ProgramareActivitate> getTable() {
        List<ProgramareActivitate> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idProgramareActivitate FROM ProgramareActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProgramareActivitate = rs.getInt("idProgramareActivitate");
                ProgramareActivitate programareActivitate = new ProgramareActivitate(idProgramareActivitate);
                programareActivitate.setNumeActivitate();
                list.add(programareActivitate);
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
            stmt.setInt(8, other.idActivitate);
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

    public static int insert(ProgramareActivitate newProgramareActivitate) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO ProgramareActivitate(dataIncepere, dataFinalizare, frecventa, zi, ora, minut, durata, idActivitate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setDate(1, newProgramareActivitate.dataIncepere);
            stmt.setDate(2, newProgramareActivitate.dataFinalizare);
            stmt.setString(3, newProgramareActivitate.frecventa);
            stmt.setString(4, newProgramareActivitate.zi);
            stmt.setInt(5, newProgramareActivitate.ora);
            stmt.setInt(6, newProgramareActivitate.minut);
            stmt.setInt(7, newProgramareActivitate.durata);
            stmt.setInt(8, newProgramareActivitate.idActivitate);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public ProgramareActivitate setId(int id) {
        this.id = id;
        return this;
    }

    public ProgramareActivitate setDataIncepere(Date dataIncepere) {
        this.dataIncepere = dataIncepere;
        return this;
    }

    public ProgramareActivitate setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
        return this;
    }

    public ProgramareActivitate setFrecventa(String frecventa) {
        this.frecventa = frecventa;
        return this;
    }

    public ProgramareActivitate setZi(String zi) {
        this.zi = zi;
        return this;
    }

    public ProgramareActivitate setOra(int ora) {
        this.ora = ora;
        return this;
    }

    public ProgramareActivitate setMinut(int minut) {
        this.minut = minut;
        return this;
    }

    public ProgramareActivitate setDurata(int durata) {
        this.durata = durata;
        return this;
    }



    public ProgramareActivitate setNumeActivitate() {
        int idCurs = new Activitate(idActivitate).getIdCurs();
        this.numeActivitate = new Curs(idCurs).getNumeCurs();
        return this;
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

    public int getIdActivitate() {
        return idActivitate;
    }

    public ProgramareActivitate setIdActivitate(int idActivitate) {
        this.idActivitate = idActivitate;
        return this;
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
                ", idActivitate=" + idActivitate +
                ", numeActivitate='" + numeActivitate + '\'' +
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
    private int idActivitate;

    // field for query DO NOT TOUCH OR CHANGE
    private String numeActivitate="error";
}
