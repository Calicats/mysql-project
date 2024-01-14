package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
