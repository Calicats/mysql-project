package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;

public class AfterLoginController {
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
    private String username;
    private String tableName;
    private boolean showPersonalInfo = true;

    public void initUser(String username, String tableName) {
        this.username = username;
        this.tableName = tableName;
        greetUser.setText("Bine ai venit, " + this.username + "!");
    }

    public void userLogout() throws IOException{
        Main main = new Main();
        System.out.println("Bye, " + username);
        main.changeScene("logare.fxml", username, tableName);
    }

    public void showPersonalData(){
        try
        {
            if(showPersonalInfo)
            {
                Connection connection = Connect.getConnection();
                String numeString = Query.getInfo(connection, tableName, username, "nume");
                String cnpString= Query.getInfo(connection, tableName, username, "CNP");
                String adresaString = Query.getInfo(connection, tableName, username, "adresa");
                String emailString = Query.getInfo(connection, tableName, username, "email");
                String nrTelefonString = Query.getInfo(connection, tableName, username, "nrTelefon");

                nume.setText("Nume: " + numeString);
                cnp.setText("CNP: " + cnpString);
                adresa.setText("Adresa: " + adresaString);
                email.setText("Email: " + emailString);
                nrTelefon.setText("Nr Telefon: " + nrTelefonString);
            }
            else
            {
                nume.setText("");
                cnp.setText("");
                adresa.setText("");
                email.setText("");
                nrTelefon.setText("");
            }

            showPersonalInfo = !showPersonalInfo;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
