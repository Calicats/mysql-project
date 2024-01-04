/* ENTRY POINT: DBPlatforma.java */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utilizator {
    public Utilizator(int id) {
        Connection connection = DBPlatforma.getConnection();
        try {
            // FIXME: intellij complains about missing table even though I created it
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Utilizator WHERE idUtilizator = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idUtilizator");
            username = rs.getString("username");
            cnp = rs.getString("cnp");
            parola = rs.getString("parola");
            id_rol = rs.getInt("id_rol");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<Utilizator> getTable() {
        List<Utilizator> list = new ArrayList<>();
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Utilizator");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idUtilizator");
                String username = rs.getString("username");
                String cnp = rs.getString("cnp");
                String parola = rs.getString("parola");
                int id_rol = rs.getInt("id_rol");
                list.add(new Utilizator(id, username, cnp, parola, id_rol));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCnp() {
        return cnp;
    }

    public String getParola() {
        return parola;
    }

    public int getId_rol() {
        return id_rol;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cnp='" + cnp + '\'' +
                ", parola='" + parola + '\'' +
                ", id_rol=" + id_rol +
                '}';
    }

    private int id;
    private String username;
    private String cnp;
    private String parola;
    private int id_rol;

    private Utilizator(int id, String username, String cnp, String parola, int id_rol) {
        this.id = id;
        this.username = username;
        this.cnp = cnp;
        this.parola = parola;
        this.id_rol = id_rol;
    }
}
