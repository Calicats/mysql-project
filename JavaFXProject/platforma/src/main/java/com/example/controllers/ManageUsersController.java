package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Insert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

public class ManageUsersController {
    @FXML
    private Button operationButton;
    @FXML
    private TextField adresaField;
    @FXML
    private Button backButton;
    @FXML
    private TextField cnpField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField nrTelefonField;
    @FXML
    private PasswordField parolaField;
    @FXML
    private ComboBox<String> selectOperationComboBox;
    @FXML
    private ComboBox<String> selectTableComboBox;
    @FXML
    private TextField usernameField;
    @FXML
    private Label additionalLabel1;
    @FXML
    private Label additionalLabel2;
    @FXML
    private Label additionalLabel3;
    @FXML
    private Label errorHandling;
    @FXML
    private TextField additionalField1;
    @FXML
    private TextField additionalField2;
    @FXML
    private TextField additionalField3;
    private String username;
    private String tableName;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        ObservableList<String> tablesList = FXCollections.observableArrayList();
        ObservableList<String> operationList = FXCollections.observableArrayList("Adauga", "Modifica", "Sterge");
        String[] tables = {"Superadministrator", "Administrator", "Profesor", "Student"};

        if(tableName.equals("superadministrator"))
        {
            tablesList.addAll(Arrays.asList(tables).subList(0, 4));
        }
        else if(tableName.equals("administrator"))
        {
            tablesList.addAll(Arrays.asList(tables).subList(0, 3));
        }

