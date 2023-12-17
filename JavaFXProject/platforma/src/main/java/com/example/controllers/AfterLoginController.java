package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import com.example.sql.roles.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

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
                for(String columnName: columns)
                {
                    TableColumn<User, String> column = new TableColumn<>(columnName);
                    table.getColumns().add(column);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
