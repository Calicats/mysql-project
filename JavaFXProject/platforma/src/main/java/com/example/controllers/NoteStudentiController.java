package com.example.controllers;

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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class NoteStudentiController {

    @FXML
    private TableView<NoteStudent> gradesTable;

    @FXML
    private TableColumn<NoteStudent, Integer> idNotaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> notaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> idParticipantActivitateColumn;

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
        for (NoteStudent noteStudent : noteStudenti) {
            if (noteStudent.getId_student() == id) {
                list.add(noteStudent);
            }
        }
    }

    public void closeScreen(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("panouStudent.fxml", "student", "student", 1024, 768);
    }
}
