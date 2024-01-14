package com.example.controllers;

import com.example.Activitate;
import com.example.ActivitateProfesor;
import com.example.ParticipantActivitate;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Insert;
import com.example.sql.Query;
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
    private TextField courseNameField;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Activitate, String> descriereColumn;

    @FXML
    private Button enrollActivityButton;

    @FXML
    private Label errorHandling;

    @FXML
    private Label foundActivity;

    @FXML
    private Label foundUser;

    @FXML
    private TableColumn<Activitate, String> idColumn;

    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumn;

    @FXML
    private TableColumn<Activitate, String> numeColumn;

    @FXML
    private TableColumn<Activitate, String> procentColumn;

    @FXML
    private Button searchActivityButton;

    @FXML
    private Button searchProfesorButton;

    @FXML
    private TableView<Activitate> tableActivitati;

    @FXML
    private TableColumn<Activitate, String> tipColumn;

    @FXML
    private TableColumn<Activitate, String> usernameColumn;

    @FXML
    private TableView<ParticipantActivitate> tableParticipant;

    @FXML
    private TableColumn<ParticipantActivitate, String> usernameProfesorColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> numeCursParticipantColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> tipParticipantColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> nrMaximStudentiParticipantColumn;

    @FXML
    private TableColumn<ParticipantActivitate, String> nrParticipantiColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<String> selectActivityType;

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

        String[] activities = {"Curs", "Seminar", "Laborator", "Examen", "Restanta"};
        ObservableList<String> activitiesList = FXCollections.observableArrayList(activities);
        selectActivityType.setItems(activitiesList);
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

        String cursString = courseNameField.getText();
        String activityType = selectActivityType.getValue();

        if(cursString.isEmpty())
        {
            foundActivity.setText("Introdu un curs!");
            clearAllFields();
            return;
        }

        if(activityType == null)
        {
            foundActivity.setText("Alege o activitate!");
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

            if(!Query.existsActivity(connection, cursString, activityType))
            {
                foundActivity.setText("Introdu o activitate valida!");
                clearAllFields();
                return;
            }

            String[][] tableInfo = Query.getActivitateTableInfoOnActivity(connection, cursString);
            ObservableList<Activitate> list = FXCollections.observableArrayList();

            for(String[] row: tableInfo)
            {
                list.add(rowToActivitate(row));
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
        String cursString = courseNameField.getText();
        String activityType = selectActivityType.getValue();

        if(activityType == null)
        {
            foundActivity.setText("Alege o activitate!");
            clearAllFields();
            return;
        }

        if(usernameProfesorString.isEmpty() || cursString.isEmpty())
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

            if(!Query.existsActivitySensitive(connection, cursString, activityType))
            {
                errorHandling.setText("Introdu o activitate valida!");
                clearAllFields();
                return;
            }

            if(!Query.existsProfesorInActivity(connection, usernameProfesorString, cursString, activityType))
            {
                errorHandling.setText("Profesorul nu este in activitatea respectiva!");
                clearAllFields();
                return;
            }

            if(Query.existsStudentInParticipant(connection, usernameProfesorString, studentLogat, activityType))
            {
                errorHandling.setText("Esti deja inscris la activitatea introdusa!");
                clearAllFields();
                return;
            }

            Insert.addParticipantActivitate(connection, usernameProfesorString, studentLogat, activityType);
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

            String[][] activitateInfo = Query.getActivitateTableInfoOnUser(connection, profesorString);
            if(activitateInfo == null)
            {
                foundUser.setText("Utilizatorul cautat nu are nicio activitate!");
                clearAllFields();
                return;
            }

            ObservableList<Activitate> listActivitate = FXCollections.observableArrayList();
            for(String[] row: activitateInfo)
            {
                listActivitate.add(rowToActivitate(row));
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiColumn.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        procentColumn.setCellValueFactory(new PropertyValueFactory<>("procentNota"));

        removeEllipses(idColumn);
        removeEllipses(usernameColumn);
        removeEllipses(numeColumn);
        removeEllipses(tipColumn);
        removeEllipses(descriereColumn);
        removeEllipses(nrMaximStudentiColumn);
        removeEllipses(procentColumn);
    }

    private void populateTableActivitati()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getActivitateTableInfo(connection);

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitate(row);
            list.add(activitate);
        }

        tableActivitati.setItems(list);
    }

    public void generateTableParticipanti()
    {
        usernameProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("usernameProfesor"));
        numeCursParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        nrMaximStudentiParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        nrParticipantiColumn.setCellValueFactory(new PropertyValueFactory<>("nrParticipanti"));

        removeEllipses(usernameProfesorColumn);
        removeEllipses(numeCursParticipantColumn);
        removeEllipses(tipParticipantColumn);
        removeEllipses(nrMaximStudentiParticipantColumn);
        removeEllipses(nrParticipantiColumn);
    }

    public void populateTableParticipanti()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<ParticipantActivitate> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getParticipantiTableInfo(connection);

        for(String[] row: allInfo)
        {
            ParticipantActivitate participantActivitate = rowToParticipantActivitate(row);
            list.add(participantActivitate);
        }

        tableParticipant.setItems(list);
    }

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
    }

    private void clearAllFields()
    {
        usernameField.clear();
        courseNameField.clear();
    }

    private void clearErrors()
    {
        foundUser.setText("");
        foundActivity.setText("");
        errorHandling.setTextFill(Color.RED);
        errorHandling.setText("");
    }

    private Activitate rowToActivitate(String[] row)
    {
        int idActivitate = -1;
        int nrMaximStudenti = -1;
        int procentNota = -1;

        try
        {
            idActivitate = Integer.parseInt(row[0]);
            nrMaximStudenti = Integer.parseInt(row[5]);
            procentNota = Integer.parseInt(row[6]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new Activitate(idActivitate, row[1], row[2], row[3], row[4],nrMaximStudenti, procentNota);
    }

    private ParticipantActivitate rowToParticipantActivitate(String[] row)
    {
        int nrMaximStudenti = -1;
        int nrParticipanti = -1;

        try
        {
            nrMaximStudenti = Integer.parseInt(row[3]);
            nrParticipanti = Integer.parseInt(row[4]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new ParticipantActivitate(row[0], row[1], row[2], nrMaximStudenti, nrParticipanti);
    }
}
