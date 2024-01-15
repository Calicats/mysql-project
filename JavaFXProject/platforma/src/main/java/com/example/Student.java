package com.example;

import com.example.sql.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    public Student(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Student WHERE idStudent = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idStudent");
                cnp = rs.getString("cnp");
                nume = rs.getString("nume");
                anStudiu = rs.getInt("anStudiu");
                numarOre = rs.getInt("numarOre");
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

    public Student(int id, String cnp, String nume, int anStudiu, int numarOre, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
        this.anStudiu = anStudiu;
        this.numarOre = numarOre;
    }

    public static List<Student> getTable() {
        List<Student> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT idStudent FROM Student");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idStudent = rs.getInt("idStudent");
                list.add(new Student(idStudent));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public static int updateEntry(int idStudent, Student other) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Student SET cnp = ?, nume = ?, anStudiu = ?, numarOre = ?, adresa = ?, nrTelefon = ?, email = ?, username = ?, parola = ? WHERE idStudent = ?");
            stmt.setString(1, other.cnp);
            stmt.setString(2, other.nume);
            stmt.setInt(3, other.anStudiu);
            stmt.setInt(4, other.numarOre);
            stmt.setString(5, other.adresa);
            stmt.setString(6, other.nrTelefon);
            stmt.setString(7, other.email);
            stmt.setString(8, other.username);
            stmt.setString(9, other.parola);
            stmt.setInt(10, idStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idStudent, String column, String value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Student SET ? = ? WHERE idStudent = ?");
            stmt.setString(1, column);
            stmt.setString(2, value);
            stmt.setInt(3, idStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int updateEntry(int idStudent, String column, int value) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE Student SET ? = ? WHERE idStudent = ?");
            stmt.setString(1, column);
            stmt.setInt(2, value);
            stmt.setInt(3, idStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int deleteEntry(int idStudent) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM Student WHERE idStudent = ?");
            stmt.setInt(1, idStudent);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public static int insert(Student newStudent) {
        Connection connection = Connect.getConnection();
        try {
            CallableStatement stmt = connection.prepareCall(
                    "{CALL AddNewStudent(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, newStudent.cnp);
            stmt.setString(2, newStudent.nume);
            stmt.setInt(3, newStudent.anStudiu);
            stmt.setInt(4, newStudent.numarOre);
            stmt.setString(5, newStudent.adresa);
            stmt.setString(6, newStudent.nrTelefon);
            stmt.setString(7, newStudent.email);
            stmt.setString(8, newStudent.username);
            stmt.setString(9, newStudent.parola);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

    public int getAnStudiu() {
        return anStudiu;
    }

    public int getNumarOre() {
        return numarOre;
    }

    @Override
    public String toString() {
        return "Student{" +
                "anStudiu=" + anStudiu +
                ", numarOre=" + numarOre +
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

    private int anStudiu;
    private int numarOre;
}
