package com.example.controllers;

import com.example.ActivitateProfesor;
import com.example.platforma.Main;
import com.example.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class ManageActivitateController {
    @FXML
    private Button operationButton;
    @FXML
    private Button findUsernameButton;
    @FXML
    private Button findActivityButton;
    @FXML
    private Button backButton;
    @FXML
    private Label foundUser;
    @FXML
    private Label foundActivity;
    @FXML
    private TextField findActivityField;
    @FXML
    private TextField descriptionActivitateField;
    @FXML
    private ComboBox<String> selectOperationComboBox;
    @FXML
    private ComboBox<String> selectActivitateComboBox;
    @FXML
    private TextField usernameProfesorField;
    @FXML
    private TextField maxStudentField;
    @FXML
    private TextField newMaxStudentField;
    @FXML
    private Label errorHandling;
    @FXML
    private ComboBox<String> selectNewActivitateComboBox;
    @FXML
    private Label selectNewActivitateLabel;
    @FXML
    private TextField newDescriptionActivitateField;
    @FXML
    private Label newDescriptionLabel;
    @FXML
    private Label newMaxStudentLabel;
    @FXML
    private TableView<ActivitateProfesor> tableActivitati;
    @FXML
    private TableView<ActivitateProfesor> tableAllActivities;
    @FXML
    private TableColumn<ActivitateProfesor, String> numeColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> usernameColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> tipActivitateColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> descriereColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> nrMaximStudentiColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> usernameAllColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> tipActivitateAllColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> descriereAllColumn;
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

        ObservableList<String> activitiesList = FXCollections.observableArrayList();
        String[] activities = {"Curs", "Seminar", "Laborator", "Colocviu", "Examen", "Restanta"};
        activitiesList.addAll(Arrays.asList(activities).subList(0, 6));

        ObservableList<String> operationsList = FXCollections.observableArrayList();
        String[] operations = {"Adauga", "Modifica", "Sterge"};
        operationsList.addAll(Arrays.asList(operations).subList(0, 3));

        selectActivitateComboBox.setItems(activitiesList);
        selectNewActivitateComboBox.setItems(activitiesList);
        selectOperationComboBox.setItems(operationsList);

        setNewFieldsVisible(false);
        setActivitateTableVisible(false);
        setAllActivitesTableVisible(false);
    }

    /***
     * Inapoi la gestionareUtilizatori
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("gestionareUtilizatori.fxml", username, tableName, 1024, 768);
    }

    public void onSelectOperation()
    {
        setNewFieldsVisible(false);
        String operationName = selectOperationComboBox.getValue();

        if(operationName == null)
        {
            return;
        }

        operationButton.setText(operationName);

        if(operationName.equals("Modifica"))
        {
            setNewFieldsVisible(true);
        }

        clearAllFields();
    }

    public void onSelectActivitate()
    {
        setAllActivitesTableVisible(true);
        generateAllActivitesTable();

        try
        {
            Connection connection = Connect.getConnection();
            String selectActivitateString = selectActivitateComboBox.getValue();
            if(connection == null || selectActivitateString == null)
            {
                return;
            }

            String[][] tableInfo = Query.getAllActivitiesTableFromSelect(connection, selectActivitateString);

            populateAllActivitiesTable(tableInfo);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onFindActivity()
    {
        setAllActivitesTableVisible(true);
        generateAllActivitesTable();

        try
        {
            Connection connection = Connect.getConnection();
            String findActivityString = findActivityField.getText();
            if(connection == null)
            {
                return;
            }

            if(findActivityString.isEmpty())
            {
                foundActivity.setText("Introdu o activitate!");
                return;
            }

            if(!Query.existsActivity(connection, findActivityString))
            {
                foundActivity.setText("Introdu o activitate valida!");
                return;
            }

            String[][] tableInfo = Query.getAllActivitiesTableFromSearch(connection, findActivityString);

            populateAllActivitiesTable(tableInfo);
            foundActivity.setText("");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Logica din spatele butonului de operatii
     */

    public void onOperationButton()
    {
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        String usernameString = usernameProfesorField.getText();
        String activitateString = selectActivitateComboBox.getValue();
        String descriptionString = descriptionActivitateField.getText();
        String maxStudentString = maxStudentField.getText();
        String operationName = selectOperationComboBox.getValue();

        if(operationName == null)
        {
            errorHandling.setText("Selecteaza o operatie!");
            return;
        }

        if(usernameString.isEmpty())
        {
            errorHandling.setText("Introdu un utilizator!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();

            if(connection == null)
            {
                return;
            }

            if(activitateString == null || descriptionString.isEmpty() || maxStudentString.isEmpty())
            {
                errorHandling.setText("Introdu date in toate campurile!");
                clearAllFields();
                return;
            }

            boolean existsProfesor = Query.userExistsInTable(connection, usernameString, "profesor");

            if(!existsProfesor)
            {
                errorHandling.setText("Introdu un utilizator valid!");
                clearAllFields();
                return;
            }

            switch (operationName)
            {
                case "Adauga" ->
                {
                    Insert.addActivitateProfesor(connection, usernameString, activitateString, descriptionString, Integer.parseInt(maxStudentString));

                    errorHandling.setTextFill(Color.rgb(0, 255, 0));
                    errorHandling.setText("Adaugare efectuata cu succes!");
                    clearAllFields();
                }

                case "Modifica" ->
                {
                    String newActivitateString = selectNewActivitateComboBox.getValue();
                    String newDescriptionString = newDescriptionActivitateField.getText();
                    String newMaxStudentString = newMaxStudentField.getText();

                    if(newActivitateString == null && newDescriptionString.isEmpty() && newMaxStudentString.isEmpty())
                    {
                        errorHandling.setText("Introdu date in cel putin un camp!");
                        clearAllFields();
                        return;
                    }

                    List<String> newEntriesList = getNewEntriesList(newActivitateString, newDescriptionString, newMaxStudentString);
                    String[] columns = {"tipActivitate", "descriere", "nrMaximStudenti"};
                    int rowsAffected = 0;

                    for(int i = 0; i < newEntriesList.size(); ++i)
                    {
                        String newValue = newEntriesList.get(i);
                        String columnName = columns[i];

                        if(!newValue.equals("null"))
                        {
                            int updated = Update.updateEntryInActivitateProfesor(connection, usernameString, columnName, activitateString, descriptionString, newValue);
                            if(updated > 0)
                            {
                                ++rowsAffected;
                            }
                        }
                    }

                    if(rowsAffected == 0)
                    {
                        errorHandling.setText("Introdu un utilizator valid!");
                        clearAllFields();
                        return;
                    }

                    errorHandling.setTextFill(Color.rgb(0, 255, 0));
                    errorHandling.setText("Modificare efectuata cu succes!");

                    clearAllFields();
                }

                case "Sterge" ->
                {
                    int affectedRows = Delete.deleteActivitateProfesor(connection, usernameString, activitateString, descriptionString);

                    if(affectedRows == 0)
                    {
                        errorHandling.setText("Introdu un utilizator valid!");
                        clearAllFields();
                        return;
                    }

                    errorHandling.setTextFill(Color.rgb(0, 255, 0));
                    errorHandling.setText("Stergere efectuata cu succes!");

                    clearAllFields();
                }
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            clearAllFields();
            e.printStackTrace();
        }
    }

    /***
     * Afiseaza detaliile utilizatorului cautat intr-o tabela
     */

    public void onSearchUsername()
    {
        generateTableActivitati();
        Connection connection = Connect.getConnection();
        String findUsernameString = usernameProfesorField.getText();
        String[] activitateInfo = Query.getAllInfoOnUser(connection, "activitateprofesor", findUsernameString);

        if(activitateInfo == null)
        {
            foundUser.setText("Utilizatorul cautat nu are nicio activitate!");
            clearAllFields();
            return;
        }

        setActivitateTableVisible(true);
        ObservableList<ActivitateProfesor> listActivitate = FXCollections.observableArrayList();
        foundUser.setText("");
        listActivitate.add(rowToActivitateProfesor(activitateInfo));
        tableActivitati.setItems(listActivitate);

        clearAllFields();
    }

    /***
     * Converteste o intrare din tabela de activitati intr-o clasa
     * @param row informatia sub forma de Array de String-uri
     * @return o noua activitate
     */

    private ActivitateProfesor rowToActivitateProfesor(String[] row)
    {
        int maxStudent = Integer.parseInt(row[4]);
        return new ActivitateProfesor(row[0], row[1], row[2], row[3], maxStudent);
    }

    /***
     * Genereaza celulele tabelului de activitati
     */

    private void generateTableActivitati()
    {
        numeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "nume")));
        usernameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "username")));
        tipActivitateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "tipActivitate")));
        descriereColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(getPropertyValue(cellData.getValue(), "descriere")));
        nrMaximStudentiColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "nrMaximStudenti")));

        removeEllipses(numeColumn);
        removeEllipses(usernameColumn);
        removeEllipses(tipActivitateColumn);
        removeEllipses(descriereColumn);
        removeEllipses(nrMaximStudentiColumn);
    }

    public void generateAllActivitesTable()
    {
        usernameAllColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "username")));
        tipActivitateAllColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "tipActivitate")));
        descriereAllColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getPropertyValue(cellData.getValue(), "descriere")));
        removeEllipses(usernameAllColumn);
        removeEllipses(tipActivitateAllColumn);
        removeEllipses(descriereAllColumn);
    }

    /***
     * Face ca textul sa apara fara ...
     * @param column coloana
     */

    private void removeEllipses(TableColumn<ActivitateProfesor, String> column)
    {
        column.setMinWidth(200);
    }

    /***
     * Genereaza o lista cu intrarile noi
     * @param newActivitateString noua activitate
     * @param newDescriptionString noua descriere a activitatii
     * @param newMaxStudentString nou nr maxim de studenti
     * @return lista de intrari noi
     */

    private List<String> getNewEntriesList(String newActivitateString, String newDescriptionString, String newMaxStudentString)
    {
        List<String> newEntriesList = new ArrayList<>();
        if(newActivitateString != null)
        {
            newEntriesList.add(newActivitateString);
        }
        else
        {
            newEntriesList.add("null");
        }

        if(!newDescriptionString.isEmpty())
        {
            newEntriesList.add(newDescriptionString);
        }
        else
        {
            newEntriesList.add("null");
        }

        if(!newMaxStudentString.isEmpty())
        {
            newEntriesList.add(newMaxStudentString);
        }
        else
        {
            newEntriesList.add("null");
        }

        return newEntriesList;
    }

    private String getPropertyValue(ActivitateProfesor activitateProfesor, String propertyName)
    {
        return switch (propertyName)
        {
            case "nume" -> activitateProfesor.getNume();
            case "username" -> activitateProfesor.getUsername();
            case "tipActivitate" -> activitateProfesor.getTipActivitate();
            case "descriere" -> activitateProfesor.getDescriere();
            case "nrMaximStudenti" -> String.valueOf(activitateProfesor.getNrMaximStudenti());
            default -> null;
        };
    }

    public void populateAllActivitiesTable(String[][] info)
    {
        ObservableList<ActivitateProfesor> list = FXCollections.observableArrayList();
        if(info == null)
        {
            return;
        }

        for(String[] row: info)
        {
            ActivitateProfesor activitateProfesor = rowToActivitateProfesor(row);
            list.add(activitateProfesor);
        }

        tableAllActivities.setItems(list);
    }

    private void clearAllFields()
    {
        usernameProfesorField.clear();
        selectActivitateComboBox.setValue(null);
        selectNewActivitateComboBox.setValue(null);
        descriptionActivitateField.clear();
        newDescriptionActivitateField.clear();
        maxStudentField.clear();
        newMaxStudentField.clear();
    }

    private void setNewFieldsVisible(boolean expr)
    {
        selectNewActivitateComboBox.setVisible(expr);
        selectNewActivitateLabel.setVisible(expr);
        newDescriptionActivitateField.setVisible(expr);
        newDescriptionLabel.setVisible(expr);
        newMaxStudentField.setVisible(expr);
        newMaxStudentLabel.setVisible(expr);
    }

    private void setActivitateTableVisible(boolean expr)
    {
        tableActivitati.getItems().clear();
        tableActivitati.setVisible(expr);
        numeColumn.setVisible(expr);
        usernameColumn.setVisible(expr);
        tipActivitateColumn.setVisible(expr);
        descriereColumn.setVisible(expr);
        nrMaximStudentiColumn.setVisible(expr);
    }

    private void setAllActivitesTableVisible(boolean expr)
    {
        tableAllActivities.getItems().clear();
        tableAllActivities.setVisible(expr);
        tipActivitateAllColumn.setVisible(expr);
        descriereAllColumn.setVisible(expr);
    }
}
