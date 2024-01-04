/* ENTRY POINT: DBPlatforma.java */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuperAdministrator extends HumanUser {
    public SuperAdministrator(int id) {
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SuperAdministrator WHERE idSuperAdmin = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idSuperAdmin");
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

    public static List<SuperAdministrator> getTable() {
        List<SuperAdministrator> list = new ArrayList<>();
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SuperAdministrator");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idSuperAdmin = rs.getInt("idSuperAdmin");
                String cnp = rs.getString("cnp");
                String nume = rs.getString("nume");
                String adresa = rs.getString("adresa");
                String nrTelefon = rs.getString("nrTelefon");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String parola = rs.getString("parola");
                int idRol = rs.getInt("idRol");
                list.add(new SuperAdministrator(idSuperAdmin, cnp, nume, adresa, nrTelefon, email, username, parola, idRol));
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

    public SuperAdministrator(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(id, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
    }
}
