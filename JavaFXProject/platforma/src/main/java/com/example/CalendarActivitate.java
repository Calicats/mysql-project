package com.example;

import com.example.sql.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivitate {
    public CalendarActivitate(int id) {
        Connection connection = Connect.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CalendarActivitate WHERE idCalendarActivitate = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            this.id = rs.getInt("idCalendarActivitate");
            dataDesfasurareActivitate = rs.getDate("dataDesfasurareActivitate");
            id_participant_activitate = rs.getInt("id_participant_activitate");
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

    @Override
    public String toString() {
        return "CalendarActivitate{" +
                "id=" + id +
                ", dataDesfasurareActivitate=" + dataDesfasurareActivitate +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

    private int id;
    private Date dataDesfasurareActivitate;
    private int id_participant_activitate;
}
