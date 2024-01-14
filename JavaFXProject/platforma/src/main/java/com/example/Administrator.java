package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
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
