/* ENTRY POINT: DBPlatforma.java */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends HumanUser {
    public Administrator(int id) {
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Administrator WHERE idAdmin = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            this.id = rs.getInt("idAdmin");
            cnp = rs.getString("cnp");
            nume = rs.getString("nume");
            adresa = rs.getString("adresa");
            nrTelefon = rs.getString("nrTelefon");
            email = rs.getString("email");
            username = rs.getString("username");
            parola = rs.getString("parola");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<Administrator> getTable() {
        List<Administrator> list = new ArrayList<>();
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Administrator");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idAdmin");
                String cnp = rs.getString("cnp");
                String nume = rs.getString("nume");
                String adresa = rs.getString("adresa");
                String nrTelefon = rs.getString("nrTelefon");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String parola = rs.getString("parola");
                int idRol = rs.getInt("idRol");
                list.add(new Administrator(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol));
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

    private Administrator(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
    }
}