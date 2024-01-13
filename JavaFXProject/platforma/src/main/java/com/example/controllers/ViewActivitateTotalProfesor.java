package com.example.controllers;

import com.example.Activitate;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import com.example.sql.Update;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;

import static javafx.scene.paint.Color.rgb;

public class ViewActivitateTotalProfesor {

    @FXML
    private Button backButton;

    @FXML
    private Button modifyButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField procentField;

    @FXML
    private TableView<Activitate> tableActivitati;

    @FXML
    private TableColumn<Activitate, String> tipColumn;

    @FXML
    private TableColumn<Activitate, String> usernameColumn;

    @FXML
    private TableColumn<Activitate, String> procentColumn;

    @FXML
    private TableColumn<Activitate, String> idColumn;

    @FXML
    private TableColumn<Activitate, String> numeColumn;

    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumn;

    @FXML
    private TableColumn<Activitate, String> descriereColumn;

    @FXML
    private Label errorHandling;

    private String username;
    private String tableName;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        generateTableActivitati();
        populateTableActivitati();
    }

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("panouProfesor.fxml", username, tableName, 1024, 768);
    }

    public void onModify()
    {
        clearErrors();

        String idString = idField.getText();
        if(idString.isEmpty())
        {
            errorHandling.setText("Introdu un id!");
            return;
        }

        String procentString = procentField.getText();
        if(procentString.isEmpty())
        {
            errorHandling.setText("Introdu un procent!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            int idActivitate = Integer.parseInt(idString);
            int procent = Integer.parseInt(procentString);

            if(idActivitate <= 0)
            {
                errorHandling.setText("Introdu un id valid!");
                return;
            }

            if(procent < 0 || procent > 100)
            {
                errorHandling.setText("Introdu un procent valid!");
                return;
            }

            int updated = Update.updateEntryInActivitate(connection, idActivitate, "activitate", "procentNota", procent);
            if(updated > 0)
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

    private void clearErrors()
    {
        errorHandling.setTextFill(rgb(255, 0, 0));
        errorHandling.setText("");
    }

    private void populateTableActivitati()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getActivitateTableInfoOnUser(connection, username);

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
}
