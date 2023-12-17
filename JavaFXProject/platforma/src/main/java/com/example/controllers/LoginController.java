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

    public void userLogin() throws IOException{
        checkLogin();
    }

    private void checkLogin() throws IOException{
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
                HashMap<Integer, String> possibleTables = new HashMap<>();
                possibleTables.put(1, "superadministrator");
                possibleTables.put(2, "administrator");
                possibleTables.put(3, "profesor");
                possibleTables.put(4, "student");

                int[] idRol = {1, 2, 3, 4};
                boolean found = false;

                for(int i = 0; i < 4; ++i)
                {
                    String tableName = possibleTables.get(idRol[i]);
                    boolean connected = Query.validateUser(connection, tableName, usernameString, passwordString, idRol[i]);
                    if(connected)
                    {
                        System.out.println(usernameString + " has logged in!");
                        loginMessage.setText("Logare cu succes!");
                        main.changeScene("dupaLogare.fxml", usernameString, tableName, 1024, 768);
                        found = true;
                        break;
                    }
                }
                if(!found)
                {
                    loginMessage.setText("Logarea a esuat!");
                    System.out.println(usernameString + " does not exist!");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}