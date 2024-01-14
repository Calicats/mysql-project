package com.example.controllers;

import com.example.Activitate;
import com.example.Curs;
import com.example.platforma.Main;
import com.example.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.rgb;

// TODO: Remake this class because of the internal database changes
// SEARCH FOR         throw new UnsupportedOperationException("You have to remake this method because of the internal database changes!");
public class ManageActivitateController {

    @FXML
    private Button activityButton;

    @FXML
    private Button backButton;

    @FXML
    private Button courseButton;

    @FXML
    private TextField courseNameField;

    @FXML
    private TableColumn<Activitate, String> descriereColumn;

    @FXML
    private TableColumn<Curs, String> descriereCursColumn;

    @FXML
    private TextField descriereField;

    @FXML
    private Label errorHandling;

    @FXML
    private Button findUsernameButton;

    @FXML
    private Label foundActivity;

    @FXML
    private Label foundUser;

    @FXML
    private Label idCursLabel;

    @FXML
    private TextField idCursField;

    @FXML
    private Label idActivitateLabel;

    @FXML
    private TextField idActivitateField;

    @FXML
    private TextField maxStudentField;

    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumn;

    @FXML
    private TableColumn<Curs, String> nrMaximStudentiCursColumn;

    @FXML
    private TableColumn<Activitate, String> numeColumn;

    @FXML
    private TableColumn<Curs, String> numeCursColumn;

    @FXML
    private TableColumn<Activitate, String> procentColumn;

    @FXML
    private TableColumn<Activitate, String> idColumn;

    @FXML
    private TableColumn<Curs, String> idCursColumn;

    @FXML
    private TextField procentField;

    @FXML
    private ComboBox<String> selectActivityComboBox;

    @FXML
    private ComboBox<String> selectOperationComboBox;

    @FXML
    private TableColumn<Activitate, String> tipColumn;

    @FXML
    private TableColumn<Activitate, String> usernameColumn;

    @FXML
    private TextField usernameProfesorField;

    @FXML
    private TableView<Activitate> tableActivitati;

    @FXML
    private TableView<Curs> tableCurs;

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

        generateTableActivitati();
        populateTableActivitati();

        generateTableCurs();
        populateTableCurs();

        setModifyDeleteFieldsVisibile(false);

        String[] activities = {"Curs", "Seminar", "Laborator", "Examen", "Restanta"};
        String[] operations = {"Adauga", "Modifica", "Sterge"};

        ObservableList<String> activitiesList = FXCollections.observableArrayList(activities);
        ObservableList<String> operationsList = FXCollections.observableArrayList(operations);

        selectActivityComboBox.setItems(activitiesList);
        selectOperationComboBox.setItems(operationsList);
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

