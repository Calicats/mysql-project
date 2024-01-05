package com.example;

import java.util.Objects;

public class CalendarActivitate {
    private int idCalendarActivitate;
    private String dataDesfasurareActivitate;
    private int id_participant_activitate;

    public CalendarActivitate(int idCalendarActivitate, String dataDesfasurareActivitate, int id_participant_activitate) {
        this.idCalendarActivitate = idCalendarActivitate;
        this.dataDesfasurareActivitate = dataDesfasurareActivitate;
        this.id_participant_activitate = id_participant_activitate;
    }

    public int getIdCalendarActivitate() {
        return idCalendarActivitate;
    }

    public void setIdCalendarActivitate(int idCalendarActivitate) {
        this.idCalendarActivitate = idCalendarActivitate;
    }

    public String getDataDesfasurareActivitate() {
        return dataDesfasurareActivitate;
    }

    public void setDataDesfasurareActivitate(String dataDesfasurareActivitate) {
        this.dataDesfasurareActivitate = dataDesfasurareActivitate;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    public void setId_participant_activitate(int id_participant_activitate) {
        this.id_participant_activitate = id_participant_activitate;
    }

    @Override
    public String toString() {
        return "CalendarActivitate{" +
                "idCalendarActivitate=" + idCalendarActivitate +
                ", dataDesfasurareActivitate='" + dataDesfasurareActivitate + '\'' +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarActivitate that)) return false;
        return idCalendarActivitate == that.idCalendarActivitate && id_participant_activitate == that.id_participant_activitate && Objects.equals(dataDesfasurareActivitate, that.dataDesfasurareActivitate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCalendarActivitate, dataDesfasurareActivitate, id_participant_activitate);
    }
}

