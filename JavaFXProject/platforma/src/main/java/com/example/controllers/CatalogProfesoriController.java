package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import com.example.sql.Update;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
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
            id = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "profesor", username);
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

        TableColumn<NoteStudent, Integer> idNotaNoteColumn = new TableColumn<>("idNota");
        idNotaNoteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<NoteStudent, Integer> notaNoteColumn = new TableColumn<>("nota");
        notaNoteColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        TableColumn<NoteStudent, String> studentNoteColumn = new TableColumn<>("student");
        studentNoteColumn.setCellValueFactory(new PropertyValueFactory<>("usernameStudent"));
        TableColumn<NoteStudent, Integer> idActivitateNoteColumn = new TableColumn<>("idActivitate");
        idActivitateNoteColumn.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));

        // add the columns to the table
        cursuriTable.getColumns().add(idActivitateColumn);
        cursuriTable.getColumns().add(numeCursColumn);
        cursuriTable.getColumns().add(tipColumn);
        cursuriTable.getColumns().add(descriereColumn);
        cursuriTable.getColumns().add(nrMaximStudentiColumn);
        cursuriTable.getColumns().add(procentNotaColumn);

        studentList.getColumns().add(idNotaNoteColumn);
        studentList.getColumns().add(notaNoteColumn);
        studentList.getColumns().add(studentNoteColumn);
        studentList.getColumns().add(idActivitateNoteColumn);

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

        populateCatalog();
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
            currentStudentId = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "student", textFieldUsernameStudent.getText());
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

    public void onDownloadCatalog()
    {
        if(textFieldIdActivitate.getText().isEmpty())
        {
            errorLabel.setText("Introdu un id de activitate!");
            return;
        }

        saveTableDataToCSV();
    }

    private void saveTableDataToCSV() {
        String defaultFileName = "catalog.csv";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                // Writing header
                writer.write("idNota,username,nota,idActivitate");
                writer.newLine();

                // Writing data
                for (NoteStudent noteStudent : studentList.getItems()) {
                    writer.write(String.format("%d,%s,%d,%d",
                            noteStudent.getId(),
                            noteStudent.getUsernameStudent(),
                            noteStudent.getNota(),
                            noteStudent.getIdActivitate()));
                    writer.newLine();
                }

                System.out.println("CSV file saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateCatalog()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        String[][] allInfo = Query.getCatalogOnIdActivitate(connection, idActivitateSelectat);
        if(allInfo == null)
        {
            errorLabel.setText("Eroare la deschiderea catalogului!");
            return;
        }

        ObservableList<NoteStudent> list = FXCollections.observableArrayList();
        for(String[] row: allInfo)
        {
            list.add(new NoteStudent(Integer.parseInt(row[0]), Integer.parseInt(row[1]), row[2], Integer.parseInt(row[3])));
        }

        studentList.setItems(list);
    }
}
