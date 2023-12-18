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

public class AfterLoginController{
    @FXML
    private Button logoutButton;
    @FXML
    private Button personalDataButton;
    @FXML
    private Label greetUser;
    @FXML
    private Label nume;
    @FXML
    private Label cnp;
    @FXML
    private Label adresa;
    @FXML
    private Label email;
    @FXML
    private Label nrTelefon;
    @FXML
    private Label msgControlUsers;
    @FXML
    private TableView<User> table;
    @FXML
    private ComboBox<String> comboBox;

    private String username;
    private String tableName;

    public void initUser(String username, String tableName) {
        this.username = username;
        this.tableName = tableName;

        greetUser.setText("Bine ai venit, " + this.username + "!");

        table.setVisible(false);
        comboBox.setVisible(false);
        msgControlUsers.setVisible(false);
    }

    public void userLogout() throws IOException{
        Main main = new Main();
        System.out.println("Bye, " + username);

        table.getItems().clear();
        table.getColumns().clear();

        comboBox.getItems().clear();

        main.changeScene("logare.fxml", username, tableName, 600, 400);
    }

    public void showPersonalData() {
        try
        {
            Connection connection = Connect.getConnection();
            String numeString = Query.getInfo(connection, tableName, username, "nume");
            String cnpString= Query.getInfo(connection, tableName, username, "CNP");
            String adresaString = Query.getInfo(connection, tableName, username, "adresa");
            String emailString = Query.getInfo(connection, tableName, username, "email");
            String nrTelefonString = Query.getInfo(connection, tableName, username, "nrTelefon");

            greetUser.setVisible(true);
            nume.setVisible(true);
            cnp.setVisible(true);
            adresa.setVisible(true);
            email.setVisible(true);
            nrTelefon.setVisible(true);

            table.setVisible(false);
            table.getItems().clear();
            table.getColumns().clear();

            comboBox.setVisible(false);
            comboBox.getItems().clear();

            msgControlUsers.setVisible(false);

            greetUser.setText("Bine ai venit, " + this.username + "!");
            nume.setText("Nume: " + numeString);
            cnp.setText("CNP: " + cnpString);
            adresa.setText("Adresa: " + adresaString);
            email.setText("Email: " + emailString);
            nrTelefon.setText("Nr Telefon: " + nrTelefonString);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void controlUsers() {
        greetUser.setVisible(false);
        nume.setVisible(false);
        cnp.setVisible(false);
        adresa.setVisible(false);
        email.setVisible(false);
        nrTelefon.setVisible(false);

        table.setVisible(true);

        comboBox.setVisible(true);
        ObservableList<String> list = FXCollections.observableArrayList("Superadministrator", "Administrator", "Profesor", "Student");
        comboBox.setItems(list);

        msgControlUsers.setVisible(true);
    }

    public void selectTable(){
        table.getItems().clear();
        table.getColumns().clear();

        String tableName = comboBox.getValue();
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
                for(String columnName : columns)
                {
                    TableColumn<User, String> column = new TableColumn<>(columnName);

                    column.setCellValueFactory(cellData ->
                            new SimpleStringProperty(getUserPropertyValue(cellData.getValue(), columnName)));

                    column.setMinWidth(200);
                    table.getColumns().add(column);
                }

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
                }

                table.setItems(list);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