    public void onSearchUsername()
    {
        setModifyDeleteFieldsVisibile(false);
        clearErrors();
        tableActivitati.getItems().clear();

        String usernameString = usernameProfesorField.getText();
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

            if(!Query.userExistsInTable(connection, usernameString, "profesor"))
            {
                errorHandling.setText("Utilizatorul cautat nu exista!");
                return;
            }

            String[][] activitateProfesor = Query.getActivitateTableInfoOnUser(connection, usernameString);
            populateTableActivitati(usernameString);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onSelectOperation()
    {
        setModifyDeleteFieldsVisibile(false);
        clearErrors();

        String operationString = selectOperationComboBox.getValue();
        if(operationString == null)
        {
            errorHandling.setText("Alege o operatie!");
            return;
        }
        if(operationString.equals("Modifica"))
        {
            setModifyDeleteFieldsVisibile(true);
        }

        activityButton.setText(operationString);
        courseButton.setText(operationString);
    }

    public void onCourseButton()
    {
        clearErrors();

        String courseString = courseNameField.getText();
        String descriereString = descriereField.getText();
        String operationString = selectOperationComboBox.getValue();

        if(operationString == null)
        {
            errorHandling.setText("Selecteaza o operatie!");
            return;
        }

        switch(operationString)
        {
            case "Adauga" ->
            {
                if(courseString.isEmpty())
                {
                    errorHandling.setText("Introdu un curs!");
                    return;
                }

                if(descriereString.isEmpty())
                {
                    errorHandling.setText("Introdu o descriere!");
                    return;
                }

                if(maxStudentField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu un numar maxim de studenti!");
                    return;
                }

                int nrMaxStudenti = Integer.parseInt(maxStudentField.getText());

                try
                {
                    Connection connection = Connect.getConnection();
                    if(connection == null)
                    {
                        return;
                    }

                    if(Query.existsCursLowerCase(connection, courseString))
                    {
                        errorHandling.setText("Exista deja cursul introdus!");
                        return;
                    }

                    Insert.addNewCurs(connection, courseString, descriereString, nrMaxStudenti);
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Inserare curs cu succes!");
                }
                catch(Exception e)
                {
                    errorHandling.setText("");
                    e.printStackTrace();
                }
            }
            case "Modifica" ->
            {
                String[] columns = {"numeCurs", "descriere", "nrMaximStudenti"};

                if(idCursField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu un id din tabela de cursuri!");
                    return;
                }

                if(courseString.isEmpty() && descriereString.isEmpty() && maxStudentField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu cel putin o valoare!");
                    return;
                }

                List<String> validEntries = getValidEntries(courseString, descriereString);
                int idCurs = Integer.parseInt(idCursField.getText());

                try
                {
                    Connection connection = Connect.getConnection();
                    if(connection == null)
                    {
                        return;
                    }

                    if(idCurs <= 0)
                    {
                        errorHandling.setText("Introdu un id valid!");
                        return;
                    }

                    int rowsAffected = 0;
                    for(int i = 0; i < 3; ++i)
                    {
                        String newValue = validEntries.get(i);
                        if(!newValue.equals("null"))
                        {
                            int updated = 0;
                            if(columns[i].equals("nrMaximStudenti"))
                            {
                                updated = Update.updateEntryInCurs(connection, idCurs, "Curs", columns[i], Integer.parseInt(newValue));
                            }
                            else
                            {
                                updated = Update.updateEntryInCurs(connection, idCurs, "curs", columns[i], newValue);
                            }

                            if(updated > 0)
                            {
                                ++rowsAffected;
                            }
                        }
                    }

                    if(rowsAffected > 0)
                    {
                        errorHandling.setTextFill(rgb(0, 255, 0));
                        errorHandling.setText("Modificare efectuata cu success!");
                    }
                    else
                    {
                        errorHandling.setText("Modificarea a esuat!");
                    }
                }
                catch(Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    e.printStackTrace();
                }
            }
            case "Sterge" ->
            {
                if(idCursField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu un id din tabela de cursuri!");
                    return;
                }

                int idCurs = Integer.parseInt(idCursField.getText());
                try
                {
                    Connection connection = Connect.getConnection();
                    if(connection == null)
                    {
                        return;
                    }

                    if(idCurs <= 0)
                    {
                        errorHandling.setText("Introdu un id valid!");
                        return;
                    }

                    int rowsAffected = Delete.deleteCurs(connection, idCurs);
                    if(rowsAffected > 0)
                    {
                        errorHandling.setTextFill(rgb(0, 255, 0));
                        errorHandling.setText("Stergere efectuata cu success!");
                    }
                    else
                    {
                        errorHandling.setText("Stergerea a esuat!");
                    }
                }
                catch(Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void onActivityButton()
    {
        clearErrors();

        String operationString = selectOperationComboBox.getValue();
        if(operationString == null)
        {
            errorHandling.setText("Alege o operatie!");
            return;
        }

        String activityString = selectActivityComboBox.getValue();
        if(activityString == null)
        {
            errorHandling.setText("Alege o activitate!");
            return;
        }

        String usernameString = usernameProfesorField.getText();
        String procentString = procentField.getText();
        String courseString = courseNameField.getText();

        if(usernameString.isEmpty())
        {
            errorHandling.setText("Introdu un utilizator!");
            return;
        }

        if(courseString.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        int procentInt = -1;
        try
        {
            procentInt = Integer.parseInt(procentString);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(procentInt < 0 || procentInt > 100)
        {
            errorHandling.setText("Introdu un procent valid!");
            return;
        }

        switch(operationString)
        {
            case "Adauga" ->
            {
                if(procentString.isEmpty())
                {
                    errorHandling.setText("Introdu un procent de nota!");
                    return;
                }

                try
                {
                    Connection connection = Connect.getConnection();
                    if(connection == null)
                    {
                        return;
                    }

                    if(!Query.userExistsInTable(connection, usernameString, "profesor"))
                    {
                        errorHandling.setText("Utilizatorul nu exista!");
                        return;
                    }

                    if(!Query.existsCursLowerCase(connection, courseString))
                    {
                        errorHandling.setText("Cursul nu exista!");
                        return;
                    }

                    if(Query.existsProfesorInActivity(connection, usernameString, courseString, activityString))
                    {
                        errorHandling.setText("Utilizatorul este deja inscris in acel curs!");
                        return;
                    }

                    Insert.addNewActivitate(connection, activityString, procentInt, courseString, usernameString);
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Inserare efectuata cu success!");
                }
                catch(Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    e.printStackTrace();
                }
            }

            case "Modifica" ->
            {
                Connection connection = Connect.getConnection();
                if(connection == null)
                {
                    return;
                }

                if(procentString.isEmpty())
                {
                    errorHandling.setText("Introdu un procent!");
                    return;
                }

                if(idActivitateField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu un id din tabela de activitati!");
                    return;
                }

                int idActivitate = Integer.parseInt(idActivitateField.getText());

                try
                {
                    if(!Query.userExistsInTable(connection, usernameString, "profesor"))
                    {
                        errorHandling.setText("Utilizatorul cautat nu exista!");
                        return;
                    }

                    if(!Query.existsCursLowerCase(connection, courseString))
                    {
                        errorHandling.setText("Cursul nu exista!");
                        return;
                    }

                    if(idActivitate <= 0)
                    {
                        errorHandling.setText("Introdu un id valid!");
                        return;
                    }

                    int updatedProcent = Update.updateEntryInActivitate(connection, idActivitate, "activitate", "procentNota", procentInt);
                    int updatedTip = Update.updateEntryInActivitate(connection, idActivitate, "activitate", "tip", activityString);

                    if(updatedProcent + updatedTip > 0)
                    {
                        errorHandling.setTextFill(rgb(0, 255, 0));
                        errorHandling.setText("Modificare efectuata cu success!");
                    }
                    else
                    {
                        errorHandling.setText("Modificarea a esuat!");
                    }

                }
                catch(Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    e.printStackTrace();
                }
            }

            case "Sterge" ->
            {
                Connection connection = Connect.getConnection();
                if(connection == null)
                {
                    return;
                }

                if(idActivitateField.getText().isEmpty())
                {
                    errorHandling.setText("Introdu un id din tabela de activitati!");
                    return;
                }

                int idActivitate = Integer.parseInt(idActivitateField.getText());

                try
                {
                    if(!Query.userExistsInTable(connection, usernameString, "profesor"))
                    {
                        errorHandling.setText("Utilizatorul cautat nu exista!");
                        return;
                    }

                    if(!Query.existsCursLowerCase(connection, courseString))
                    {
                        errorHandling.setText("Cursul nu exista!");
                        return;
                    }

                    if(idActivitate <= 0)
                    {
                        errorHandling.setText("Introdu un id valid!");
                        return;
                    }

                    int rowsAffected = Delete.deleteActivitate(connection, idActivitate);

                    if(rowsAffected > 0)
                    {
                        errorHandling.setTextFill(rgb(0, 255, 0));
                        errorHandling.setText("Stergere efectuata cu success!");
                    }
                    else
                    {
                        errorHandling.setText("Stergerea a esuat!");
                    }
                }
                catch(Exception e)
                {
                    errorHandling.setText(e.getMessage());
                    e.printStackTrace();
                }
            }
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

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
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

    private void populateTableActivitati(String username)
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] info = Query.getActivitateTableInfoOnUser(connection, username);

        if(info == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: info)
        {
            Activitate activitate = rowToActivitate(row);
            list.add(activitate);
        }

        tableActivitati.setItems(list);
    }

    private void generateTableCurs()
    {
        idCursColumn.setCellValueFactory(new PropertyValueFactory<>("idCurs"));
        numeCursColumn.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        descriereCursColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiCursColumn.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));

        removeEllipses(idCursColumn);
        removeEllipses(numeCursColumn);
        removeEllipses(descriereCursColumn);
        removeEllipses(nrMaximStudentiCursColumn);
    }

    private void populateTableCurs()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Curs> list = FXCollections.observableArrayList();
        String[][] info = Query.getCursTableInfo(connection);

        if(info == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: info)
        {
            Curs curs = rowToCurs(row);
            list.add(curs);
        }

        tableCurs.setItems(list);
    }

    private void populateTableCurs(String[][] info)
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Curs> list = FXCollections.observableArrayList();
        info = Query.getCursTableInfo(connection);

        if(info == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: info)
        {
            Curs curs = rowToCurs(row);
            list.add(curs);
        }

        tableCurs.setItems(list);
    }

    private List<String> getValidEntries(String courseString, String descriereString)
    {
        List<String> validEntries = new ArrayList<>();

        if(courseString.isEmpty())
        {
            validEntries.add("null");
        }
        else
        {
            validEntries.add(courseString);
        }

        if(descriereString.isEmpty())
        {
            validEntries.add("null");
        }
        else
        {
            validEntries.add(descriereString);
        }

        if(maxStudentField.getText().isEmpty())
        {
            validEntries.add("null");
        }
        else
        {
            validEntries.add(maxStudentField.getText());
        }

        return validEntries;
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

    private Curs rowToCurs(String[] row)
    {
        int idCurs = -1;
        int nrMaximStudenti = -1;
        try
        {
            idCurs = Integer.parseInt(row[0]);
            nrMaximStudenti = Integer.parseInt(row[3]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new Curs(idCurs, row[1], row[2], nrMaximStudenti);
    }

    private void setModifyDeleteFieldsVisibile(boolean expr)
    {
        idCursLabel.setVisible(expr);
        idCursField.setVisible(expr);

        idActivitateLabel.setVisible(expr);
        idActivitateField.setVisible(expr);
    }

    private void clearErrors()
    {
        errorHandling.setTextFill(rgb(255, 0, 0));
        errorHandling.setText("");
    }
}
