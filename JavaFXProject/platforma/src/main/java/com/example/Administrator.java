package com.example;

import com.example.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    public static final int ID_ROL = 2;

    public Administrator(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Administrator WHERE idAdmin = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idAdmin");
                cnp = rs.getString("cnp");
                nume = rs.getString("nume");
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

    public Administrator(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
    }

    public static List<Administrator> getTable() {
        List<Administrator> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idAdmin FROM Administrator");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idAdmin = rs.getInt("idAdmin");
                list.add(new Administrator(idAdmin));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idAdmin, Administrator other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Administrator SET cnp = ?, nume = ?, adresa = ?, nrTelefon = ?, email = ?, username = ?, parola = ? WHERE idAdmin = ?");
            stmt.setString(1, other.cnp);
            stmt.setString(2, other.nume);
            stmt.setString(3, other.adresa);
            stmt.setString(4, other.nrTelefon);
            stmt.setString(5, other.email);
            stmt.setString(6, other.username);
            stmt.setString(7, other.parola);
            stmt.setInt(8, idAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idAdmin, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Administrator SET ? = ? WHERE idAdmin = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idAdmin, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Administrator SET ? = ? WHERE idAdmin = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idAdmin) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Administrator WHERE idAdmin = ?");
            stmt.setInt(1, idAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(Administrator newAdmin) {
        Connection connection = Connect.getConnection();
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL AddNewAdministrator(?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, newAdmin.cnp);
            stmt.setString(2, newAdmin.nume);
            stmt.setString(3, newAdmin.adresa);
            stmt.setString(4, newAdmin.nrTelefon);
            stmt.setString(5, newAdmin.email);
            stmt.setString(6, newAdmin.username);
            stmt.setString(7, newAdmin.parola);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + id +
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
}
