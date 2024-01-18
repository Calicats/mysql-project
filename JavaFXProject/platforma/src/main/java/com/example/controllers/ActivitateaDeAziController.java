package com.example.controllers;

import com.example.ActivitatePeZi;
import com.example.Profesor;
import com.example.Student;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class ActivitateaDeAziController {
    public TableView<ActivitatePeZi> situatiaLaZiTable;

    String table = "null";

    public void init(String table){
        this.table = table;
        // populate table
        // create columns
        situatiaLaZiTable.getColumns().clear();
        /*
            private String name;
            private int startHour;
            private int endHour;
            private int startMinutes;
            private int endMinutes;

            private String tip;

            private LocalDate startDate;
            private LocalDate endDate;
         */
        TableColumn<ActivitatePeZi, String> nameColumn = new TableColumn<>("Nume");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ActivitatePeZi, String> startHourColumn = new TableColumn<>("Ora inceput");
        startHourColumn.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        TableColumn<ActivitatePeZi, String> endHourColumn = new TableColumn<>("Ora sfarsit");
        endHourColumn.setCellValueFactory(new PropertyValueFactory<>("endHour"));
        TableColumn<ActivitatePeZi, String> startMinutesColumn = new TableColumn<>("Minute inceput");
        startMinutesColumn.setCellValueFactory(new PropertyValueFactory<>("startMinutes"));
        TableColumn<ActivitatePeZi, String> endMinutesColumn = new TableColumn<>("Minute sfarsit");
        endMinutesColumn.setCellValueFactory(new PropertyValueFactory<>("endMinutes"));
        TableColumn<ActivitatePeZi, String> tipColumn = new TableColumn<>("Tip");
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        TableColumn<ActivitatePeZi, String> startDateColumn = new TableColumn<>("Data inceput");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        TableColumn<ActivitatePeZi, String> endDateColumn = new TableColumn<>("Data sfarsit");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        // add columns
        situatiaLaZiTable.getColumns().addAll(nameColumn, startHourColumn, endHourColumn, startMinutesColumn, endMinutesColumn, tipColumn, startDateColumn, endDateColumn);

        if(table.equals("student"))
            try {
                int idStudent = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), table)+1;
                situatiaLaZiTable.setItems(ActivitatePeZi.getActivitateZi(new Student(idStudent)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if(table.equals("profesor"))
            try {
                int idProfesor = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), table)+1;
                situatiaLaZiTable.setItems(ActivitatePeZi.getActivitateZi(new Profesor(idProfesor)));
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    public void goBack(ActionEvent actionEvent) {
        try {
            if(table.equals("student"))
                Main.main.changeScene("panouStudent.fxml");
            else
                Main.main.changeScene("panouProfesor.fxml");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
