package com.example;

import com.example.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuperAdministrator extends User {
    public SuperAdministrator(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SuperAdministrator WHERE idSuperAdmin = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idSuperAdmin");
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

    public SuperAdministrator(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
    }

    public static List<SuperAdministrator> getTable() {
        List<SuperAdministrator> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idSuperAdmin FROM SuperAdministrator");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idSuperAdmin = rs.getInt("idSuperAdmin");
                list.add(new SuperAdministrator(idSuperAdmin));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idSuperAdmin, SuperAdministrator other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE SuperAdministrator SET cnp = ?, nume = ?, adresa = ?, nrTelefon = ?, email = ?, username = ?, parola = ? WHERE idSuperAdmin = ?");
            stmt.setString(1, other.cnp);
            stmt.setString(2, other.nume);
            stmt.setString(3, other.adresa);
            stmt.setString(4, other.nrTelefon);
            stmt.setString(5, other.email);
            stmt.setString(6, other.username);
            stmt.setString(7, other.parola);
            stmt.setInt(8, idSuperAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idSuperAdmin, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE SuperAdministrator SET ? = ? WHERE idSuperAdmin = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idSuperAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idSuperAdmin, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE SuperAdministrator SET ? = ? WHERE idSuperAdmin = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idSuperAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idSuperAdmin) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM SuperAdministrator WHERE idSuperAdmin = ?");
            stmt.setInt(1, idSuperAdmin);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(SuperAdministrator newSuperAdmin) {
        Connection connection = Connect.getConnection();
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL AddNewSuperAdministrator(?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, newSuperAdmin.cnp);
            stmt.setString(2, newSuperAdmin.nume);
            stmt.setString(3, newSuperAdmin.adresa);
            stmt.setString(4, newSuperAdmin.nrTelefon);
            stmt.setString(5, newSuperAdmin.email);
            stmt.setString(6, newSuperAdmin.username);
            stmt.setString(7, newSuperAdmin.parola);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    @Override
    public String toString() {
        return "SuperAdministrator{" +
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
