/* ENTRY POINT: DBPlatforma.java */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivitateProfesor {
    public ActivitateProfesor(int id) {
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateProfesor WHERE idActivitateProfesor = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // TODO: Silently Fail instead of throwing exception?
            this.id = rs.getInt("idActivitateProfesor");
            tipActivitate = rs.getString("tipActivitate");
            descriere = rs.getString("descriere");
            nrMaximStudenti = rs.getInt("nrMaximStudenti");
            id_profesor = rs.getInt("id_profesor");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public static List<ActivitateProfesor> getTable() {
        List<ActivitateProfesor> list = new ArrayList<>();
        Connection connection = DBPlatforma.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ActivitateProfesor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idActivitateProfesor");
                String tipActivitate = rs.getString("tipActivitate");
                String descriere = rs.getString("descriere");
                int nrMaximStudenti = rs.getInt("nrMaximStudenti");
                int id_profesor = rs.getInt("id_profesor");
                list.add(new ActivitateProfesor(id, tipActivitate, descriere, nrMaximStudenti, id_profesor));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public String getTipActivitate() {
        return tipActivitate;
    }

    public String getDescriere() {
        return descriere;
    }

    public int getNrMaximStudenti() {
        return nrMaximStudenti;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    @Override
    public String toString() {
        return "ActivitateProfesor{" +
                "id=" + id +
                ", tipActivitate='" + tipActivitate + '\'' +
                ", descriere='" + descriere + '\'' +
                ", nrMaximStudenti=" + nrMaximStudenti +
                ", id_profesor=" + id_profesor +
                '}';
    }

    private int id;
    private String tipActivitate;
    private String descriere;
    private int nrMaximStudenti;
    private int id_profesor;

    private ActivitateProfesor(int id, String tipActivitate, String descriere, int nrMaximStudenti, int id_profesor) {
        this.id = id;
        this.tipActivitate = tipActivitate;
        this.descriere = descriere;
        this.nrMaximStudenti = nrMaximStudenti;
        this.id_profesor = id_profesor;
    }
}
