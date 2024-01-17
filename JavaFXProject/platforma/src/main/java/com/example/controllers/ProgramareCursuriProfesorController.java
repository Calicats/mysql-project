package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO Rewrite this class

public class ProgramareCursuriProfesorController {
    public Button backButton;
    public ChoiceBox dropBoxSelecteazaActivitate;
    public DatePicker dataIncepereInput;
    public DatePicker dataFinalizareInput;
    public ChoiceBox dropBoxSelecteazaZiua;
    public ChoiceBox dropBoxSelecteazaFrecventa;
    public TextField oraIncepereTextInput;
    public TextField minuteIncepereTextInput;
    public TextField durataTextInput;
    public Button butonSalveazaDatele;
    public TableView programareActivitateTable;
    public TextField idActivitateTextField;

    private String username;
    private String tableName;

    ProgramareActivitate programareActivitateCurrent = null;
    boolean isUpdate = false;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

    }

    public void closeScreen(ActionEvent actionEvent)  {
        try{
            Main.main.changeScene("panouProfesor.fxml", username, tableName, 1024, 768);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadActivitatiSelectorActivitate(ContextMenuEvent contextMenuEvent) {

        programareActivitateTable.getItems().clear();
        programareActivitateTable.getColumns().clear();
        // create columns
        TableColumn<ProgramareActivitate, String> column1 = new TableColumn<>("Nume Activitate");
        column1.setCellValueFactory(new PropertyValueFactory<>("numeActivitate"));

        TableColumn<ProgramareActivitate, Integer> column2 = new TableColumn<>("ID");
        column2.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProgramareActivitate, Date> column3 = new TableColumn<>("Data Incepere");
        column3.setCellValueFactory(new PropertyValueFactory<>("dataIncepere"));

        TableColumn<ProgramareActivitate, Date> column4 = new TableColumn<>("Data Finalizare");
        column4.setCellValueFactory(new PropertyValueFactory<>("dataFinalizare"));

        TableColumn<ProgramareActivitate, String> column5 = new TableColumn<>("Frecventa");
        column5.setCellValueFactory(new PropertyValueFactory<>("frecventa"));

        TableColumn<ProgramareActivitate, String> column6 = new TableColumn<>("Zi");
        column6.setCellValueFactory(new PropertyValueFactory<>("zi"));

        TableColumn<ProgramareActivitate, Integer> column7 = new TableColumn<>("Ora");
        column7.setCellValueFactory(new PropertyValueFactory<>("ora"));

        TableColumn<ProgramareActivitate, Integer> column8 = new TableColumn<>("Minut");
        column8.setCellValueFactory(new PropertyValueFactory<>("minut"));

        TableColumn<ProgramareActivitate, Integer> column9 = new TableColumn<>("Durata");
        column9.setCellValueFactory(new PropertyValueFactory<>("durata"));

        TableColumn<ProgramareActivitate, Integer> column10 = new TableColumn<>("ID Participant Activitate");
        column10.setCellValueFactory(new PropertyValueFactory<>("idParticipantActivitate"));

        // add columns
        programareActivitateTable.getColumns().add(column1);
        programareActivitateTable.getColumns().add(column2);
        programareActivitateTable.getColumns().add(column3);
        programareActivitateTable.getColumns().add(column4);
        programareActivitateTable.getColumns().add(column5);
        programareActivitateTable.getColumns().add(column6);
        programareActivitateTable.getColumns().add(column7);
        programareActivitateTable.getColumns().add(column8);
        programareActivitateTable.getColumns().add(column9);
        programareActivitateTable.getColumns().add(column10);


        try {
            String [][] programareCursuri = Query.getQueryForProgramareCursuri(Objects.requireNonNull(Connect.getConnection()), Main.main.username);
            List<ProgramareActivitate> programareActivitateObservableList = new ArrayList<>();
            if(programareCursuri.length == 0){
                System.out.println("Nu exista activitati");
                return;
            }
            for (String[] strings : programareCursuri) {
                ProgramareActivitate programareActivitate = new ProgramareActivitate(Integer.parseInt(strings[0]), Date.valueOf(strings[1]), Date.valueOf(strings[2]), strings[3], strings[4], Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), Integer.parseInt(strings[7]), Integer.parseInt(strings[8]), Integer.parseInt(strings[9]));
                programareActivitateObservableList.add(programareActivitate);
            }
            programareActivitateTable.setItems(FXCollections.observableList(programareActivitateObservableList));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Eroare la incarcarea activitatilor");
        }






    }

    public void dropBoxSelecteazaActivitateSelectItem(MouseEvent mouseEvent) {
        // when an item is selected, load the data from the database
        // and fill the fields with it
        //ActivitateProfesor activitate = (ActivitateProfesor) dropBoxSelecteazaActivitate.getItems().get(dropBoxSelecteazaActivitate.getSelectionModel().getSelectedIndex());
/*
        CalendarActivitate calendarActivitate = CalendarActivitate.getTable().get(activitate.get);
        dataIncepereInput.setValue(activitate.getData_incepere());
        dataFinalizareInput.setValue(activitate.getData_finalizare());
        dropBoxSelecteazaZiua.setValue(activitate.getZiua());
        dropBoxSelecteazaFrecventa.setValue(activitate.getFrecventa());
        oraIncepereTextInput.setText(activitate.getOra_incepere());
        minuteIncepereTextInput.setText(activitate.getMinute_incepere());
        durataTextInput.setText(activitate.getDurata());

 */


    }

    public void selecteazaDataIncepere(ActionEvent actionEvent) {
        // check if the date is valid
    }

    public void selecteazaDataFinalizare(ActionEvent actionEvent) {
    }

    public void dropBoxSelecteazaZiuaSelectItem(MouseEvent mouseEvent) {
    }

    public void dropBoxSelecteazaFrecventaSelectItem(MouseEvent mouseEvent) {
    }

    public void createDialogErrorBox(String message){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Eroare");
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    public void searchActivitate(ActionEvent actionEvent) {
        // search for the activitate
        // if it exists, load the data
// else, display an error message
        int idActivitate=-1;
        try {
            idActivitate = Integer.parseInt(idActivitateTextField.getText());
        }
        catch (Exception e){
            System.out.println("ID-ul activitatii trebuie sa fie un numar intreg");
            // show an error message create a dialog box
            createDialogErrorBox("ID-ul activitatii trebuie sa fie un numar intreg");
            return;
        }
        // check if the activitate exists
        if(!Query.checkIfActivitateExists(Objects.requireNonNull(Connect.getConnection()), idActivitate)){
            System.out.println("Activitatea cu id-ul " + idActivitate + " nu exista");
            createDialogErrorBox("Activitatea cu id-ul " + idActivitate + " nu exista");
            return;
        }

        Activitate activitate = new Activitate(idActivitate);
        int idProfesor = 0;
        try {
            idProfesor = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "profesor",Main.main.username)-1;
            if(activitate.getIdProfesor() != idProfesor){
                System.out.println("Nu aveti dreptul sa modificati aceasta activitate");
                createDialogErrorBox("Nu aveti dreptul sa modificati aceasta activitate");
                return;
            }
        } catch (Exception e) {
            createDialogErrorBox("An error has been encountered, please try again later.");
            e.printStackTrace();
            return;
        }

        // get programareactivitate that is linked with activitate from the database
// and fill the fields with it
        List<ProgramareActivitate> programareActivitateList = ProgramareActivitate.getTable();
        ProgramareActivitate programareActivitate = null;
        // get the programareactivitate that is linked with activitate
        for(ProgramareActivitate p : programareActivitateList){
            ParticipantActivitate pa = new ParticipantActivitate(p.getIdParticipantActivitate());
            if(pa.getIdActivitate() == idActivitate && pa.getIdProfesor() == idProfesor){
                programareActivitate = p;
                break;
            }
        }
        if(programareActivitate == null){
            System.out.println("Nu exista o programare pentru aceasta activitate");
            createDialogErrorBox("Nu exista o programare pentru aceasta activitate. Se va crea una noua.");
            programareActivitateCurrent = new ProgramareActivitate(ProgramareActivitate.getTable().size());
            isUpdate = false;
            return;
        }
        programareActivitateCurrent = programareActivitate;
        isUpdate = true;
        // fill the fields with the data from the database
        dataIncepereInput.setValue(programareActivitate.getDataIncepere().toLocalDate());
        dataFinalizareInput.setValue(programareActivitate.getDataFinalizare().toLocalDate());
        dropBoxSelecteazaZiua.setValue(programareActivitate.getZi());
        dropBoxSelecteazaFrecventa.setValue(programareActivitate.getFrecventa());
        oraIncepereTextInput.setText(String.valueOf(programareActivitate.getOra()));
        minuteIncepereTextInput.setText(String.valueOf(programareActivitate.getMinut()));
        durataTextInput.setText(String.valueOf(programareActivitate.getDurata()));


    }
}
