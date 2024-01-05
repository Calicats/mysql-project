package com.example.controllers;

import com.example.NoteStudent;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NoteStudentiController {

    @FXML
    private TableView<NoteStudent> gradesTable;

    @FXML
    private TableColumn<NoteStudent, Integer> idNotaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> notaColumn;

    @FXML
    private TableColumn<NoteStudent, Integer> idParticipantActivitateColumn;

    public void initialize() {
        // ... (other initialization code)


        //String[][] string = Query.getTableInfo(Connect.getConnection(), "note_studenti");

        // Create an ObservableList of NoteStudent objects
        //ObservableList<NoteStudent> data = FXCollections.observableArrayList();

        // Bind the columns to NoteStudent properties
        //idNotaColumn.setCellValueFactory(cellData -> cellData.getValue().idNotaProperty().asObject());
        //notaColumn.setCellValueFactory(cellData -> cellData.getValue().notaProperty().asObject());
        //idParticipantActivitateColumn.setCellValueFactory(cellData -> cellData.getValue().idParticipantActivitateProperty().asObject());

        // Set the TableView items with the created data
        //gradesTable.setItems(data);
    }
}