        selectTableComboBox.setItems(tablesList);
        selectOperationComboBox.setItems(operationList);
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        makeAdditionalFieldsInvisible();
    }

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("dupaLogare.fxml", username, tableName, 1024, 768);
    }

    public void onSelectOperation()
    {
        String buttonText = selectOperationComboBox.getValue();
        operationButton.setText(buttonText);
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        clearAllFields();
    }

    public void onOperationButton() {
        String tableName = selectTableComboBox.getValue();
        String operationName = selectOperationComboBox.getValue();
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        if (tableName == null) {
            errorHandling.setText("Selecteaza o tabela!");
            return;
        }

        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            System.out.println("null");
            return;
        }

        String numeString = nameField.getText();
        String cnpString = cnpField.getText();
        String adresaString = adresaField.getText();
        String emailString = emailField.getText();
        String nrTelefonString = nrTelefonField.getText();
        String usernameString = usernameField.getText();
        String parolaString = parolaField.getText();

        switch (tableName) {
            case "Superadministrator" -> {
                if (oneIsEmpty(numeString, cnpString, adresaString, emailString, nrTelefonString, usernameString, parolaString)) {
                    errorHandling.setText("Introdu date in toate campurile!");
                    return;
                }
                if (operationName == null) {
                    errorHandling.setText("Selecteaza o operatie!");
                    return;
                }
                if (operationName.equals("Adauga")) {
                    System.out.println("Se va adauga un superadmin!");
                    boolean ex = false;
                    try {
                        Insert.addSuperAdmin(connection, cnpString, numeString, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    } catch (Exception e) {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }
                    if(!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Superadministrator adaugat cu succes!");
                    }
                    clearAllFields();
                }
            }
            case "Administrator" -> {
                if (oneIsEmpty(numeString, cnpString, adresaString, emailString, nrTelefonString, usernameString, parolaString)) {
                    errorHandling.setText("Introdu date in toate campurile!");
                    return;
                }
                if (operationName == null) {
                    errorHandling.setText("Selecteaza o operatie!");
                    return;
                }
                if (operationName.equals("Adauga")) {
                    System.out.println("Se va adauga un admin!");
                    boolean ex = false;
                    try
                    {
                        Insert.addAdmin(connection, cnpString, numeString, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    }
                    catch(Exception e)
                    {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }
                    if(!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Administrator adaugat cu succes!");
                    }
                    clearAllFields();
                }
            }
            case "Profesor" -> {
                String departamentString = additionalField1.getText();
                String nrMinOreString = additionalField2.getText();
                String nrMaxOreString = additionalField3.getText();
                if (departamentString.isEmpty() || nrMinOreString.isEmpty() || nrMaxOreString.isEmpty()) {
                    errorHandling.setText("Introdu date in toate campurile!");
                    return;
                }
                int nrMinOre = Integer.parseInt(nrMinOreString);
                int nrMaxOre = Integer.parseInt(nrMaxOreString);
                if (operationName == null) {
                    errorHandling.setText("Selecteaza o operatie!");
                    return;
                }
                if (operationName.equals("Adauga")) {
                    System.out.println("Se va adauga un profesor");
                    boolean ex = false;
                    try {
                        Insert.addProfessor(connection, cnpString, numeString, departamentString, nrMinOre, nrMaxOre, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    } catch (Exception e) {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }
                    if(!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Profesor adaugat cu succes!");
                    }
                    clearAllFields();
                }
            }
            case "Student" -> {
                String anStudiuString = additionalField1.getText();
                String numarOreString = additionalField2.getText();
                if (anStudiuString.isEmpty() || numarOreString.isEmpty()) {
                    errorHandling.setText("Introdu date in toate campurile!");
                    return;
                }
                int anStudiu = Integer.parseInt(anStudiuString);
                int numarOre = Integer.parseInt(numarOreString);
                if (operationName == null) {
                    errorHandling.setText("Selecteaza o operatie!");
                    return;
                }
                if (operationName.equals("Adauga")) {
                    System.out.println("Se va adauga un student");
                    boolean ex = false;
                    try {
                        Insert.addStudent(connection, cnpString, numeString, anStudiu, numarOre, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    } catch (Exception e) {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }
                    if(!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Student adaugat cu succes!");
                    }
                    clearAllFields();
                }
            }
        }
    }


    public void onSelectTable()
    {
        String tableName = selectTableComboBox.getValue();
        errorHandling.setText("");
        clearAllFields();
        if(tableName.equals("Profesor"))
        {
            additionalField1.setVisible(true);
            additionalField1.setPromptText("Departament");
            additionalField1.setText("");
            additionalLabel1.setVisible(true);
            additionalLabel1.setText("Departament");

            additionalField2.setVisible(true);
            additionalField2.setPromptText("Numar minim ore");
            additionalField2.setText("");
            additionalLabel2.setVisible(true);
            additionalLabel2.setText("Numar minim ore");

            additionalField3.setVisible(true);
            additionalField3.setPromptText("Numar minim ore");
            additionalField3.setText("");
            additionalLabel3.setVisible(true);
            additionalLabel3.setText("Numar minim ore");
        }
        else if(tableName.equals("Student"))
        {
            additionalField1.setVisible(true);
            additionalField1.setPromptText("An studii");
            additionalField1.setText("");
            additionalLabel1.setVisible(true);
            additionalLabel1.setText("An studiu");

            additionalField2.setVisible(true);
            additionalField2.setPromptText("Numar ore");
            additionalField2.setText("");
            additionalLabel2.setVisible(true);
            additionalLabel2.setText("Numar ore");

            additionalField3.setVisible(false);
            additionalField3.setText("");
            additionalLabel3.setVisible(false);
            additionalLabel3.setText("");
        }
        else
        {
            makeAdditionalFieldsInvisible();
        }
    }

    private void makeAdditionalFieldsInvisible() {
        additionalField1.setVisible(false);
        additionalField1.setText("");
        additionalField2.setVisible(false);
        additionalField2.setText("");
        additionalField3.setVisible(false);
        additionalField3.setText("");

        additionalLabel1.setVisible(false);
        additionalLabel1.setText("");
        additionalLabel2.setVisible(false);
        additionalLabel2.setText("");
        additionalLabel3.setVisible(false);
        additionalLabel3.setText("");
    }

    private void clearAllFields() {
        nameField.clear();
        cnpField.clear();
        adresaField.clear();
        emailField.clear();
        nrTelefonField.clear();
        usernameField.clear();
        parolaField.clear();
        additionalField1.clear();
        additionalField2.clear();
        additionalField3.clear();
    }

    private boolean oneIsEmpty(String numeString, String cnpString, String adresaString, String emailString, String nrTelefonString, String usernameString, String parolaString) {
        return numeString.isEmpty() || cnpString.isEmpty() || adresaString.isEmpty() || emailString.isEmpty() || nrTelefonString.isEmpty() || usernameString.isEmpty() || parolaString.isEmpty();
    }
}
