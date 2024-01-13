package com.example.controllers;

import com.example.ActivitateProfesor;
import com.example.NoteStudent;
import com.example.Student;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import com.example.sql.Update;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

// TODO Rewrite this class

public class CatalogProfesoriController {

    public ChoiceBox selectCurs;
    public ChoiceBox selectStudent;
    public Button saveGradeButton;
    public Button backButton;
    @FXML
    public TableView<ActivitateProfesor> cursuriTable;
    public TextField inputGrade;
    public Label errorLabel;
    public TableView<NoteStudent> studentList;

    HashMap<Integer, Integer> cursuriHash = new HashMap<Integer, Integer>(0);
    HashMap<Integer, Integer> studentiHash = new HashMap<Integer, Integer>(0);
    int currentCursId=0;
    int currentStudentId=0;
    int id;
    String username;
    String tableName;
    public void initialize(String username, String tableName){
        // get the current user's id
        int id=-1;
        try{
            id = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("panouProfesor.fxml", username, tableName, 1024, 768);
    }




    public void loadCursuri(ContextMenuEvent contextMenuEvent) {
        // create a list of cursuri for the current user
        List<ActivitateProfesor> cursuri = ActivitateProfesor.getTable();
        // using the id, get the cursuri for the current user
        ObservableList<ActivitateProfesor> cursuriProfesor = FXCollections.observableArrayList();
        // create the tables for the cursuri with strings
        ObservableList<String> cursuriProfesorId = FXCollections.observableArrayList();
        ObservableList<String> cursuriProfesorNume = FXCollections.observableArrayList();
        ObservableList<String> cursuriProfesorTip = FXCollections.observableArrayList();

        ObservableList<String> cursuriProfesorChoiceBox = FXCollections.observableArrayList();
        cursuriHash.clear();
        int i = 0;
//        for (ActivitateProfesor curs : cursuri) {
//
//            if (curs.getId_profesor() == id) {
//                cursuriProfesor.add(curs);
//                cursuriProfesorChoiceBox.add(curs.getId()+" "+curs.getNume());
//                cursuriProfesorId.add(String.valueOf(curs.getId()));
//                cursuriProfesorNume.add(curs.getNume());
//                cursuriProfesorTip.add(curs.getTipActivitate());
//                cursuriHash.put(i, curs.getId());
//                i++;
//            }
//        }
        // put the cursuri in the table
        cursuriTable.getColumns().clear();
        // Create the columns
        TableColumn<ActivitateProfesor, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Assuming you have a getId() method in ActivitateProfesor

        TableColumn<ActivitateProfesor, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume")); // Assuming you have a getNume() method

        TableColumn<ActivitateProfesor, String> tipActivitateColumn = new TableColumn<>("Tip Activitate");
        tipActivitateColumn.setCellValueFactory(new PropertyValueFactory<>("tipActivitate")); // Assuming you have a getTipActivitate() method

    // Add the columns to the TableView
        cursuriTable.getColumns().clear();
        cursuriTable.getColumns().addAll(idColumn, numeColumn, tipActivitateColumn);

    // Add data to the TableView
        cursuriTable.setItems(cursuriProfesor); // Assuming cursuriProfesor is an ObservableList<ActivitateProfesor> containing your data
        // put the cursuri in the choice box
        selectCurs.setItems(cursuriProfesorChoiceBox);

    }

    public void selectCurs(MouseEvent mouseEvent) {
        // get the curs id
        if(selectCurs.getItems().isEmpty())
            loadCursuri(null);
        if(selectCurs.getItems().isEmpty()){
            // there are no students for this curs
            errorLabel.setText("Nu exista cursuri pentru acest profesor!");
            return;
        }
        try {
            currentCursId = cursuriHash.getOrDefault(selectCurs.getSelectionModel().getSelectedIndex(), 0);
        } catch (Exception e) {
            selectCurs.getItems().clear();
            errorLabel.setText("Nu s-au putut incarca cursurile!");
            e.printStackTrace();
        }


    }

    public void selectStudent(MouseEvent mouseEvent) {
        // get the student id
        if(selectStudent.getItems().isEmpty())
            loadStudents(null);
        if(selectStudent.getItems().isEmpty()) {
            // there are no students for this curs
            errorLabel.setText("Nu exista studenti pentru acest curs!");
            return;
        }
        currentStudentId = studentiHash.get(selectStudent.getSelectionModel().getSelectedIndex());
    }

    public void loadStudents(ContextMenuEvent contextMenuEvent) {
        // get the students for the curs
        List<Student> studenti = Student.getTable();
        ObservableList<String> noteStudentiCurs = FXCollections.observableArrayList();
        studentiHash.clear();
        int i = 0;
        for (Student s : studenti) {
            if (s.getId() == currentCursId) {
                noteStudentiCurs.add(s.getNume());
                studentiHash.put(i, s.getId());
                i++;
            }
        }

        // put the students in the table
        if (noteStudentiCurs != null)
            selectStudent.setItems(noteStudentiCurs);
        currentStudentId = 0;

        // Create columns for TableView
        TableColumn<NoteStudent, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Assuming you have a getId() method in NoteStudent class

        TableColumn<NoteStudent, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume")); // You might need to adapt this part based on your Student class

        TableColumn<NoteStudent, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("nota")); // Assuming you have a getNota() method in NoteStudent class

        // Clear the columns before adding new ones
        studentList.getColumns().clear();
        studentList.getColumns().addAll(idColumn, numeColumn, gradeColumn);

        // Load data into the table based on the selected student
        selectStudent.setOnAction(e -> {
            // Assuming you have a method to get the selected student's ID from the selectStudent ComboBox
            int selectedStudentId = studentiHash.getOrDefault(currentStudentId, 0); // Implement this according to your logic


            // Get NoteStudent data based on selected student's ID
            List<NoteStudent> studentGrades = NoteStudent.getTable(selectedStudentId); // Implement this method to fetch NoteStudent data for the selected student

            // Populate TableView with the fetched NoteStudent data
            if (studentGrades != null) {
                ObservableList<NoteStudent> studentGradesList = FXCollections.observableArrayList(studentGrades);
                studentList.setItems(studentGradesList);
            }
        });
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

}
