package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static javafx.scene.paint.Color.rgb;

public class CreazaModificaGrupDeStudii {
    @FXML
    private TextField addProfesorField;

    @FXML
    private TextField addStudentField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField minuteField;

    @FXML
    private TextField numeGrupField;

    @FXML
    private TextField oraField;

    @FXML
    private TextField removeProfesorField;

    @FXML
    private TextField removeStudentField;

    @FXML
    private Label errorHandling;

    @FXML
    private TextField numeCursField;

    @FXML
    private TextField idField;

    @FXML
    private TextField participantsField;

    @FXML
    private TableView<MembruGrupStudiu> tableStudent;

    @FXML
    private TableView<MembruGrupStudiu> tableProfesor;

    @FXML
    private TableColumn<MembruGrupStudiu, String> usernameStudentColumn;

    @FXML
    private TableColumn<MembruGrupStudiu, String> usernameProfesorColumn;

    @FXML
    private TableView<Sugestii> tableSugestii;

    @FXML
    private TableColumn<Sugestii, String> suggestedUserColumn;

    @FXML
    private TableColumn<Sugestii, String> suggestedRoleColumn;

    @FXML
    private TableView<GrupStudiu> tableGrupuri;

    @FXML
    private TableColumn<GrupStudiu, String> idGrupColumn;

    @FXML
    private TableColumn<GrupStudiu, String> numeGrupColumn;

    @FXML
    private TableColumn<GrupStudiu, String> idIntalnireColumn;

    @FXML
    private TableColumn<GrupStudiu, String> dataIntalnireColumn;

    public void onBackButton() throws IOException
    {
        Main.main.changeScene("grupuriDeStudii.fxml", username, tableName, 1200, 562);
    }

