import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
public class ProcedureCaller {
    public static void addNewStudent(Connection connection, String cnp, String nume, int anStudiu, int numarOre, String adresa,
                                      String nrTelefon, String email, String parola, String username) throws Exception {
        try (CallableStatement statement = connection.prepareCall("{call AddNewStudent(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            statement.setString(1, cnp);
            statement.setString(2, nume);
            statement.setInt(3, anStudiu);
            statement.setInt(4, numarOre);
            statement.setString(5, adresa);
            statement.setString(6, nrTelefon);
            statement.setString(7, email);
            statement.setString(8, parola);
            statement.setString(9, username);
            statement.execute();
        }
    }

    public static void addNewProfessor(Connection connection, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre,
                                        String adresa, String nrTelefon, String email, String parola, String username) throws Exception {
        try (CallableStatement statement = connection.prepareCall("{call AddNewProfessor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            statement.setString(1, cnp);
            statement.setString(2, nume);
            statement.setString(3, departament);
            statement.setInt(4, nrMinOre);
            statement.setInt(5, nrMaxOre);
            statement.setString(6, adresa);
            statement.setString(7, nrTelefon);
            statement.setString(8, email);
            statement.setString(9, parola);
            statement.setString(10, username);
            statement.execute();
        }
    }

    public static void addNewAdministrator(Connection connection, String cnp, String nume, String adresa, String nrTelefon,
                                            String email, String parola, String username) throws Exception {
        try (CallableStatement statement = connection.prepareCall("{call AddNewAdministrator(?, ?, ?, ?, ?, ?, ?)}")) {
            statement.setString(1, cnp);
            statement.setString(2, nume);
            statement.setString(3, adresa);
            statement.setString(4, nrTelefon);
            statement.setString(5, email);
            statement.setString(6, parola);
            statement.setString(7, username);
            statement.execute();
        }
    }

    public static void addNewSuperAdministrator(Connection connection, String cnp, String nume, String adresa, String nrTelefon,
                                                 String email, String parola, String username) throws Exception {
        try (CallableStatement statement = connection.prepareCall("{call AddNewSuperAdministrator(?, ?, ?, ?, ?, ?, ?)}")) {
            statement.setString(1, cnp);
            statement.setString(2, nume);
            statement.setString(3, adresa);
            statement.setString(4, nrTelefon);
            statement.setString(5, email);
            statement.setString(6, parola);
            statement.setString(7, username);
            statement.execute();
        }
    }
}
