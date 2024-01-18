package com.example;

import com.example.controllers.ProgramareCursuriProfesorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitatePeZi {
    Activitate activitate;
    ProgramareActivitate programareActivitate;

    // data to show on the table
    public String name;
    public int startHour;
    public int endHour;
    public int startMinutes;
    public int endMinutes;

    public String tip;

    public LocalDate startDate;
    public LocalDate endDate;

    public ActivitatePeZi(Activitate activitate, ProgramareActivitate programareActivitate) {
        this.activitate = activitate;
        this.programareActivitate = programareActivitate;
        name = new Curs(activitate.getIdCurs()).getNumeCurs();
        startDate = programareActivitate.getDataIncepere().toLocalDate();
        endDate = programareActivitate.getDataFinalizare().toLocalDate();
        startHour = programareActivitate.getOra();
        startMinutes = programareActivitate.getMinut();
        endHour = programareActivitate.getOra();
        endMinutes = programareActivitate.getMinut();
        tip = activitate.getTip();
    }

    public static ObservableList<ActivitatePeZi> getActivitateZi(Profesor profesor) {
        List<ParticipantActivitate> participantActivitates = new ArrayList<>();
        for(ParticipantActivitate p : ParticipantActivitate.getTable()){
            if(p.getIdProfesor() == profesor.getId()){
                participantActivitates.add(p);
            }
        }
        ObservableList<ActivitatePeZi> activitatePeZi = FXCollections.observableArrayList();
        for(ParticipantActivitate participantActivitate : ParticipantActivitate.getTable()) {
            for (ProgramareActivitate programareActivitate : ProgramareActivitate.getTable()) {
                if((ProgramareCursuriProfesorController.ZI.valueOf(programareActivitate.getZi()).ordinal()+1) != java.time.LocalDate.now().getDayOfWeek().getValue()){
                    continue;
                }
                if(programareActivitate.getIdActivitate() == participantActivitate.getIdActivitate()){
                    // check for frecventa
                    boolean ok = false;
                    switch (programareActivitate.getFrecventa()){
                        case "Bilunar":
                            ok=getWeekSinceStart(programareActivitate)%2==0;
                            break;
                        case "Lunar":
                            ok=getWeekSinceStart(programareActivitate)%4==0;
                            break;
                        default:
                            ok=true;
                            break;
                    }
                    if(ok) {
                        activitatePeZi.add(new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate));
                        System.out.println("ActivitatePeZi: " + new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate).toString());
                    }
                    break;
                }
                else{
                    System.out.println("programareActivitate.getIdActivitate()="+programareActivitate.getIdActivitate());
                    System.out.println("p.getIdActivitate()="+participantActivitate.getIdActivitate());
                    System.out.println("Nope: "+ new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate).toString());
                    System.out.println("Day: "+ (ProgramareCursuriProfesorController.ZI.valueOf(programareActivitate.getZi()).ordinal()+1) +" == "+ java.time.LocalDate.now().getDayOfWeek().getValue());
                    System.out.println("isBefore: "+ programareActivitate.getDataFinalizare().toLocalDate().isBefore(java.time.LocalDate.now()));

                }
            }
        }
        return activitatePeZi;
    }

    public Activitate getActivitate() {
        return activitate;
    }

    public ProgramareActivitate getProgramareActivitate() {
        return programareActivitate;
    }

    public String getName() {
        return name;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public String getTip() {
        return tip;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public static int getWeekSinceStart(ProgramareActivitate programareActivitate){
        // count weeks since start
        LocalDate localDate = programareActivitate.getDataIncepere().toLocalDate();
        int currentWeeks=0;
        if(localDate.isAfter(java.time.LocalDate.now())){
            localDate.plusDays(7);
            currentWeeks++;
        }
        return currentWeeks;
    }


    public static ObservableList<ActivitatePeZi> getActivitateZi(Student student){
        List<ParticipantActivitate> participantActivitates = new ArrayList<>();
        for(ParticipantActivitate p : ParticipantActivitate.getTable()){
            if(p.getIdStudent() == student.getId()){
                participantActivitates.add(p);
            }
        }
        ObservableList<ActivitatePeZi> activitatePeZi = FXCollections.observableArrayList();
        for(ParticipantActivitate participantActivitate : ParticipantActivitate.getTable()) {
            for (ProgramareActivitate programareActivitate : ProgramareActivitate.getTable()) {
                if((ProgramareCursuriProfesorController.ZI.valueOf(programareActivitate.getZi()).ordinal()+1) != java.time.LocalDate.now().getDayOfWeek().getValue()){
                    continue;
                }
                if(programareActivitate.getIdActivitate() == participantActivitate.getIdActivitate()){
                    // check for frecventa
                    boolean ok = false;
                    switch (programareActivitate.getFrecventa()){
                        case "Bilunar":
                            ok=getWeekSinceStart(programareActivitate)%2==0;
                            break;
                        case "Lunar":
                            ok=getWeekSinceStart(programareActivitate)%4==0;
                            break;
                        default:
                            ok=true;
                            break;
                    }
                    if(ok) {
                        activitatePeZi.add(new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate));
                        System.out.println("ActivitatePeZi: " + new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate).toString());
                    }
                    break;
                }
                else{
                    System.out.println("programareActivitate.getIdActivitate()="+programareActivitate.getIdActivitate());
                    System.out.println("p.getIdActivitate()="+participantActivitate.getIdActivitate());
                    System.out.println("Nope: "+ new ActivitatePeZi(new Activitate(programareActivitate.getIdActivitate()), programareActivitate).toString());
                    System.out.println("Day: "+ (ProgramareCursuriProfesorController.ZI.valueOf(programareActivitate.getZi()).ordinal()+1) +" == "+ java.time.LocalDate.now().getDayOfWeek().getValue());
                    System.out.println("isBefore: "+ programareActivitate.getDataFinalizare().toLocalDate().isBefore(java.time.LocalDate.now()));

                }
            }
        }
        return activitatePeZi;

    }

    @Override
    public String toString() {
        return "ActivitatePeZi{" +
                "activitate=" + activitate +
                ", programareActivitate=" + programareActivitate +
                ", name='" + name + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", startMinutes=" + startMinutes +
                ", endMinutes=" + endMinutes +
                ", tip='" + tip + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
