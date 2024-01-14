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
            id = Query.getIdByUsername(Objects.requireNonNull(Connect.getConnection()), username);
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
        idNotaColumn.setCellValueFactory(new PropertyValueFactory<>("idNota"));
        TableColumn<NoteStudent, Integer> notaColumn = new TableColumn<>("nota");
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        TableColumn<NoteStudent, Integer> idParticipantActivitateColumn = new TableColumn<>("idParticipantActivitate");
        idParticipantActivitateColumn.setCellValueFactory(new PropertyValueFactory<>("idParticipantActivitate"));
        gradesTable.getColumns().addAll(idNotaColumn, notaColumn, idParticipantActivitateColumn);

       // TO DO: save the grade in the database
    }

    public void closeScreen(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("panouStudent.fxml");
    }
}
