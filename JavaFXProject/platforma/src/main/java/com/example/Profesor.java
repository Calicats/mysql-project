package com.example;

import com.example.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Profesor extends User {
    public static final int ID_ROL = 3;

    public Profesor(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Profesor WHERE idProfesor = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idProfesor");
                cnp = rs.getString("cnp");
                nume = rs.getString("nume");
                departament = rs.getString("departament");
                nrMinOre = rs.getInt("nrMinOre");
                nrMaxOre = rs.getInt("nrMaxOre");
                adresa = rs.getString("adresa");
                nrTelefon = rs.getString("nrTelefon");
                email = rs.getString("email");
                username = rs.getString("username");
                parola = rs.getString("parola");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public Profesor(int id, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
        this.departament = departament;
        this.nrMinOre = nrMinOre;
        this.nrMaxOre = nrMaxOre;
    }

    public static List<Profesor> getTable() {
        List<Profesor> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idProfesor FROM Profesor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idProfesor = rs.getInt("idProfesor");
                list.add(new Profesor(idProfesor));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idProfesor, Profesor other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Profesor SET cnp = ?, nume = ?, departament = ?, nrMinOre = ?, nrMaxOre = ?, adresa = ?, nrTelefon = ?, email = ?, username = ?, parola = ? WHERE idProfesor = ?");
            stmt.setString(1, other.cnp);
            stmt.setString(2, other.nume);
            stmt.setString(3, other.departament);
            stmt.setInt(4, other.nrMinOre);
            stmt.setInt(5, other.nrMaxOre);
            stmt.setString(6, other.adresa);
            stmt.setString(7, other.nrTelefon);
            stmt.setString(8, other.email);
            stmt.setString(9, other.username);
            stmt.setString(10, other.parola);
            stmt.setInt(11, idProfesor);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idProfesor, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Profesor SET ? = ? WHERE idProfesor = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idProfesor);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idProfesor, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Profesor SET ? = ? WHERE idProfesor = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idProfesor);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idProfesor) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Profesor WHERE idProfesor = ?");
            stmt.setInt(1, idProfesor);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(Profesor newProfesor) {
        Connection connection = Connect.getConnection();
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL AddNewProfesor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, newProfesor.cnp);
            stmt.setString(2, newProfesor.nume);
            stmt.setString(3, newProfesor.departament);
            stmt.setInt(4, newProfesor.nrMinOre);
            stmt.setInt(5, newProfesor.nrMaxOre);
            stmt.setString(6, newProfesor.adresa);
            stmt.setString(7, newProfesor.nrTelefon);
            stmt.setString(8, newProfesor.email);
            stmt.setString(9, newProfesor.username);
            stmt.setString(10, newProfesor.parola);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public String getDepartament() {
        return departament;
    }

    public int getNrMinOre() {
        return nrMinOre;
    }

    public int getNrMaxOre() {
        return nrMaxOre;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "departament='" + departament + '\'' +
                ", nrMinOre=" + nrMinOre +
                ", nrMaxOre=" + nrMaxOre +
                ", id=" + id +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", idRol=" + idRol +
                '}';
    }

    private String departament;
    private int nrMinOre;
    private int nrMaxOre;
}
