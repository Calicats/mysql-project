package com.example.controllers;

import com.example.IntalnireGrupStudiu;
import com.example.MesajGrupStudiu;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Delete;
import com.example.sql.Insert;
import com.example.sql.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.rgb;

public class GrupuriDeStudiiController {

    @FXML
    private TableColumn<IntalnireGrupStudiu, String> attendanceColumn;

    @FXML
    private CheckBox confirmAttendance;

    @FXML
    private TableColumn<IntalnireGrupStudiu, String> dateColumn;

    @FXML
    private Label errorHandling;

    @FXML
    private TableColumn<IntalnireGrupStudiu, String> groupColumn;

    @FXML
    private ComboBox<String> selectGroupComboBox;

    @FXML
    private Label meetingDate;

    @FXML
    private TableColumn<MesajGrupStudiu, String> messageColumn;

    @FXML
    private TextField messageField;

    @FXML
    private TableColumn<MesajGrupStudiu, String> userColumn;

    @FXML
    private TableView<MesajGrupStudiu> tableMesaje;

    @FXML
    private TableView<IntalnireGrupStudiu> tableIntalniri;

    public void initialize()
    {
        userColumn.setCellValueFactory(new PropertyValueFactory<>("numeUtilizator"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("textMesaj"));

        groupColumn.setCellValueFactory(new PropertyValueFactory<>("numeGrup"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dataIntalnire"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("numarParticipanti"));

        removeEllipses(groupColumn);
        removeEllipses(dateColumn);
        removeEllipses(attendanceColumn);
    }

    public void goBack() throws IOException
    {
        if(tableName.equals("profesor"))
        {
            Main.main.changeScene("panouProfesor.fxml", username, tableName, 1024, 768);
        }
        else
        {
            Main.main.changeScene("panouStudent.fxml", username, tableName, 1024, 768);
        }
    }

    public void onCreateGroup() throws IOException
    {
        Main.main.changeScene("creazaModificaGrupDeStudii.fxml", username, tableName, 806, 502);
    }

    public void onModifyGroup()
    {
        clearErrors();

        String numeGrup = selectGroupComboBox.getValue();
        if(numeGrup == null)
        {
            errorHandling.setText("Selecteaza un grup!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            meetingDate.setText("Data intalnire: " + Query.getMeetingDate(connection, numeGrup) +
                    ", " + Query.getMeetingTime(connection, numeGrup));

            populateMessageTable(numeGrup);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onConfirmAttendance()
    {
        clearErrors();

        String numeGrup = selectGroupComboBox.getValue();
        if(numeGrup == null)
        {
            errorHandling.setText("Selecteaza un grup!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            boolean isTicked = confirmAttendance.isSelected();
            if(isTicked)
            {
                if(Query.existsInMeet(connection, numeGrup, username))
                {
                    errorHandling.setText("Esti deja prezent in intalnire!");
                    return;
                }
                Insert.addNewMemberInMeeting(connection, numeGrup, username);
                errorHandling.setTextFill(rgb(0, 255, 0));
                errorHandling.setText("Participi la intalnirea grupului " + numeGrup + "!");
            }
            else
            {
                if(!Query.existsInMeet(connection, numeGrup, username))
                {
                    errorHandling.setText("Nu esti prezent in intalnire!");
                    return;
                }
                int deleted = Delete.deleteMemberFromMeeting(connection, numeGrup, username);
                if(deleted > 0)
                {
                    errorHandling.setTextFill(rgb(0, 255, 0));
                    errorHandling.setText("Nu mai participi la intalnirea grupului " + numeGrup + "!");
                }
                else
                {
                    errorHandling.setText("Eroare la iesirea din intalnirea grupului " + numeGrup + "!");
                }
            }

            populateMeetings();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onSendButton()
    {
        clearErrors();

        String numeGrup = selectGroupComboBox.getValue();
        String message = messageField.getText();
        if(numeGrup == null)
        {
            errorHandling.setText("Alege un grup!");
            return;
        }

        try
        {
            populateMessageTable(numeGrup);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }

        if(message.isEmpty())
        {
            errorHandling.setText("Introdu un mesaj!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }
            Insert.addMessage(connection, numeGrup, username, message);
            populateMessageTable(numeGrup);
            messageField.clear();
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateGroupMenu()
    {
        String query = "SELECT gs.numeGrup FROM MembruGrupStudiu mgs " +
                "JOIN GrupStudiu gs ON mgs.idGrupStudiu = gs.idGrupStudiu " +
                "WHERE mgs.username = ?";

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> list = new ArrayList<>();
            while(resultSet.next())
            {
                String groupName = resultSet.getString("numeGrup");
                list.add(groupName);
            }

            updateGroupMenu(list);
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateGroupMenu(List<String> list)
    {
        selectGroupComboBox.getItems().clear();
        ObservableList<String> groups = FXCollections.observableList(list);
        selectGroupComboBox.setItems(groups);
    }

    private void populateMessageTable(String numeGrup) throws Exception
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        String[][] allInfo = Query.getMessages(connection, numeGrup);
        if(allInfo == null)
        {
            errorHandling.setText("Eroare la incarcare mesaje!");
            return;
        }
        ObservableList<MesajGrupStudiu> list = FXCollections.observableArrayList();
        for(String[] row: allInfo)
        {
            list.add(rowToMesaj(row));
        }
        tableMesaje.setItems(list);
    }

    private void populateMeetings()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }

        String[][] allInfo = Query.getGroupMeetings(connection, username);
        if(allInfo == null)
        {
            return;
        }
        ObservableList<IntalnireGrupStudiu> list = FXCollections.observableArrayList();
        for(String[] row: allInfo)
        {
            list.add(rowToMeet(row));
        }

        tableIntalniri.setItems(list);
    }

    private MesajGrupStudiu rowToMesaj(String[] row)
    {
        return new MesajGrupStudiu(row[0], row[1]);
    }

    private IntalnireGrupStudiu rowToMeet(String[] row)
    {
        java.sql.Date date;
        if(row[1].equals("No Meeting"))
        {
            date = Date.valueOf("1970-01-01");
            return new IntalnireGrupStudiu(row[0], date, Integer.parseInt(row[2]));
        }
        return new IntalnireGrupStudiu(row[0], Date.valueOf(row[1]), Integer.parseInt(row[2]));
    }

    private void clearErrors()
    {
        errorHandling.setTextFill(rgb(255, 0, 0));
        errorHandling.setText("");
    }

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
    }

    private String username;
    private String tableName;

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        populateGroupMenu();
        populateMeetings();
    }
}
