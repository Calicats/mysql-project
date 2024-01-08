package com.example.sql;

import com.example.NoteStudent;

import java.sql.*;

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
        NoteStudent.getTable(noteStudent.getId_student()).forEach(noteStudent1 -> {
            if (noteStudent1.getId_participant_activitate() == noteStudent.getId_participant_activitate()) {
                try {
                    String update = "UPDATE notestudent SET nota = ? WHERE id_student = ? AND id_participant_activitate = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(update);
                    preparedStatement.setInt(1, noteStudent.getNota());
                    preparedStatement.setInt(2, noteStudent.getId_student());
                    preparedStatement.setInt(3, noteStudent.getId_participant_activitate());
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
            preparedStatement.setInt(3, noteStudent.getId_student());
            preparedStatement.setInt(4, noteStudent.getId_participant_activitate());
            return preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
