package com.example.sql;

import com.example.NoteStudent;

import java.sql.*;
import java.time.LocalDate;

public class Update {

    /***
     * Modifica informatia de la o tabela si valoarea noua setata
     * @param connection conexiunea la db_platforma
     * @param username username
     * @param tableName numele tabelei utilizatorului
     * @param columnName coloana pe care se doreste a efectua modificarea
     * @param entry informatia noua, sub forma de String
     * @return numarul de date modificate
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static int updateEntry(Connection connection, String username, String tableName, String columnName, String entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setString(2, username);
        return preparedStatement.executeUpdate();
    }

    /***
     * Modifica informatia de la o tabela si valoarea noua setata
     * @param connection conexiunea la db_platforma
     * @param username username
     * @param tableName numele tabelei utilizatorului
     * @param columnName coloana pe care se doreste a efectua modificarea
     * @param entry informatia noua, sub forma de int
     * @return numarul de date modificate
     * @throws Exception exceptia pe care o arunca metoda
     */

    public static int updateEntry(Connection connection, String username, String tableName, String columnName, int entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, entry);
        preparedStatement.setString(2, username);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInCurs(Connection connection, int id, String tableName, String columnName, String entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInCurs(Connection connection, int id, String tableName, String columnName, int entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, entry);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInActivitate(Connection connection, int id, String tableName, String columnName, String entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE idCurs = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInActivitate(Connection connection, int id, String tableName, String columnName, int entry) throws Exception
    {
        String update = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE idActivitate = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1, entry);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    @Deprecated
    public static int updateEntryInActivitateProfesor(Connection connection, String username, String columnName, String oldActivity, String oldDescription, String entry) throws Exception
    {
        String update = "UPDATE activitateprofesor SET " + columnName + " = ? WHERE tipActivitate = ? AND descriere = ? AND id_profesor = (SELECT idProfesor FROM Profesor WHERE username = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, entry);
        preparedStatement.setString(2, oldActivity);
        preparedStatement.setString(3, oldDescription);
        preparedStatement.setString(4, username);
        return preparedStatement.executeUpdate();
    }

    public static int updateEntryInNoteStudent(Connection connection, NoteStudent noteStudent)
    {
        /*
        this.id = id;
        this.nota = nota;
        this.id_student = id_student;
        this.id_participant_activitate = id_participant_activitate;
         */
        // will update the grade of the student if there is a grade for that student
        // check if there is a grade for that student
        NoteStudent.getTable(noteStudent.getIdStudent()).forEach(noteStudent1 -> {
            if (noteStudent1.getIdActivitate() == noteStudent.getIdActivitate()) {
                try {
                    String update = "UPDATE notestudent SET nota = ? WHERE id_student = ? AND id_participant_activitate = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(update);
                    preparedStatement.setInt(1, noteStudent.getNota());
                    preparedStatement.setInt(2, noteStudent.getIdStudent());
                    preparedStatement.setInt(3, noteStudent.getIdActivitate());
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // will insert a new grade if there is no grade for that student in that activity
            String insert = "INSERT INTO notestudent VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, noteStudent.getId());
            preparedStatement.setInt(2, noteStudent.getNota());
            preparedStatement.setInt(3, noteStudent.getIdStudent());
            preparedStatement.setInt(4, noteStudent.getIdActivitate());
            return preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static int updateNumeGrup(Connection connection, String numeGrup, int idGrupStudiu) throws Exception
    {
        String update = "UPDATE GrupStudiu SET numeGrup = ? WHERE idGrupStudiu = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, numeGrup);
        preparedStatement.setInt(2, idGrupStudiu);
        return preparedStatement.executeUpdate();
    }

    public static int updateDataIntalnire(Connection connection, int idIntalnire, LocalDate newDataIntalnire) {
        try {
            String query = "UPDATE IntalnireGrupStudiu SET dataIntalnire = ? WHERE idIntalnireGrupStudiu = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(newDataIntalnire));
                preparedStatement.setInt(2, idIntalnire);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return 0;
    }

    // Method to update the numarMinParticipanti field
    public static int updateNumarMinParticipanti(Connection connection,int idIntalnire, int newNumarMinParticipanti) {
        try {
            String query = "UPDATE IntalnireGrupStudiu SET numarMinParticipanti = ? WHERE idIntalnireGrupStudiu = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newNumarMinParticipanti);
                preparedStatement.setInt(2, idIntalnire);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return 0;
    }

    // Method to update the ora field
    public static int updateOra(Connection connection,int idIntalnire, int newOra) {
        try {
            String query = "UPDATE IntalnireGrupStudiu SET ora = ? WHERE idIntalnireGrupStudiu = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newOra);
                preparedStatement.setInt(2, idIntalnire);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return 0;
    }

    // Method to update the minut field
    public static int updateMinut(Connection connection,int idIntalnire, int newMinut) {
        try {
            String query = "UPDATE IntalnireGrupStudiu SET minut = ? WHERE idIntalnireGrupStudiu = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newMinut);
                preparedStatement.setInt(2, idIntalnire);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return 0;
    }
}
