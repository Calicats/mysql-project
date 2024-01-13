package com.example.controllers;

import com.example.ActivitateProfesor;
import com.example.CalendarActivitate;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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

    public void closeScreen(ActionEvent actionEvent)  {
        try{
            Main.main.changeScene("panouProfesor.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadActivitatiSelectorActivitate(ContextMenuEvent contextMenuEvent) {
        // read all the activities from the database

//        List<ActivitateProfesor> activitati = ActivitateProfesor.getTable();
//
//        try {
//            int profId = Query.getIdByUsername(Connect.getConnection(), Main.main.username);
//            // and load them into the dropBoxSelecteazaActivitate
//            // but only the ones that the teacher is teaching
//            for(ActivitateProfesor activitate : activitati) {
//                if(activitate.getId_profesor() == profId)
//                    dropBoxSelecteazaActivitate.getItems().add(activitate);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void dropBoxSelecteazaActivitateSelectItem(MouseEvent mouseEvent) {
        // when an item is selected, load the data from the database
        // and fill the fields with it
        ActivitateProfesor activitate = (ActivitateProfesor) dropBoxSelecteazaActivitate.getItems().get(dropBoxSelecteazaActivitate.getSelectionModel().getSelectedIndex());
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

    }

    public void selecteazaDataFinalizare(ActionEvent actionEvent) {
    }

    public void dropBoxSelecteazaZiuaSelectItem(MouseEvent mouseEvent) {
    }

    public void dropBoxSelecteazaFrecventaSelectItem(MouseEvent mouseEvent) {
    }
}
