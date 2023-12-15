import java.sql.*;

public class Model {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/db_platforma";
    private static final String username = "root";
    private static final String password = "Sharldevil16!";
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
    public static boolean validateUser(Connection connection, String tableName, String username, String password, int userType) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE username = ? AND parola = ? AND idRol = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, userType);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
