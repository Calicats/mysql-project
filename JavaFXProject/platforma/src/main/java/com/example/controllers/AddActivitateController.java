package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Insert;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

public class AddActivitateController {
    @FXML
    private Button addActivitateButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField descriptionActivitateField;
    @FXML
    private ComboBox<String> selectActivitateComboBox;
    @FXML
    private TextField usernameProfesorField;
    @FXML
    private Label errorHandling;
    private String username;
    private String tableName;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        ObservableList<String> list = FXCollections.observableArrayList();
        String[] activities = {"Curs", "Seminar", "Laborator", "Colocviu", "Examen", "Restanta"};
        list.addAll(Arrays.asList(activities).subList(0, 6));

        selectActivitateComboBox.setItems(list);
    }

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("gestionareUtilizatori.fxml", username, tableName, 1024, 768);
    }

    public void onAddActivitate()
    {
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        String usernameString = usernameProfesorField.getText();
        String activitateString = selectActivitateComboBox.getValue();
        String descriptionString = descriptionActivitateField.getText();

        if(usernameString.isEmpty() || activitateString.isEmpty() || descriptionString.isEmpty())
        {
            errorHandling.setText("Introdu date in toate campurile!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();

            if(connection == null)
            {
                return;
            }

            boolean existsProfesor = Query.userExistsInTable(connection, usernameString, "profesor");

            if(existsProfesor)
            {
                Insert.addActivitateProfesor(connection, usernameString, activitateString, descriptionString);
                errorHandling.setTextFill(Color.rgb(0, 255, 0));
                errorHandling.setText("Activitate adaugata cu succes!");
            }
            else
            {
                errorHandling.setText("Introdu un utilizator valid!");
            }

            clearAllFields();
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            clearAllFields();
            e.printStackTrace();
        }
    }

    private void clearAllFields()
    {
        usernameProfesorField.clear();
        selectActivitateComboBox.setValue(null);
        descriptionActivitateField.clear();
    }
}
