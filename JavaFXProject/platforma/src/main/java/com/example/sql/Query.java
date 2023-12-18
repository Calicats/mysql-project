package com.example.sql;

import java.sql.*;
import java.util.*;

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

    public static String getSingleInfo(Connection connection, String tableName, String username, String info)
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

    public static String[] getAllInfoOnUser(Connection connection, String tableName, String username)
    {
        String query = getQueryForAllInfoOnUser(tableName);
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if(resultSet.next())
            {
                String[] userInfo = new String[columnCount];
                for(int i = 1; i <= columnCount; ++i)
                {
                    userInfo[i - 1] = resultSet.getString(i);
                }
                return userInfo;
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
                String columnName = resultSet.getString("COLUMN_NAME");
                if(!columnName.contains("id"))
                {
                    ++columnCount;
                }
            }

            resultSet.beforeFirst();

            String[] columnNames = new String[columnCount];

            int i = 0;
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                if(!columnName.contains("id"))
                {
                    columnNames[i++] = columnName;
                }
            }

            return columnNames;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[][] getTableInfo(Connection connection, String tableName)
    {
        String query = getQueryForTableInfo(tableName);

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<String[]> rows = new ArrayList<>();

            while (resultSet.next())
            {
                String[] rowData = new String[columnCount];

                for(int i = 0; i < columnCount; i++)
                {
                    rowData[i] = resultSet.getString(i + 1);
                }

                rows.add(rowData);
            }

            String[][] result = new String[rows.size()][];
            rows.toArray(result);

            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static String getQueryForTableInfo(String tableName) {
        String query = null;
        if(tableName.equals("Superadministrator"))
        {
            query = "SELECT * FROM superadministrator";
        }
        else if(tableName.equals("Administrator"))
        {
            query = "SELECT * FROM administrator";
        }
        else if(tableName.equals("Profesor"))
        {
            query = "SELECT * FROM profesor";
        }
        else if(tableName.equals("Student"))
        {
            query = "SELECT * FROM student";
        }
        return query;
    }

    private static String getQueryForAllInfoOnUser(String tableName)
    {
        String query = null;
        if(tableName.equals("Superadministrator"))
        {
            query = "SELECT * FROM superadministrator WHERE username = ?";
        }
        else if(tableName.equals("Administrator"))
        {
            query = "SELECT * FROM administrator WHERE username = ?";
        }
        else if(tableName.equals("Profesor"))
        {
            query = "SELECT * FROM profesor WHERE username = ?";
        }
        else if(tableName.equals("Student"))
        {
            query = "SELECT * FROM student WHERE username = ?";
        }
        return query;
    }

}
