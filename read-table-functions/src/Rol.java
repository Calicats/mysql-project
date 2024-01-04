/* ENTRY POINT: DBPlatforma.java */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Rol {
    public Rol(int id) {
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Rol WHERE idRol = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idRol");
            numeRol = rs.getString("numeRol");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<Rol> getTable() {
        List<Rol> list = new ArrayList<>();
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Rol");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idRol");
                String numeRol = rs.getString("numeRol");
                list.add(new Rol(id, numeRol));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getNumeRol() {
        return numeRol;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", numeRol='" + numeRol + '\'' +
                '}';
    }

    int id;
    String numeRol;

    private Rol(int id, String numeRol) {
        this.id = id;
        this.numeRol = numeRol;
    }
}
