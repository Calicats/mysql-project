package com.example.controllers;

import com.example.Activitate;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;

public class ViewActivitateTotalStudent {

    @FXML
    private TableView<Activitate> tableActivitatiStudent;

    @FXML
    private TableColumn<Activitate, String> tipColumnStudent;

    @FXML
    private TableColumn<Activitate, String> usernameColumnStudent;

    @FXML
    private TableColumn<Activitate, String> procentColumnStudent;

    @FXML
    private TableColumn<Activitate, String> idColumnStudent;

    @FXML
    private TableColumn<Activitate, String> numeColumnStudent;

    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumnStudent;

    @FXML
    private TableColumn<Activitate, String> descriereColumnStudent;


    public void onBackButton() throws IOException
    {
        Main.main.changeScene("panouStudent.fxml", username, tableName, 1024, 768);
    }

    private void generateTableActivitatiStudent()
    {
        idColumnStudent.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        usernameColumnStudent.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeColumnStudent.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipColumnStudent.setCellValueFactory(new PropertyValueFactory<>("tip"));
        descriereColumnStudent.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiColumnStudent.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        procentColumnStudent.setCellValueFactory(new PropertyValueFactory<>("procentNota"));

        removeEllipses(idColumnStudent);
        removeEllipses(usernameColumnStudent);
        removeEllipses(numeColumnStudent);
        removeEllipses(tipColumnStudent);
        removeEllipses(descriereColumnStudent);
        removeEllipses(nrMaximStudentiColumnStudent);
        removeEllipses(procentColumnStudent);
    }

    private void populateTableActivitatiStudent()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] allInfo = null;
        try
        {
            allInfo = Query.getStudentActivitesOnId(connection, username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitateStudent(row);
            list.add(activitate);
        }

        tableActivitatiStudent.setItems(list);
    }

    private Activitate rowToActivitateStudent(String[] row)
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

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
    }

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        generateTableActivitatiStudent();
        populateTableActivitatiStudent();
    }

    private String username;
    private String tableName;
}
