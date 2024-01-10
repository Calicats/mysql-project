package com.example.controllers;

import com.example.ActivitateProfesor;
import com.example.ParticipantActivitate;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Insert;
import com.example.sql.Query;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Connection;

public class InscriereActivitateController {

    @FXML
    private TextField activityField;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<ActivitateProfesor, String> descriereColumn;

    @FXML
    private TableColumn<ActivitateProfesor, String> descriereActivitateParticipant;

    @FXML
    private TableColumn<ActivitateProfesor, String> nrMaximStudentiColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> nrStudenti;

    @FXML
    private TableColumn<ActivitateProfesor, String> numeColumn;

    @FXML
    private Button searchActivityButton;

    @FXML
    private Label foundUser;

    @FXML
    private Label errorHandling;

    @FXML
    private Label foundActivity;

    @FXML
    private Button searchProfesorButton;

    @FXML
    private Button enrollActivityButton;

    @FXML
    private TableView<ActivitateProfesor> tableActivitati;

    @FXML
    private TableView<ParticipantActivitate> tableParticipanti;

    @FXML
    private TableColumn<ActivitateProfesor, String> tipActivitateColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> tipActivitateParticipant;

    @FXML
    private TableColumn<ActivitateProfesor, String> usernameColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TableColumn<ParticipantActivitate, String> usernameProfesor;

    private String studentLogat;
    private String tableName;

    public void initUser(String studentLogat, String tableName)
    {
        this.studentLogat = studentLogat;
        this.tableName = tableName;

        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");

        generateTableActivitati();
        generateTableParticipanti();

        populateTableActivitati();
        populateTableParticipanti();
    }

    @FXML
    public void onBackButton() throws IOException
    {
        Main.main.changeScene("panouStudent.fxml", studentLogat, tableName, 1024, 768);
    }

    @FXML
    public void onSearchActivity()
    {
        clearErrors();

        String activityString = activityField.getText();

        if(activityString.isEmpty())
        {
            foundActivity.setText("Introdu o activitate!");
            clearAllFields();
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            if(!Query.existsActivity(connection, activityString))
            {
                foundActivity.setText("Introdu o activitate valida!");
                clearAllFields();
                return;
            }

            String[][] tableInfo = Query.getAllActivitiesTableFromSearch(connection, activityString);
            ObservableList<ActivitateProfesor> list = FXCollections.observableArrayList();

            for(String[] row: tableInfo)
            {
                list.add(rowToActivitateProfesor(row));
            }

            tableActivitati.setItems(list);
            foundActivity.setText("");
        }
        catch(Exception e)
        {
            errorHandling.setTextFill(Color.RED);
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onEnrollActivity()
    {
        clearErrors();

        String usernameProfesorString = usernameField.getText();
        String activityString = activityField.getText();
        if(usernameProfesorString.isEmpty() || activityString.isEmpty())
        {
            errorHandling.setText("Introdu date in toate campurile!");
            clearAllFields();
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            if(!Query.userExistsInTable(connection, usernameProfesorString, "profesor"))
            {
                errorHandling.setText("Introdu un profesor valid!");
                clearAllFields();
                return;
            }

            if(!Query.existsActivitySensitive(connection, activityString))
            {
                errorHandling.setText("Introdu o activitate valida!");
                clearAllFields();
                return;
            }

            if(Query.existsStudentInActivity(connection, studentLogat))
            {
                errorHandling.setText("Esti deja inscris la activitatea introdusa!");
                clearAllFields();
                return;
            }

            Insert.addParticipantActivitate(connection, usernameProfesorString, studentLogat);
            errorHandling.setTextFill(Color.rgb(0, 255, 0));
            errorHandling.setText("Inscriere efectuata cu succes!");
            clearAllFields();
        }
        catch(Exception e)
        {
            errorHandling.setTextFill(Color.RED);
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onSearchProfesor()
    {
        clearErrors();

        String profesorString = usernameField.getText();
        if(profesorString.isEmpty())
        {
            foundUser.setText("Introdu un profesor!");
            clearAllFields();
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            if(!Query.userExistsInTable(connection, profesorString, "profesor"))
            {
                foundUser.setText("Introdu un utilizator valid!");
                clearAllFields();
                return;
            }

            String[][] activitateInfo = Query.getUsersFromActivityPanel(connection, profesorString);
            if(activitateInfo == null)
            {
                foundUser.setText("Utilizatorul cautat nu are nicio activitate!");
                clearAllFields();
                return;
            }

            ObservableList<ActivitateProfesor> listActivitate = FXCollections.observableArrayList();
            for(String[] row: activitateInfo)
            {
                listActivitate.add(rowToActivitateProfesor(row));
            }
            tableActivitati.setItems(listActivitate);
            clearAllFields();
            clearErrors();
        }
        catch(Exception e)
        {
            errorHandling.setTextFill(Color.RED);
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

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

    private void generateTableParticipanti()
    {
        usernameProfesor.setCellValueFactory(new PropertyValueFactory<>("usernameProfesor"));
        tipActivitateParticipant.setCellValueFactory(new PropertyValueFactory<>("tipActivitate"));
        descriereActivitateParticipant.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrStudenti.setCellValueFactory(new PropertyValueFactory<>("nrStudenti"));

        removeEllipses(usernameProfesor);
        removeEllipses(tipActivitateParticipant);
        removeEllipses(descriereActivitateParticipant);
        removeEllipses(nrStudenti);
    }

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
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

    private void clearAllFields()
    {
        usernameField.clear();
        activityField.clear();
    }

    private void clearErrors()
    {
        foundUser.setText("");
        foundActivity.setText("");
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");
    }

    private void populateTableActivitati()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        String[][] activitateInfo = Query.getActivitateTableInfo(connection);

        if(activitateInfo == null)
        {
            errorHandling.setText("activitateprofesor is null!");
            return;
        }

        ObservableList<ActivitateProfesor> listActivitate = FXCollections.observableArrayList();
        for(String[] row: activitateInfo)
        {
            listActivitate.add(rowToActivitateProfesor(row));
        }
        tableActivitati.setItems(listActivitate);
    }

    private void populateTableParticipanti()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        String[][] participantActivitateInfo = Query.getParticipantiTableInfo(connection);
        if(participantActivitateInfo == null)
        {
            errorHandling.setText("participantactivitate is null!");
            return;
        }

        ObservableList<ParticipantActivitate> listParticipantActivitate = FXCollections.observableArrayList();
        for(String[] row: participantActivitateInfo)
        {
            listParticipantActivitate.add(rowToParticipantActivitate(row));
        }
        tableParticipanti.setItems(listParticipantActivitate);
    }

    private ActivitateProfesor rowToActivitateProfesor(String[] row)
    {
        int maxStudent = Integer.parseInt(row[4]);
        return new ActivitateProfesor(row[0], row[1], row[2], row[3], maxStudent);
    }

    private ParticipantActivitate rowToParticipantActivitate(String[] row)
    {
        int nrStudenti = Integer.parseInt(row[3]);
        return new ParticipantActivitate(row[0], row[1], row[2], nrStudenti);
    }
}
