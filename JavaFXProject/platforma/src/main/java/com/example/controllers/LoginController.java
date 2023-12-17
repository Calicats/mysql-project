package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField parola;

    public void userLogin() {
        checkLogin();
    }

    private void checkLogin() {
        try
        {
            Main main = new Main();
            String usernameString = username.getText();
            String passwordString = parola.getText();
            if(usernameString.isEmpty() || passwordString.isEmpty())
            {
                loginMessage.setText("Te rog introdu datele!");
            }
            else
            {
                Connection connection = Connect.getConnection();
                String tableName = Query.validateUser(connection, usernameString, passwordString);

                if(tableName != null)
                {
                    System.out.println(usernameString + " logged in as " + tableName);
                    main.changeScene("dupaLogare.fxml", usernameString, tableName, 1024, 768);
                }
                else
                {
                    loginMessage.setText("Date incorecte!");
                    System.out.println("null");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}