package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Delete;
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
    private Button addActivitateButton;
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

    /***
     * "Constructor", in ghilimele
     * @param username numele utilizatorului logat
     * @param tableName tabela de care apartine utilizatorul
     */

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

    /***
     * Intoarcere la panoul administrativ
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("panouAdministrativ.fxml", username, tableName, 1024, 768);
    }

    public void onAddActivitate() throws IOException
    {
        Main main = new Main();
        main.changeScene("gestionareActivitate.fxml", username, tableName, 1024, 768);
    }

    /***
     * Modifica textul butonului pe ce ai selectat in combo box
     */

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
        deleteOperation(false);

        if(buttonText.equals("Modifica") || buttonText.equals("Sterge"))
        {
            if(buttonText.equals("Sterge"))
            {
                // seteaza toate campurile ineditabile, mai putin cel de introducere de utilizator
                deleteOperation(true);
            }

            modifyUserLabel.setVisible(true);
            modifyUserField.setVisible(true);
            modifyUserField.setText("");
            modifyUserLabel.setText("Introdu un nume de utilizator");

            setUsernameAndPasswordEditable(false);
        }

        clearAllFields();
    }

    /***
     * Logica din spatele butonului de operatie (Adauga, Modifica, Stergere)
     * Am ales sa nu poti modifica username-ul si parola
     */

    public void onOperationButton()
    {
        String tableName = selectTableComboBox.getValue();
        String operationName = selectOperationComboBox.getValue();
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        if(tableName == null)
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

        String cnpString = cnpField.getText();
        String numeString = nameField.getText();
        String adresaString = adresaField.getText();
        String emailString = emailField.getText();
        String nrTelefonString = nrTelefonField.getText();
        String usernameString = usernameField.getText();
        String parolaString = parolaField.getText();
        List<String> entries = new ArrayList<>(Arrays.asList(cnpString, numeString, adresaString, nrTelefonString, emailString, usernameString, parolaString));

        if(operationName == null)
        {
            errorHandling.setText("Selecteaza o operatie!");
            return;
        }

        switch(operationName)
        {
            case "Adauga" ->
            {
                if(oneIsEmpty(numeString, cnpString, adresaString, emailString, nrTelefonString, usernameString, parolaString))
                {
                    errorHandling.setText("Introdu date in toate campurile!");
                    clearAllFields();
                    return;
                }

                switch (tableName)
                {
                    case "Superadministrator" ->
                    {
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

                        if(!ex)
                        {
                            errorHandling.setTextFill(Color.rgb(0, 255, 0));
                            errorHandling.setText("Superadministrator adaugat cu succes!");
                        }

                        clearAllFields();
                    }

                    case "Administrator" ->
                    {
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

                    case "Profesor" ->
                    {
                        String departamentString = additionalField1.getText();
                        String nrMinOreString = additionalField2.getText();
                        String nrMaxOreString = additionalField3.getText();

                        if(departamentString.isEmpty() || nrMinOreString.isEmpty() || nrMaxOreString.isEmpty())
                        {
                            errorHandling.setText("Introdu date in toate campurile!");
                            clearAllFields();
                            selectOperationComboBox.setValue(null);
                            return;
                        }

                        int nrMinOre = Integer.parseInt(nrMinOreString);
                        int nrMaxOre = Integer.parseInt(nrMaxOreString);

                        boolean ex = false;

                        try
                        {
                            Insert.addProfesor(connection, cnpString, numeString, departamentString, nrMinOre, nrMaxOre, adresaString, nrTelefonString, emailString, usernameString, parolaString);
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
                            errorHandling.setText("Profesor adaugat cu succes!");
                        }

                        clearAllFields();
                        selectOperationComboBox.setValue(null);
                    }

                    case "Student" ->
                    {
                        String anStudiuString = additionalField1.getText();
                        String numarOreString = additionalField2.getText();

                        if(anStudiuString.isEmpty() || numarOreString.isEmpty())
                        {
                            errorHandling.setText("Introdu date in toate campurile!");
                            clearAllFields();
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

                        if(!ex)
                        {
                            errorHandling.setTextFill(Color.rgb(0, 255, 0));
                            errorHandling.setText("Student adaugat cu succes!");
                        }

                        clearAllFields();
                    }
                }
            }

            case "Modifica" ->
            {
                // modifyUserString = numele utilizatorului ce vrei sa il modifici
                String modifyUserString = modifyUserField.getText();
                // columnList = lista de coloane
                List<String> columnsList = getColumnsList(tableName);
                // validEntries = lista de intrari valide, care nu sunt "null"
                List<String> validEntries = new ArrayList<>();

                if(tableName.equals("Profesor"))
                {
                    // adauga campurile departament, nrMinOre, nrMaxOre
                    entries.add(2, additionalField1.getText());
                    entries.add(3, additionalField2.getText());
                    entries.add(4, additionalField3.getText());
                }
                else if(tableName.equals("Student"))
                {
                    // adauga campurile anStudiu, numarOre
                    entries.add(2, additionalField1.getText());
                    entries.add(3, additionalField2.getText());
                }

                int entriesCount = 0;

                for (String value : entries)
                {
                    if(!value.isEmpty())
                    {
                        validEntries.add(value);
                        ++entriesCount;
                    }
                    else
                    {
                        validEntries.add("null");
                    }
                }

                // daca nu avem intrari, afisam un mesaj corespunzator
                if(entriesCount == 0)
                {
                    errorHandling.setText("Introdu cel putin o data valida!");
                    clearAllFields();
                    return;
                }

                boolean ex = false;
                int rowsAffected = 0;

                for (int i = 0; i < validEntries.size(); ++i)
                {
                    if (!validEntries.get(i).equals("null"))
                    {
                        String columnName = columnsList.get(i);
                        String newValue = validEntries.get(i);

                        if(columnName.equals("username") || columnName.equals("parola"))
                        {
                            // ignor coloanele username si parola
                            continue;
                        }

                        try
                        {
                            int updated = 0;
                            if(columnName.equals("nrMinOre") || columnName.equals("nrMaxOre") || columnName.equals("numarOre") || columnName.equals("anStudiu"))
                            {
                                // adaug un int
                                updated = Update.updateEntry(connection, modifyUserString, tableName, columnName, Integer.parseInt(newValue));
                            }
                            else
                            {
                                // adaug un string
                                updated = Update.updateEntry(connection, modifyUserString, tableName, columnName, newValue);
                            }

                            if (updated > 0)
                            {
                                ++rowsAffected;
                            }
                        }
                        catch(Exception e)
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

            case "Sterge" ->
            {
                // modifyUserString = numele utilizatorului ce vrei sa il stergi
                String modifyUserString = modifyUserField.getText();

                if(modifyUserString.equals(this.username))
                {
                    errorHandling.setText("Nu te poti sterge pe tine insuti!");
                    clearAllFields();
                    selectOperationComboBox.setValue(null);
                    return;
                }

                try
                {
                    int affectedRows = Delete.deleteUser(connection, modifyUserString, tableName);

                    if(affectedRows > 0)
                    {
                        errorHandling.setTextFill(Color.rgb(0, 255, 0));
                        errorHandling.setText("Stergere efectuata cu succes!");
                    }
                    else
                    {
                        errorHandling.setText("Introdu un utilizator valid!");
                    }

                    clearAllFields();
                }
                catch (Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    clearAllFields();
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * Metoda care afiseaza campurile necesare introducerii datelor
     */

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

    /***
     * Necesar pentru modificare/stergere
     * @param expr true/false
     */

    private void setUsernameAndPasswordEditable(boolean expr)
    {
        usernameField.setEditable(expr);
        parolaField.setEditable(expr);
    }

    /***
     * Campurile din profesor si student le seteaza invisibile
     */

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

    /***
     * Functie care construieste lista de coloane a tabelului selectat in combo box
     * @param tableName numele tabelului
     * @return lista de coloane a tabelului
     */

    private List<String> getColumnsList(String tableName)
    {
        List<String> columnsList = new ArrayList<>();

        switch(tableName)
        {
            case "Superadministrator", "Administrator" ->
            {
                String[] columns = {"cnp", "nume", "adresa", "nrTelefon", "email", "username", "parola"};
                columnsList = Arrays.asList(columns);
            }
            case "Profesor" ->
            {
                String[] columns = {"cnp", "nume", "departament", "nrMinOre", "nrMaxOre", "adresa", "nrTelefon", "email", "username", "parola"};
                columnsList = Arrays.asList(columns);
            }
            case "Student" ->
            {
                String[] columns = {"cnp", "nume", "anStudiu", "numarOre", "adresa", "nrTelefon", "email", "username", "parola"};
                columnsList = Arrays.asList(columns);
            }
        }
        return columnsList;
    }

    /***
     * Sterge tot ce s-a introdus in campuri
     */

    private void clearAllFields()
    {
        cnpField.clear();
        nameField.clear();
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

    /***
     * Seteaza toate campurile ineditable, mai putin cel de introducere de utilizator
     * @param expr = true/false
     */

    private void deleteOperation(boolean expr)
    {
        cnpField.setEditable(!expr);
        nameField.setEditable(!expr);
        adresaField.setEditable(!expr);
        emailField.setEditable(!expr);
        nrTelefonField.setEditable(!expr);
        usernameField.setEditable(!expr);
        parolaField.setEditable(!expr);
        additionalField1.setEditable(!expr);
        additionalField2.setEditable(!expr);
        additionalField3.setEditable(!expr);
    }

    private boolean oneIsEmpty(String numeString, String cnpString, String adresaString, String emailString, String nrTelefonString, String usernameString, String parolaString)
    {
        return numeString.isEmpty() || cnpString.isEmpty() || adresaString.isEmpty() || emailString.isEmpty() || nrTelefonString.isEmpty() || usernameString.isEmpty() || parolaString.isEmpty();
    }
}
