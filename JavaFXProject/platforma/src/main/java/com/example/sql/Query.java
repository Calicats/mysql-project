package com.example.sql;

import java.sql.*;

public class Query {
    public static boolean validateUser(Connection connection, String tableName, String username, String password, int userType) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE username = ? AND parola = ? AND idRol = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, userType);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static String getInfo(Connection connection, String tableName, String username, String info)
    {
        String query = "SELECT " + info + " FROM " + tableName + " WHERE username = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getString(info);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getColumnNames(Connection connection, String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "db_platforma", tableName, null);

            int columnCount = 0;
            while (resultSet.next()) {
                columnCount++;
            }

            resultSet.beforeFirst();

            String[] columnNames = new String[columnCount];
            int i = 0;

            while (resultSet.next()) {
                columnNames[i++] = resultSet.getString("COLUMN_NAME");
            }

            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
