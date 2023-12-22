package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Insert;
import com.example.sql.Update;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Label modifyUserLabel;
    @FXML
    private TextField modifyUserField;
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
            tablesList.addAll(Arrays.asList(tables).subList(1, 4));
        }

        selectTableComboBox.setItems(tablesList);
        selectOperationComboBox.setItems(operationList);
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        modifyUserLabel.setVisible(false);
        modifyUserField.setVisible(false);

        modifyUserField.setText("");
        modifyUserLabel.setText("");

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

        modifyUserLabel.setVisible(false);
        modifyUserField.setVisible(false);
        modifyUserField.setText("");
        modifyUserLabel.setText("");
        setUsernameAndPasswordEditable(true);

        if(buttonText.equals("Modifica") || buttonText.equals("Sterge"))
        {
            modifyUserLabel.setVisible(true);
            modifyUserField.setVisible(true);
            modifyUserField.setText("");
            modifyUserLabel.setText("Introdu un nume de utilizator");

            setUsernameAndPasswordEditable(false);
        }

        clearAllFields();
    }

    public void onOperationButton()
    {
        String tableName = selectTableComboBox.getValue();
        String operationName = selectOperationComboBox.getValue();
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        if (tableName == null)
        {
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
        List<String> entries = Arrays.asList(cnpString, numeString, adresaString, nrTelefonString, emailString, usernameString, parolaString);

        if (operationName == null)
        {
            errorHandling.setText("Selecteaza o operatie!");
            return;
        }

        if (operationName.equals("Adauga"))
        {
            switch (tableName)
            {
                case "Superadministrator" ->
                {
                    if (oneIsEmpty(numeString, cnpString, adresaString, emailString, nrTelefonString, usernameString, parolaString))
                    {
                        errorHandling.setText("Introdu date in toate campurile!");
                        return;
                    }

                    boolean ex = false;

                    try
                    {
                        Insert.addSuperAdmin(connection, cnpString, numeString, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    }
                    catch(Exception e)
                    {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }

                    if (!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Superadministrator adaugat cu succes!");
                    }

                    clearAllFields();
                }

                case "Administrator" ->
                {
                    if (oneIsEmpty(numeString, cnpString, adresaString, emailString, nrTelefonString, usernameString, parolaString))
                    {
                        errorHandling.setText("Introdu date in toate campurile!");
                        return;
                    }

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

                    if (!ex) {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Administrator adaugat cu succes!");
                    }
                    clearAllFields();
                }

                case "Profesor" ->
                {
                    String departamentString = additionalField1.getText();
                    String nrMinOreString = additionalField2.getText();
                    String nrMaxOreString = additionalField3.getText();

                    if (departamentString.isEmpty() || nrMinOreString.isEmpty() || nrMaxOreString.isEmpty())
                    {
                        errorHandling.setText("Introdu date in toate campurile!");
                        return;
                    }

                    int nrMinOre = Integer.parseInt(nrMinOreString);
                    int nrMaxOre = Integer.parseInt(nrMaxOreString);

                    boolean ex = false;

                    try
                    {
                        Insert.addProfessor(connection, cnpString, numeString, departamentString, nrMinOre, nrMaxOre, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    }
                    catch(Exception e)
                    {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }

                    if (!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Profesor adaugat cu succes!");
                    }
                    clearAllFields();
                }

                case "Student" ->
                {
                    String anStudiuString = additionalField1.getText();
                    String numarOreString = additionalField2.getText();

                    if (anStudiuString.isEmpty() || numarOreString.isEmpty())
                    {
                        errorHandling.setText("Introdu date in toate campurile!");
                        return;
                    }

                    int anStudiu = Integer.parseInt(anStudiuString);
                    int numarOre = Integer.parseInt(numarOreString);

                    boolean ex = false;

                    try
                    {
                        Insert.addStudent(connection, cnpString, numeString, anStudiu, numarOre, adresaString, nrTelefonString, emailString, usernameString, parolaString);
                    }
                    catch(Exception e)
                    {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }

                    if (!ex)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Student adaugat cu succes!");
                    }

                    clearAllFields();
                }
            }
        }
        else if(operationName.equals("Modifica"))
        {
            String modifyUserString = modifyUserField.getText();

            List<String> columnsList = getColumnsList(tableName);
            List<String> validEntries = new ArrayList<>();

            int emptyCount = 0;
            for(String value: entries)
            {
                if(!value.isEmpty())
                {
                    validEntries.add(value);
                }
                else
                {
                    validEntries.add("null");
                    ++emptyCount;
                }
            }

            if(emptyCount == 7 || emptyCount == 9 || emptyCount == 10)
            {
                errorHandling.setText("Introdu cel putin o data valida!");
                return;
            }

            boolean ex = false;
            int rowsAffected = 0;

            for(int i = 0; i < validEntries.size(); ++i)
            {
                if(!validEntries.get(i).equals("null"))
                {
                    String columnName = columnsList.get(i);
                    if(columnName.equals("username") || columnName.equals("parola"))
                    {
                        continue;
                    }

                    String newValue = validEntries.get(i);
                    try
                    {
                        int updated = 0;
                        if(columnName.equals("nrMinOre") || columnName.equals("nrMaxOre") || columnName.equals("numarOre") || columnName.equals("anStudiu"))
                        {
                            updated = Update.updateEntry(connection, modifyUserString, tableName, columnName, Integer.parseInt(newValue));
                        }
                        else
                        {
                            updated = Update.updateEntry(connection, modifyUserString, tableName, columnName, newValue);
                        }

                        if(updated > 0)
                        {
                            ++rowsAffected;
                        }
                    }

                    catch (Exception e)
                    {
                        errorHandling.setText(e.getMessage());
                        ex = true;
                        e.printStackTrace();
                    }
                }
            }

            if(rowsAffected == 0)
            {
                errorHandling.setText("Introdu un utilizator valid!");
                clearAllFields();
                return;
            }

            if(!ex)
            {
                errorHandling.setTextFill(Color.rgb(0, 255, 0));
                errorHandling.setText("Modificare efectuata cu succes!");
            }

            clearAllFields();
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
            additionalField3.setPromptText("Numar maxim ore");
            additionalField3.setText("");
            additionalLabel3.setVisible(true);
            additionalLabel3.setText("Numar maxim ore");
        }
        else if(tableName.equals("Student"))
        {
            additionalField1.setVisible(true);
            additionalField1.setPromptText("An studiu");
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

    private void setUsernameAndPasswordEditable(boolean expr)
    {
        usernameField.setEditable(expr);
        parolaField.setEditable(expr);
    }

    private void makeAdditionalFieldsInvisible()
    {
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

    private List<String> getColumnsList(String tableName)
    {
        List<String> columnsList = new ArrayList<>();

        if(tableName.equals("Superadministrator") || tableName.equals("Administrator"))
        {
            String[] columns = {"cnp", "nume", "adresa", "nrTelefon", "email", "username", "parola"};
            columnsList = Arrays.asList(columns);
        }
        else if(tableName.equals("Profesor"))
        {
            String[] columns = {"cnp", "nume", "departament", "nrMinOre", "nrMaxOre", "adresa", "nrTelefon", "email", "username", "parola"};
            columnsList = Arrays.asList(columns);
        }
        else if(tableName.equals("Student"))
        {
            String[] columns = {"cnp", "nume", "anStudiu", "numarOre", "adresa", "nrTelefon", "email", "username", "parola"};
            columnsList = Arrays.asList(columns);
        }
        return columnsList;
    }

    private void clearAllFields()
    {
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
        modifyUserField.clear();
    }

    private boolean oneIsEmpty(String numeString, String cnpString, String adresaString, String emailString, String nrTelefonString, String usernameString, String parolaString)
    {
        return numeString.isEmpty() || cnpString.isEmpty() || adresaString.isEmpty() || emailString.isEmpty() || nrTelefonString.isEmpty() || usernameString.isEmpty() || parolaString.isEmpty();
    }
}
