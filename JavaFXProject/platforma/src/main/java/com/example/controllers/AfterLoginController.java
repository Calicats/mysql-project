package com.example.controllers;

import com.example.platforma.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLoginController {
    @FXML
    private Button logoutButton;
    public void userLogout() throws IOException{
        Main main = new Main();
        main.changeScene("logare.fxml");
    }
}
