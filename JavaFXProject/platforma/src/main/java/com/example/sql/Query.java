package com.example.sql;

import java.sql.*;

public class Query {
    public static String validateUser(Connection connection, String username, String password) {
        String query = "SELECT id_rol FROM utilizator WHERE BINARY username = ? AND BINARY parola = ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int id_rol = resultSet.getInt("id_rol");
                switch(id_rol)
                {
                    case 1:
                        return "superadministrator";
                    case 2:
                        return "administrator";
                    case 3:
                        return "profesor";
                    case 4:
                        return "student";
                    default:
                        return null;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
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