    public void initialize()
    {
        usernameStudentColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        suggestedUserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        suggestedRoleColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));

        idGrupColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeGrupColumn.setCellValueFactory(new PropertyValueFactory<>("numeGrup"));
        idIntalnireColumn.setCellValueFactory(new PropertyValueFactory<>("idIntalnire"));
        dataIntalnireColumn.setCellValueFactory(new PropertyValueFactory<>("dataIntalnire"));

        removeEllipses(usernameStudentColumn);
        removeEllipses(usernameProfesorColumn);
        removeEllipses(suggestedUserColumn);
        removeEllipses(suggestedRoleColumn);
        removeEllipses(idGrupColumn);
        removeEllipses(numeGrupColumn);
        removeEllipses(idIntalnireColumn);
        removeEllipses(dataIntalnireColumn);
    }

    public void onAddStudent()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String usernameStudent = addStudentField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        if(usernameStudent.isEmpty())
        {
            errorHandling.setText("Introdu un student!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!Query.existsUser(connection, usernameStudent))
            {
                errorHandling.setText("Utilizatorul nu exista!");
                return;
            }

            if(!Query.getRolFromUser(connection, usernameStudent).equals("Student"))
            {
                errorHandling.setText("Utilizatorul nu este student!");
                return;
            }

            if(!Query.existsStudentInParticipantForGroup(connection, usernameStudent, numeCurs))
            {
                errorHandling.setText("Utilizatorul nu este inscris in activitate!");
                return;
            }

            if(Query.existsUserInGroup(connection, usernameStudent, numeGrup))
            {
                errorHandling.setText("Utilizatorul este deja in grup!");
                return;
            }

            Insert.addNewMembruGrup(connection, numeGrup, usernameStudent);
            errorHandling.setTextFill(rgb(0, 255, 0));
            errorHandling.setText("Utilizator adaugat cu succes!");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onAddProfesor()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String usernameProfesor = addProfesorField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        if(usernameProfesor.isEmpty())
        {
            errorHandling.setText("Introdu un student!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!Query.existsUser(connection, usernameProfesor))
            {
                errorHandling.setText("Utilizatorul nu exista!");
                return;
            }

            if(!Query.getRolFromUser(connection, usernameProfesor).equals("Profesor"))
            {
                errorHandling.setText("Utilizatorul nu este profesor!");
                return;
            }

            if(!Query.existsProfesorInActivity(connection, usernameProfesor, numeCurs))
            {
                errorHandling.setText("Utilizatorul nu este inscris in activitate!");
                return;
            }

            if(Query.existsUserInGroup(connection, usernameProfesor, numeGrup))
            {
                errorHandling.setText("Utilizatorul este deja in grup!");
                return;
            }

            Insert.addNewMembruGrup(connection, numeGrup, usernameProfesor);
            errorHandling.setTextFill(rgb(0, 255, 0));
            errorHandling.setText("Utilizator adaugat cu succes!");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onRemoveStudent()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String usernameStudent = removeStudentField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        if(usernameStudent.isEmpty())
        {
            errorHandling.setText("Introdu un student!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!Query.existsUser(connection, usernameStudent))
            {
                errorHandling.setText("Utilizatorul nu exista!");
                return;
            }

            if(!Query.getRolFromUser(connection, usernameStudent).equals("Student"))
            {
                errorHandling.setText("Utilizatorul nu este student!");
                return;
            }

            if(!Query.existsStudentInParticipantForGroup(connection, usernameStudent, numeCurs))
            {
                errorHandling.setText("Utilizatorul nu este inscris in activitate!");
                return;
            }

            if(!Query.existsUserInGroup(connection, usernameStudent, numeGrup))
            {
                errorHandling.setText("Utilizatorul nu este in grup!");
                return;
            }

            int deleted = Delete.deleteMembruGrup(connection, usernameStudent, numeGrup);
            if(deleted > 0)
            {
                errorHandling.setTextFill(rgb(0, 255, 0));
                errorHandling.setText("Utilizator sters cu succes!");
            }
            else
            {
                errorHandling.setText("A esuat stergerea utilizatorului!");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onRemoveProfesor()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String usernameProfesor = removeProfesorField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        if(usernameProfesor.isEmpty())
        {
            errorHandling.setText("Introdu un student!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(Query.getRolFromUser(connection, username).equals("Student"))
            {
                errorHandling.setText("Nu ai persmisiunea sa stergi profesori!");
                return;
            }

            if(!Query.existsUser(connection, usernameProfesor))
            {
                errorHandling.setText("Utilizatorul nu exista!");
                return;
            }

            if(!Query.getRolFromUser(connection, usernameProfesor).equals("Profesor"))
            {
                errorHandling.setText("Utilizatorul nu este profesor!");
                return;
            }

            if(!Query.existsProfesorInActivity(connection, usernameProfesor, numeCurs))
            {
                errorHandling.setText("Utilizatorul nu este inscris in activitate!");
                return;
            }

            if(!Query.existsUserInGroup(connection, usernameProfesor, numeGrup))
            {
                errorHandling.setText("Utilizatorul nu este in grup!");
                return;
            }

            int deleted = Delete.deleteMembruGrup(connection, usernameProfesor, numeGrup);
            if(deleted > 0)
            {
                errorHandling.setTextFill(rgb(0, 255, 0));
                errorHandling.setText("Utilizator sters cu succes!");
            }
            else
            {
                errorHandling.setText("A esuat stergerea utilizatorului!");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onAddMeeting()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String oraString = oraField.getText();
        String minuteString = minuteField.getText();
        String participantiString = participantsField.getText();
        LocalDate selectedDate = datePicker.getValue();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Selecteaza un curs!");
            return;
        }

        if(oraString.isEmpty())
        {
            errorHandling.setText("Introdu ora!");
            return;
        }

        if(minuteString.isEmpty())
        {
            errorHandling.setText("Introdu minutele!");
            return;
        }

        if(participantiString.isEmpty())
        {
            errorHandling.setText("Introdu numarul de participanti!");
            return;
        }

        if(selectedDate == null)
        {
            errorHandling.setText("Alege o data!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            int ora = Integer.parseInt(oraString);
            int minute = Integer.parseInt(minuteString);
            int participanti = Integer.parseInt(participantiString);
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!(ora >= 0 && ora <= 23))
            {
                errorHandling.setText("Ora invalida!");
                return;
            }

            if(!(minute >= 0 && minute <= 59))
            {
                errorHandling.setText("Minute invalide!");
                return;
            }

            if(participanti <= 0)
            {
                errorHandling.setText("Numar participanti invalid!!");
                return;
            }

            if(Query.existsMeeting(connection, numeGrup))
            {
                errorHandling.setText("Exista deja o intalnire planificata!");
                return;
            }

            Insert.addNewMeeting(connection, numeGrup, selectedDate, participanti, ora, minute);
            errorHandling.setTextFill(rgb(0,255, 0));
            errorHandling.setText("Adaugare cu succes!");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onModifyMeeting()
    {
        clearErrors();

        String idString = idField.getText();
        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();

        if(idString.isEmpty())
        {
            errorHandling.setText("Introdu un id la meeting!");
            return;
        }

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un nume de curs!");
            return;
        }

        String oraString = oraField.getText();
        String minuteString = minuteField.getText();
        String participantiString = participantsField.getText();
        LocalDate selectedDate = datePicker.getValue();

        try
        {
            Connection connection = Connect.getConnection();
            int id = Integer.parseInt(idString);

            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(id <= 0)
            {
                errorHandling.setText("Id invalid!");
                return;
            }

            if(!oraString.isEmpty() && !(Integer.parseInt(oraString) >= 0 && Integer.parseInt(oraString) <= 23))
            {
                errorHandling.setText("Ora invalida!");
                return;
            }

            if(!minuteString.isEmpty() && !(Integer.parseInt(minuteString) >= 0 && Integer.parseInt(minuteString) <= 59))
            {
                errorHandling.setText("Minute invalide!");
                return;
            }

            if(!participantiString.isEmpty() && Integer.parseInt(participantiString) <= 0)
            {
                errorHandling.setText("Numar participanti invalid!!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!Query.existsMeeting(connection, numeGrup))
            {
                errorHandling.setText("Nu exista o intalnire planificata!");
                return;
            }

            if(selectedDate != null)
            {
                int updated = Update.updateDataIntalnire(connection, id, selectedDate);
                if(updated > 0)
                {
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Modificare cu succes!");
                }
                else
                {
                    errorHandling.setText("Modificarea a esuat!");
                }
            }

            if(!oraString.isEmpty())
            {
                int ora = Integer.parseInt(oraString);
                int updated = Update.updateOra(connection, id, ora);
                if(updated > 0)
                {
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Modificare cu succes!");
                }
                else
                {
                    errorHandling.setText("Modificarea a esuat!");
                }
            }

            if(!minuteString.isEmpty())
            {
                int minute = Integer.parseInt(minuteString);
                int updated = Update.updateMinut(connection, id, minute);
                if(updated > 0)
                {
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Modificare cu succes!");
                }
                else
                {
                    errorHandling.setText("Modificarea a esuat!");
                }
            }

            if(!participantiString.isEmpty())
            {
                int participanti = Integer.parseInt(participantiString);
                int updated = Update.updateNumarMinParticipanti(connection, id, participanti);
                if(updated > 0)
                {
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Modificare cu succes!");
                }
                else
                {
                    errorHandling.setText("Modificarea a esuat!");
                }
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onDeleteMeeting()
    {
        clearErrors();

        String idString = idField.getText();
        String numeCurs = numeCursField.getText();
        String numeGrup = numeGrupField.getText();

        if(idString.isEmpty())
        {
            errorHandling.setText("Introdu un id de intalnire!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un nume de curs!");
            return;
        }

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            if(!Query.existsMeeting(connection, numeGrup))
            {
                errorHandling.setText("Nu exista o intalnire planificata!");
                return;
            }

            int id = Integer.parseInt(idString);
            if(id <= 0)
            {
                errorHandling.setText("Id invalid!");
                return;
            }

            int deleted = Delete.deleteMeeting(connection, id);
            if(deleted > 0)
            {
                errorHandling.setTextFill(rgb(0, 255, 0));
                errorHandling.setText("Stergerea intalnirii cu succes!");
            }
            else
            {
                errorHandling.setText("Stergerea intalnirii a esuat!");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onCreateButton()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            Insert.addNewGrupStudiu(connection, numeGrup);
            Insert.addNewMembruGrup(connection, numeGrup, username);
            errorHandling.setTextFill(rgb(0,255, 0));
            errorHandling.setText("Adaugare cu succes!");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onModifyButton()
    {
        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        String idGrup = idField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un nume de curs!");
            return;
        }

        if(idGrup.isEmpty())
        {
            errorHandling.setText("Introdu un id de grup!!");
            return;
        }


        try
        {
            int idGrupStudiu = Integer.parseInt(idGrup);
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            int updated = Update.updateNumeGrup(connection, numeGrup, idGrupStudiu);
            if(updated > 0)
            {
                errorHandling.setTextFill(rgb(0,255, 0));
                errorHandling.setText("Modificare cu succes!");
            }
            else
            {
                errorHandling.setText("Modificarea a esuat!");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onDeleteButton()
    {
        clearErrors();

        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();
        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un curs!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul nu exista!");
                return;
            }

            int deleted = Delete.deleteGrup(connection, numeGrup);
            if(deleted > 0)
            {
                errorHandling.setTextFill(rgb(0,255, 0));
                errorHandling.setText("Stergere cu succes!");
            }
            else
            {
                errorHandling.setText("Stergerea a esuat!");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onShowParticipants()
    {
        clearErrors();
        String numeGrup = numeGrupField.getText();
        String numeCurs = numeCursField.getText();

        if(numeGrup.isEmpty())
        {
            errorHandling.setText("Introdu un nume de grup!");
            return;
        }

        if(numeCurs.isEmpty())
        {
            errorHandling.setText("Introdu un nume de curs!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsGrup(connection, numeGrup))
            {
                errorHandling.setText("Grupul cautat nu exista!");
                return;
            }

            populateMembruStudent();
            populateMembruProfesor();
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onShowSuggestions()
    {
        clearErrors();
        String numeCurs = numeCursField.getText();

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean existsInCourse = false;
            if(tableName.equals("profesor"))
            {
                existsInCourse = Query.existsProfesorInActivity(connection, username, numeCurs);
            }
            else if(tableName.equals("student"))
            {
                existsInCourse = Query.existsStudentInParticipantForGroup(connection, username, numeCurs);
            }

            if(!existsInCourse)
            {
                errorHandling.setText("Nu esti inscris in cursul respectiv!");
                return;
            }

            if(!Query.existsCursLowerCase(connection, numeCurs))
            {
                errorHandling.setText("Cursul cautat nu exista!");
                return;
            }

            populateSuggestions(numeCurs);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearErrors()
    {
        errorHandling.setTextFill(rgb(255, 0, 0));
        errorHandling.setText("");
    }

    private void populateMembruStudent()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        ObservableList<MembruGrupStudiu> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getMembersStudent(connection);
        if(allInfo == null)
        {
            return;
        }

        for(String[] row: allInfo)
        {
            list.add(rowToMembru(row));
        }

        tableStudent.setItems(list);
    }

    private void populateMembruProfesor()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        ObservableList<MembruGrupStudiu> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getMembersProfesor(connection);
        if(allInfo == null)
        {
            return;
        }

        for(String[] row: allInfo)
        {
            list.add(rowToMembru(row));
        }

        tableProfesor.setItems(list);
    }

    private void populateSuggestions(String numeCurs)
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Sugestii> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getSuggestions(connection, numeCurs);
        if(allInfo == null)
        {
            errorHandling.setText("Eroare la afisare sugestii!");
            return;
        }

        for(String[] row: allInfo)
        {
            list.add(rowToSugestii(row));
        }

        tableSugestii.setItems(list);
    }

    private void populateGrupuri()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<GrupStudiu> list = FXCollections.observableArrayList();
        String[][] allInfo = Query.getGrup(connection);
        if(allInfo == null)
        {
            errorHandling.setText("Eroare la afisare sugestii!");
            return;
        }

        for(String[] row: allInfo)
        {
            list.add(rowToGrup(row));
        }
        tableGrupuri.setItems(list);
    }

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
    }

    private MembruGrupStudiu rowToMembru(String[] row)
    {
        return new MembruGrupStudiu(row[0]);
    }

    private Sugestii rowToSugestii(String[] row)
    {
        return new Sugestii(row[0], row[1]);
    }

    private GrupStudiu rowToGrup(String[] row)
    {
        return new GrupStudiu(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2]), Date.valueOf(row[3]));
    }

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        populateGrupuri();
    }

    private String username;
    private String tableName;
}
