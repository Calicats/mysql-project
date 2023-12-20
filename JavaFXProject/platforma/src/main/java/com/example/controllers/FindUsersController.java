package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

public class FindUsersController {
    @FXML
    private Button backButton;
    @FXML
    private Button findUsernameButton;
    @FXML
    private TextField findUsernameField;
    @FXML
    private ComboBox<String> selectTableComboBox;
    @FXML
    private Label foundUser;
    @FXML
    private TableView<User> table;
    private String username;
    private String tableName;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        ObservableList<String> tablesList = FXCollections.observableArrayList();
        String[] tables = {"Superadministrator", "Administrator", "Profesor", "Student"};
        tablesList.addAll(Arrays.asList(tables).subList(0, 4));

        selectTableComboBox.setItems(tablesList);
    }

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("dupaLogare.fxml", username, tableName, 1024, 768);
    }

    public void onSelectTable() {
        table.getItems().clear();
        table.getColumns().clear();
        findUsernameField.clear();
        foundUser.setText("");

        String tableName = selectTableComboBox.getValue();
        if(tableName == null)
        {
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            String[] columns = Query.getColumnNames(connection, tableName);
            if(columns == null)
            {
                System.out.println("null");
            }
            else
            {
                generateTable(columns);
                populateTable(connection, tableName);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onSearchUsername()
    {
        String tableName = selectTableComboBox.getValue();

        table.getItems().clear();
        table.getColumns().clear();

        if(tableName == null)
        {
            foundUser.setText("Selecteaza o tabela!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            String findUsernameString = findUsernameField.getText();
            if(findUsernameString.isEmpty())
            {
                foundUser.setText("Introdu un utilizator!");
                return;
            }

            String[] userInfo = Query.getAllInfoOnUser(connection, tableName, findUsernameString);
            String[] columns = Query.getColumnNames(connection, tableName);
            if(userInfo == null || columns == null)
            {
                foundUser.setText("Utilizatorul nu exista!");
                System.out.println("null");
                return;
            }

            ObservableList<User> list = FXCollections.observableArrayList();
            for(String value: userInfo)
            {
                System.out.print(value + "\t");
            }
            System.out.println();
            foundUser.setText("");

            generateTable(columns);
            list.add(rowToUserInfo(userInfo, tableName));
            table.setItems(list);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void generateTable(String[] columns) {
        for (String columnName : columns) {
            TableColumn<User, String> column = new TableColumn<>(columnName);

            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(getUserPropertyValue(cellData.getValue(), columnName)));

            table.getColumns().add(column);
        }
    }

    private String getUserPropertyValue(User user, String propertyName) {
        if(user instanceof SuperAdministrator)
        {
            switch(propertyName)
            {
                case "idSuperAdmin":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Administrator)
        {
            switch(propertyName)
            {
                case "idAdmin":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Profesor)
        {
            switch(propertyName)
            {
                case "idProfesor":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "departament":
                    return ((Profesor) user).getDepartament();
                case "nrMinOre":
                    return String.valueOf(((Profesor) user).getNrMinOre());
                case "nrMaxOre":
                    return String.valueOf(((Profesor) user).getNrMaxOre());
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Student)
        {
            switch(propertyName)
            {
                case "idStudent":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "anStudiu":
                    return String.valueOf(((Student) user).getAnStudiu());
                case "numarOre":
                    return String.valueOf(((Student) user).getNumarOre());
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        return null;
    }

    private void populateTable(Connection connection, String tableName) {
        ObservableList<User> list = FXCollections.observableArrayList();
        String[][] userInfo = Query.getTableInfo(connection, tableName);

        if(userInfo == null)
        {
            System.out.println("null");
        }
        else
        {
            for (String[] row : userInfo)
            {
                for(String value : row)
                {
                    System.out.print(value + "\t");
                }
                System.out.println();
                User user = rowToUserInfo(row, tableName);
                list.add(user);
            }
            table.setItems(list);
        }
    }

    private User rowToUserInfo(String[] row, String tableName) {
        switch (tableName) {
            case "Superadministrator":
                return new SuperAdministrator(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        row[4], row[5], row[6], row[7], Integer.parseInt(row[8]));
            case "Administrator":
                return new Administrator(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        row[4], row[5], row[6], row[7], Integer.parseInt(row[8]));
            case "Profesor":
                return new Profesor(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        Integer.parseInt(row[4]), Integer.parseInt(row[5]), row[6], row[7],
                        row[8], row[9], row[10], Integer.parseInt(row[11]));
            case "Student":
                return new Student(Integer.parseInt(row[0]), row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]),
                        row[5], row[6], row[7], row[8], row[9],Integer.parseInt(row[10]));
            default:
                return null;
        }
    }
}