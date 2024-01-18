package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO Rewrite this class

public class ProgramareCursuriProfesorController {
    public Button backButton;
    public DatePicker dataIncepereInput;
    public DatePicker dataFinalizareInput;
    public ChoiceBox<ZI> dropBoxSelecteazaZiua;
    public ChoiceBox<FRECVENTA> dropBoxSelecteazaFrecventa;
    public TextField oraIncepereTextInput;
    public TextField minuteIncepereTextInput;
    public TextField durataTextInput;
    public Button butonSalveazaDatele;
    public TableView<ProgramareActivitate> programareActivitateTable;
    public TextField idActivitateTextField;
    public TableView<Activitate> tabelActivitati;

    private String username;
    private String tableName;



    public enum ZI {
        Luni,
        Marti,
        Miercuri,
        Joi,
        Vineri,
        Sambata,
        Duminica
    }

    public enum FRECVENTA {
        Saptamanal,
        Bilunar,
        Lunar,
        Odata
    }

    ProgramareActivitate programareActivitateCurrent = null;
    boolean isUpdate = false;

    int currentIdActivitate = -1;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;
        tabelActivitati.getItems().clear();
        tabelActivitati.getColumns().clear();
        // create columns
        /*
            private int idActivitate;
    private String tip;
    private String nume;
    private String descriere;
    private int nrMaximStudenti;
    private int idCurs;
    private int idProfesor;
         */
        TableColumn<Activitate, String> column2 = new TableColumn<>("ID");
        column2.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        TableColumn<Activitate, String> column1 = new TableColumn<>("Tip");
        column1.setCellValueFactory(new PropertyValueFactory<>("tip"));
        TableColumn<Activitate, Integer> column3 = new TableColumn<>("Nr. Maxim Studenti");
        column3.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        TableColumn<Activitate, Integer> column4 = new TableColumn<>("Nume Curs");
        column4.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        // add columns
        tabelActivitati.getColumns().add(column2);
        tabelActivitati.getColumns().add(column1);
        tabelActivitati.getColumns().add(column3);
        tabelActivitati.getColumns().add(column4);
        ObservableList<Activitate> activitateObservableList = FXCollections.observableArrayList();
        for (Activitate activitate : Activitate.getTable()) {
            try {
                if(activitate.getIdProfesor() == Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "profesor", username)){
                    activitate.setNumeCurs(new Curs(activitate.getIdCurs()).getNumeCurs());
                    activitateObservableList.add(activitate);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        tabelActivitati.setItems(activitateObservableList);


        programareActivitateTable.getItems().clear();
        programareActivitateTable.getColumns().clear();
        // create columns
        /*
            private int id;
            private java.sql.Date dataIncepere;
            private java.sql.Date dataFinalizare;
            private String frecventa;
            private String zi;
            private int ora;
            private int minut;
            private int durata;
            private int idParticipantActivitate;

            // field for query DO NOT TOUCH OR CHANGE
            private String numeActivitate;
         */
        TableColumn<ProgramareActivitate, Integer> column14 = new TableColumn<>("ID Activitate");
        column14.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        TableColumn<ProgramareActivitate, Integer> column6 = new TableColumn<>("ID");
        column6.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<ProgramareActivitate, Date> column7 = new TableColumn<>("Data Incepere");
        column7.setCellValueFactory(new PropertyValueFactory<>("dataIncepere"));
        TableColumn<ProgramareActivitate, Date> column8 = new TableColumn<>("Data Finalizare");
        column8.setCellValueFactory(new PropertyValueFactory<>("dataFinalizare"));
        TableColumn<ProgramareActivitate, String> column9 = new TableColumn<>("Frecventa");
        column9.setCellValueFactory(new PropertyValueFactory<>("frecventa"));
        TableColumn<ProgramareActivitate, String> column10 = new TableColumn<>("Zi");
        column10.setCellValueFactory(new PropertyValueFactory<>("zi"));
        TableColumn<ProgramareActivitate, Integer> column11 = new TableColumn<>("Ora");
        column11.setCellValueFactory(new PropertyValueFactory<>("ora"));
        TableColumn<ProgramareActivitate, Integer> column12 = new TableColumn<>("Minut");
        column12.setCellValueFactory(new PropertyValueFactory<>("minut"));
        TableColumn<ProgramareActivitate, Integer> column13 = new TableColumn<>("Durata");
        column13.setCellValueFactory(new PropertyValueFactory<>("durata"));


        // add columns
        programareActivitateTable.getColumns().add(column14);
        programareActivitateTable.getColumns().add(column6);
        programareActivitateTable.getColumns().add(column7);
        programareActivitateTable.getColumns().add(column8);
        programareActivitateTable.getColumns().add(column9);
        programareActivitateTable.getColumns().add(column10);
        programareActivitateTable.getColumns().add(column11);
        programareActivitateTable.getColumns().add(column12);
        programareActivitateTable.getColumns().add(column13);


        // load the data from the database
        ObservableList<ProgramareActivitate> programareActivitateObservableList = FXCollections.observableArrayList();
        for (ProgramareActivitate programareActivitate : ProgramareActivitate.getTable()) {
            try {
                if(new Activitate(programareActivitate.getIdActivitate()).getIdProfesor()==Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "profesor", username)){
                    programareActivitate.setNumeActivitate();
                    programareActivitateObservableList.add(programareActivitate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        programareActivitateTable.setItems(programareActivitateObservableList);

        dropBoxSelecteazaZiua.getItems().clear();
        for (ZI zi : ZI.values()) {
            dropBoxSelecteazaZiua.getItems().add(zi);
        }
        dropBoxSelecteazaFrecventa.getItems().clear();
        for (FRECVENTA frecventa : FRECVENTA.values()) {
            dropBoxSelecteazaFrecventa.getItems().add(frecventa);
        }



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

    public void selecteazaDataIncepere(ActionEvent actionEvent) {
        // check if the date is valid and in the future

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
            idProfesor = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "profesor",Main.main.username);
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
            if(p.getIdActivitate() == idActivitate){
                programareActivitate = p;
                break;
            }
        }

        currentIdActivitate = idActivitate;
        // get the participantactivitate that is linked with activitate


        if(programareActivitate == null){
            System.out.println("Nu exista o programare pentru aceasta activitate");
            createDialogErrorBox("Nu exista o programare pentru aceasta activitate. Se va crea una noua.");
            programareActivitateCurrent = new ProgramareActivitate();
            // clean all the fields
            dataIncepereInput.setValue(null);
            dataFinalizareInput.setValue(null);
            dropBoxSelecteazaZiua.setValue(null);
            dropBoxSelecteazaFrecventa.setValue(null);
            oraIncepereTextInput.setText("");
            minuteIncepereTextInput.setText("");
            durataTextInput.setText("");
            isUpdate = false;
            return;
        }
        programareActivitateCurrent = programareActivitate;
        isUpdate = true;
        // fill the fields with the data from the database
        dataIncepereInput.setValue(programareActivitate.getDataIncepere().toLocalDate());
        dataFinalizareInput.setValue(programareActivitate.getDataFinalizare().toLocalDate());
        dropBoxSelecteazaZiua.setValue(ZI.valueOf(programareActivitate.getZi()));
        dropBoxSelecteazaFrecventa.setValue(FRECVENTA.valueOf(programareActivitate.getFrecventa()));
        oraIncepereTextInput.setText(String.valueOf(programareActivitate.getOra()));
        minuteIncepereTextInput.setText(String.valueOf(programareActivitate.getMinut()));
        durataTextInput.setText(String.valueOf(programareActivitate.getDurata()));


    }

    public void salveazaDatele(ActionEvent actionEvent) {
        // check if the data is valid
        // if it is, save it to the database
        // else, display an error message
        if(dataIncepereInput.getValue() == null || dataFinalizareInput.getValue() == null && !dropBoxSelecteazaFrecventa.getSelectionModel().isSelected(3) || dropBoxSelecteazaZiua.getValue() == null || dropBoxSelecteazaFrecventa.getValue() == null || oraIncepereTextInput.getText().equals("") || minuteIncepereTextInput.getText().equals("") || durataTextInput.getText().equals("")){
            System.out.println("Toate campurile trebuie completate");
            createDialogErrorBox("Toate campurile trebuie completate");
            return;
        }
        // check if the data is valid and in the future
        if(dataIncepereInput.getValue().isBefore(java.time.LocalDate.now()) || dataFinalizareInput.getValue().isBefore(java.time.LocalDate.now()) || dataFinalizareInput.getValue().isBefore(dataIncepereInput.getValue()) && !dropBoxSelecteazaFrecventa.getSelectionModel().isSelected(3)){
            System.out.println("Data trebuie sa fie in viitor");
            createDialogErrorBox("Data trebuie sa fie in viitor");
            return;
        }
        // check if the rest are valid numbers and if they are in the correct range
        int oraIncepere = 0;
        int minuteIncepere = 0;
        int durata = 0;
        try {
            oraIncepere = Integer.parseInt(oraIncepereTextInput.getText());
            minuteIncepere = Integer.parseInt(minuteIncepereTextInput.getText());
            durata = Integer.parseInt(durataTextInput.getText());
        }
        catch (Exception e){
            System.out.println("Ora, minutul si durata trebuie sa fie numere intregi");
            createDialogErrorBox("Ora, minutul si durata trebuie sa fie numere intregi");
            return;
        }
        if(oraIncepere < 0 || oraIncepere > 23){
            System.out.println("Ora trebuie sa fie intre 0 si 23");
            createDialogErrorBox("Ora trebuie sa fie intre 0 si 23");
            return;
        }
        if(minuteIncepere < 0 || minuteIncepere > 59){
            System.out.println("Minutele trebuie sa fie intre 0 si 59");
            createDialogErrorBox("Minutele trebuie sa fie intre 0 si 59");
            return;
        }
        if(durata < 0){
            System.out.println("Durata trebuie sa fie mai mare decat 0");
            createDialogErrorBox("Durata trebuie sa fie mai mare decat 0");
            return;
        }

        // check if the activitate exists
        if(currentIdActivitate==-1){
            System.out.println("Nu ati selectat o activitate");
            createDialogErrorBox("Nu ati selectat o activitate");
            return;
        }

        // save the data to the database
        // if it is an update, update the data
        // else, create a new entry
        programareActivitateCurrent.setDataIncepere(Date.valueOf(dataIncepereInput.getValue()));
        programareActivitateCurrent.setDataFinalizare(Date.valueOf(dataFinalizareInput.getValue()));
        programareActivitateCurrent.setFrecventa(dropBoxSelecteazaFrecventa.getValue().toString());
        programareActivitateCurrent.setZi(dropBoxSelecteazaZiua.getValue().toString());
        programareActivitateCurrent.setOra(oraIncepere);
        programareActivitateCurrent.setMinut(minuteIncepere);
        programareActivitateCurrent.setDurata(durata);
        programareActivitateCurrent.setIdActivitate(currentIdActivitate);


        if(isUpdate){
            ProgramareActivitate.updateEntry(programareActivitateCurrent.getId(), programareActivitateCurrent);
            System.out.println("Datele au fost salvate");
            createDialogErrorBox("Datele au fost salvate. \nProgramre: " + programareActivitateCurrent.toString());

            return;
        }
        // create a new entry
        // get the idParticipantActivitate
        int idParticipantActivitate = ProgramareActivitate.getTable().size();
        // create a new entry in the database
        programareActivitateCurrent.setId(idParticipantActivitate);
        ProgramareActivitate.insert(programareActivitateCurrent);

        // tell the user that the data has been saved
        System.out.println("Datele au fost salvate");
        createDialogErrorBox("Datele au fost salvate. \nProgramre: " + programareActivitateCurrent.toString());


    }
}
