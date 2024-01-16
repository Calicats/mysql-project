package com.example.controllers;

import com.example.Activitate;
import com.example.NoteStudent;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

// TODO Rewrite this class?

public class NoteStudentiController {

    @FXML
    private TableView<NoteStudent> gradesTable;

    @FXML
    private TableColumn<NoteStudent, Integer> idNotaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> notaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> idParticipantActivitateColumn;

    private String username;

    public void initialize(String username){
        ObservableList<NoteStudent> list = FXCollections.observableArrayList();
        List<NoteStudent> noteStudenti = NoteStudent.getTable();
        // get the current user's id
        int id=-1;
        try{
            id = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), "student", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.username = username;
        for (NoteStudent noteStudent : noteStudenti) {
            if (noteStudent.getIdStudent() == id) {
                list.add(noteStudent);
            }
        }
        gradesTable.getColumns().clear();
        TableColumn<NoteStudent, Integer> idNotaColumn = new TableColumn<>("idNota");
        idNotaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<NoteStudent, Integer> notaColumn = new TableColumn<>("nota");
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        TableColumn<NoteStudent, Integer> idActivitateColumn = new TableColumn<>("idActivitate");
        idActivitateColumn.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        gradesTable.getColumns().addAll(idNotaColumn, notaColumn, idActivitateColumn);

       // TO DO: save the grade in the database
        gradesTable.setItems(list);
    }

    public void closeScreen(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("panouStudent.fxml");
    }

    public void onDownloadButton()
    {
        saveTableDataToCSV();
    }

    private void saveTableDataToCSV() {
        String defaultFileName = "gradesTable.csv";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.setInitialFileName(defaultFileName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                // Writing header
                writer.write("idNota,nota,idActivitate");
                writer.newLine();

                // Writing data
                for (NoteStudent noteStudent : gradesTable.getItems()) {
                    writer.write(String.format("%d,%d,%d",
                            noteStudent.getId(),
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

}
