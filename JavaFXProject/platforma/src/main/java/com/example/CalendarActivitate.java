package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Deprecated
public class CalendarActivitate {
    private int id;
    private Date dataDesfasurareActivitate;
    private int id_participant_activitate;
    private Date data_incepere;
    private Date data_finalizare;
    private String ziua;
    private String frecventa;
    private int ora_incepere;
    private int minute_incepere;
    private int durata;

    public CalendarActivitate(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CalendarActivitate WHERE idCalendarActivitate = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("idCalendarActivitate");
                dataDesfasurareActivitate = rs.getDate("dataDesfasurareActivitate");
                id_participant_activitate = rs.getInt("id_participant_activitate");
                data_incepere = rs.getDate("data_incepere");
                data_finalizare = rs.getDate("data_finalizare");
                ziua = rs.getString("ziua");
                frecventa = rs.getString("frecventa");
                ora_incepere = rs.getInt("ora_incepere");
                minute_incepere = rs.getInt("minute_incepere");
                durata = rs.getInt("durata");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public CalendarActivitate(int id, Date dataDesfasurareActivitate, int id_participant_activitate) {
        this.id = id;
        this.dataDesfasurareActivitate = dataDesfasurareActivitate;
        this.id_participant_activitate = id_participant_activitate;
    }

    public static List<CalendarActivitate> getTable() {
        List<CalendarActivitate> list = new ArrayList<>();
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ParticipantActivitate");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idParticipantActivitate");
                Date dataDesfasurareActivitate = rs.getDate("dataDesfasurareActivitate");
                int id_participant_activitate = rs.getInt("id_participant_activitate");
                list.add(new CalendarActivitate(id, dataDesfasurareActivitate, id_participant_activitate));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public Date getDataDesfasurareActivitate() {
        return dataDesfasurareActivitate;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataDesfasurareActivitate(Date dataDesfasurareActivitate) {
        this.dataDesfasurareActivitate = dataDesfasurareActivitate;
    }

    public void setId_participant_activitate(int id_participant_activitate) {
        this.id_participant_activitate = id_participant_activitate;
    }

    public Date getData_incepere() {
        return data_incepere;
    }

    public void setData_incepere(Date data_incepere) {
        this.data_incepere = data_incepere;
    }

    public Date getData_finalizare() {
        return data_finalizare;
    }

    public void setData_finalizare(Date data_finalizare) {
        this.data_finalizare = data_finalizare;
    }

    public String getZiua() {
        return ziua;
    }

    public void setZiua(String ziua) {
        this.ziua = ziua;
    }

    public String getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(String frecventa) {
        this.frecventa = frecventa;
    }

    public int getOra_incepere() {
        return ora_incepere;
    }

    public void setOra_incepere(int ora_incepere) {
        this.ora_incepere = ora_incepere;
    }

    public int getMinute_incepere() {
        return minute_incepere;
    }

    public void setMinute_incepere(int minute_incepere) {
        this.minute_incepere = minute_incepere;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "CalendarActivitate{" +
                "id=" + id +
                ", dataDesfasurareActivitate=" + dataDesfasurareActivitate +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

}
