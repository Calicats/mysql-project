import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Random;

public class Main {
    public static void main(String[] args)  {
        String dbUrl = "jdbc:mysql://localhost:3306/db_platforma";
        String username = "root";
        String password = "durdeu10";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {

            String name = RandomNameGenerator.generateRandomName();
            ProcedureCaller.addNewStudent(connection, RandomNameGenerator.generateRandomCnp(), name, new Random().nextInt(6), new Random().nextInt(16), "Str " + new Random().nextInt(100), RandomNameGenerator.generateRandomCnp(), name.replace(" ", "")+"@example.com", "student_password");
            name = RandomNameGenerator.generateRandomName();
            ProcedureCaller.addNewProfessor(connection, RandomNameGenerator.generateRandomCnp(), name, "Departament " + new Random().nextInt(100), new Random().nextInt(16), new Random().nextInt(16), "Str " + new Random().nextInt(100), RandomNameGenerator.generateRandomCnp(), name.replace(" ", "")+"@example.com", "professor_password");
            name = RandomNameGenerator.generateRandomName();
            ProcedureCaller.addNewAdministrator(connection, RandomNameGenerator.generateRandomCnp(), name, "Str " + new Random().nextInt(100), RandomNameGenerator.generateRandomCnp(), name.replace(" ", "")+"@example.com", "administrator_password");
            name = RandomNameGenerator.generateRandomName();
            ProcedureCaller.addNewSuperAdministrator(connection, RandomNameGenerator.generateRandomCnp(), name, "Str " + new Random().nextInt(100), RandomNameGenerator.generateRandomCnp(), name.replace(" ", "")+"@example.com", "superadministrator_password");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







