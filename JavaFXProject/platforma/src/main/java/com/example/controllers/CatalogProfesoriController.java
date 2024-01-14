package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import com.example.sql.Update;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO Rewrite this class

public class CatalogProfesoriController {

    public ChoiceBox selectCurs;
    public ChoiceBox selectStudent;
    public Button saveGradeButton;
    public Button backButton;
    @FXML
    public TableView<Activitate> cursuriTable;
    public TextField inputGrade;
    public Label errorLabel;
    public TableView<NoteStudent> studentList;
    public TextField textFieldIdActivitate;
    public Button buttonCautaIdStudent;
    public TextField textFieldUsernameStudent;

    int idUser = -1;
    int currentCursId=0;
    int currentStudentId=0;
    int idActivitateSelectat=-1;
    int idStudentSelectat=-1;
    List<Activitate> activitateProfesorListForThisProfesor = new ArrayList<>();

    public void initialize(String username, String tableName){
        // get the current user's id
        int id=-1;
        try{
            id = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        idUser = id;
        showActivitateTable();

    }
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("panouProfesor.fxml");
    }


    public void showActivitateTable(){
        // get the list of activities
        List<Activitate> activitateProfesorList = Activitate.getTable();
        List<Activitate> activitateProfesorListForThisProfesor = new ArrayList<>();
        for (Activitate activitate : activitateProfesorList) {
            if(activitate.getIdProfesor() == idUser)
            {
                activitateProfesorListForThisProfesor.add(activitate);
            }
        }
        // create the table columns
        TableColumn<Activitate, Integer> idActivitateColumn = new TableColumn<>("Id");
        TableColumn<Activitate, String> numeCursColumn = new TableColumn<>("Nume curs");
        TableColumn<Activitate, String> tipColumn = new TableColumn<>("Tip");
        TableColumn<Activitate, String> descriereColumn = new TableColumn<>("Descriere");
        TableColumn<Activitate, Integer> nrMaximStudentiColumn = new TableColumn<>("Nr. maxim studenti");
        TableColumn<Activitate, Integer> procentNotaColumn = new TableColumn<>("Procent nota");
        // add the columns to the table
        idActivitateColumn.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        numeCursColumn.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiColumn.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        procentNotaColumn.setCellValueFactory(new PropertyValueFactory<>("procentNota"));

        // add the columns to the table
        cursuriTable.getColumns().add(idActivitateColumn);
        cursuriTable.getColumns().add(numeCursColumn);
        cursuriTable.getColumns().add(tipColumn);
        cursuriTable.getColumns().add(descriereColumn);
        cursuriTable.getColumns().add(nrMaximStudentiColumn);
        cursuriTable.getColumns().add(procentNotaColumn);
        // add the list to the table as an observable list
        cursuriTable.setItems(FXCollections.observableList(activitateProfesorListForThisProfesor));


    }


    public void saveGrade(ActionEvent actionEvent) {

        // get the grade and return error if it's not a number
        int grade = 0;
        try {
            grade = Integer.parseInt(inputGrade.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Nota trebuie sa fie un numar!");
            return;
        }
        if(selectStudent.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Nu ati selectat un student!");
            return;
        }
        if(selectCurs.getSelectionModel().isEmpty())
        {
            errorLabel.setText("Nu ati selectat un curs!");
            return;
        }
        if(grade>0 && grade<11) {
            NoteStudent noteStudent = new NoteStudent(NoteStudent.getNextId(), grade, currentStudentId, currentCursId);

        }
        else {
            errorLabel.setText("Nota trebuie sa fie intre 1 si 10!");
            return;
        }

        // save the grade
        Update.updateEntryInNoteStudent(Objects.requireNonNull(Connect.getConnection()), new NoteStudent(NoteStudent.getNextId(), grade, currentStudentId, currentCursId));

    }

    public void cautaActivitate(ActionEvent actionEvent) {
        if(textFieldIdActivitate.getText().isEmpty())
        {
            errorLabel.setText("Nu ati introdus un id!");
            return;
        }
        try{
            currentCursId = Integer.parseInt(textFieldIdActivitate.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Id-ul trebuie sa fie un numar!");
            return;
        }
        Activitate activitate = new Activitate(currentCursId);
        if(activitate.getIdActivitate() == -1)
        {
            errorLabel.setText("Nu exista o activitate cu acest id!");
            return;
        }
        if(activitate.getIdProfesor() != idUser)
        {
            errorLabel.setText("Nu aveti acces la aceasta activitate!");
            return;
        }
        errorLabel.setText("Activitatea a fost gasita!");
        idActivitateSelectat = currentCursId;
        // get the list of students
        List<ParticipantActivitate> studentList = ParticipantActivitate.getTable();
        List<Student> studentListForThisCourse = new ArrayList<>();
        for (ParticipantActivitate participantActivitate : studentList) {
            if(participantActivitate.getId() == currentCursId)
            {
                studentListForThisCourse.add(new Student(participantActivitate.getIdStudent()));
            }
        }

    }

    public void cautaStudent(ActionEvent actionEvent) {
        if(idActivitateSelectat == -1)
        {
            errorLabel.setText("Nu ati cautat o activitate!");
            return;
        }
        if(textFieldUsernameStudent.getText().isEmpty())
        {
            errorLabel.setText("Nu ati introdus un username!");
            return;
        }
        try{
            currentStudentId = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), textFieldUsernameStudent.getText());
        } catch (Exception e) {
            errorLabel.setText("Nu exista un student cu acest username!");
            return;
        }
        List<ParticipantActivitate> studentList = ParticipantActivitate.getTable();
        boolean found = false;
        for (ParticipantActivitate participantActivitate : studentList) {
            if(participantActivitate.getIdActivitate()== currentCursId && participantActivitate.getIdStudent() == currentStudentId)
            {
                found = true;
                break;
            }
        }
        if(!found)
        {
            errorLabel.setText("Acest student nu e inrolat in activitatea selectata!");
            return;
        }
        errorLabel.setText("Studentul a fost gasit!");
        idStudentSelectat = currentStudentId;


    }
}
