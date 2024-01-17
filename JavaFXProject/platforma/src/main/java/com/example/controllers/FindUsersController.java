package com.example.controllers;

import com.example.*;
import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

// TODO: Remake this class because of the internal database changes
// SEARCH FOR throw new UnsupportedOperationException("You have to remake this method because of the internal database changes!");
public class FindUsersController {
    @FXML
    private Button backButton;
    @FXML
    private Button findUsernameButton;
    @FXML
    private TextField findUsernameField;
    @FXML
    private Button refreshButton;
    @FXML
    private Button searchActivityButton;
    @FXML
    private ComboBox<String> selectTableComboBox;
    @FXML
    private Label errorHandling;
    @FXML
    private Label activityNameLabel;
    @FXML
    private TextField activityNameField;
    @FXML
    private TableView<User> tableUsers;
    // tabela tableActivitati cu coloane hardcodate, ca tabela ii construita cu un JOIN
    @FXML
    private TableView<Activitate> tableActivitati;
    @FXML
    private TableColumn<Activitate, String> idColumn;
    @FXML
    private TableColumn<Activitate, String> numeColumn;
    @FXML
    private TableColumn<Activitate, String> usernameColumn;
    @FXML
    private TableColumn<Activitate, String> tipColumn;
    @FXML
    private TableColumn<Activitate, String> descriereColumn;
    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumn;
    @FXML
    private TableColumn<Activitate, String> procentColumn;

    @FXML
    private TableView<Activitate> tableActivitatiStudent;

    @FXML
    private TableColumn<Activitate, String> tipColumnStudent;

    @FXML
    private TableColumn<Activitate, String> usernameColumnStudent;

    @FXML
    private TableColumn<Activitate, String> procentColumnStudent;

    @FXML
    private TableColumn<Activitate, String> idColumnStudent;

    @FXML
    private TableColumn<Activitate, String> numeColumnStudent;

    @FXML
    private TableColumn<Activitate, String> nrMaximStudentiColumnStudent;

    @FXML
    private TableColumn<Activitate, String> descriereColumnStudent;

    private String username;
    private String tableName;

    /***
     * "Constructor", in ghilimele
     * @param username numele utilizatorului logat
     * @param tableName tabela de care apartine utilizatorul
     */

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        ObservableList<String> tablesList = FXCollections.observableArrayList();
        String[] tables = {"Superadministrator", "Administrator", "Profesor", "Student"};
        tablesList.addAll(Arrays.asList(tables).subList(0, 4));

        setActivitateTableVisible(false);

        tableUsers.setVisible(false);
        selectTableComboBox.setItems(tablesList);
    }

    /***
     * Intoarcere la panoul administrativ
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onBackButton() throws IOException
    {
        Main main = new Main();
        main.changeScene("panouAdministrativ.fxml", username, tableName, 1024, 768);
    }

    /***
     * Afiseaza tabela pe care ai selectat-o in combo box
     */

    public void onSelectTable()
    {
        tableUsers.setVisible(true);
        tableUsers.getItems().clear();
        tableUsers.getColumns().clear();
        findUsernameField.clear();
        errorHandling.setText("");

        setActivitateTableVisible(false);

        String tableName = selectTableComboBox.getValue();
        if(tableName == null)
        {
            return;
        }

        if(tableName.equals("Profesor"))
        {
            setActivitateTableVisible(true);
        }

        try
        {
            Connection connection = Connect.getConnection();
            // genereaza un tabel, fara id-uri
            // adica in tabel vezi toata informatia, dar fara id-uri
            String[] columnsUsers = Query.getColumnNames(connection, tableName);

            if(columnsUsers == null)
            {
                System.out.println("null");
            }
            else
            {
                if(tableName.equals("Profesor"))
                {
                    generateTableActivitati();
                    populateTableActivitati(connection);

                    generateTableActivitatiStudent();
                    populateTableActivitatiStudent();
                }
                generateTableUsers(columnsUsers);
                populateTableUsers(connection, tableName);
                errorHandling.setText("");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Afiseaza in tabele detaliile utilizatorului si activitatile profesorului cautat
     */

    public void onSearchUsername()
    {
        String tableName = selectTableComboBox.getValue();

        tableUsers.getItems().clear();
        tableUsers.getColumns().clear();

        if(tableName == null)
        {
            errorHandling.setText("Selecteaza o tabela!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            String findUsernameString = findUsernameField.getText();

            if(findUsernameString.isEmpty())
            {
                errorHandling.setText("Introdu un utilizator!");
                return;
            }

            if(!Query.userExistsInTable(connection, findUsernameString, tableName))
            {
                errorHandling.setText("Utilizatorul cautat nu exista!");
                return;
            }

            String[][] usersInfo = Query.getUsersFromUsersPanel(connection, findUsernameString, tableName);
            String[] columns = Query.getColumnNames(connection, tableName);

            if(usersInfo == null || columns == null)
            {
                errorHandling.setText("Utilizatorul nu exista!");
                return;
            }

            ObservableList<User> listUsers = FXCollections.observableArrayList();
            errorHandling.setText("");

            generateTableUsers(columns);
            for(String[] row: usersInfo)
            {
                listUsers.add(rowToUserInfo(row, tableName));
            }
            tableUsers.setItems(listUsers);

            if(tableName.equals("Profesor"))
            {
                String[][] activitateProfesor = Query.getActivitateTableInfoOnUser(connection, findUsernameString);
                if(activitateProfesor == null)
                {
                    errorHandling.setText("Utilizatorul cautat nu are nicio activitate!");
                    return;
                }

                generateTableActivitati();
                populateTableActivitati(connection, findUsernameString);
                errorHandling.setText("");
            }
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onSearchActivity()
    {
        String activityNameString = activityNameField.getText();

        if(activityNameString.isEmpty())
        {
            errorHandling.setText("Introdu o activitate!");
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            if(!Query.existsCurs(connection, activityNameString))
            {
                errorHandling.setText("Activitatea cautata nu exista!");
                return;
            }

            String[][] activitateProfesor = Query.getActivitateTableInfoOnActivity(connection, activityNameString);
            if(activitateProfesor == null)
            {
                return;
            }

            generateTableActivitati();
            populateTableActivitatiOnActivity(connection, activityNameString);

            generateTableActivitatiStudent();
            populateTableActivitatiStudentOnActivity(activityNameString);

            errorHandling.setText("");
        }
        catch(Exception e)
        {
            errorHandling.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Un buton simplu de refresh, sterge tot ceea ce a fost pe ecran + continutul
     */

    public void onRefreshButton()
    {
        selectTableComboBox.setValue(null);
        setActivitateTableVisible(false);
        tableUsers.setVisible(false);
        tableUsers.getItems().clear();
        tableUsers.getColumns().clear();
        findUsernameField.clear();
        errorHandling.setText("");
    }

    private void setActivitateTableVisible(boolean expr)
    {
        tableActivitati.getItems().clear();
        tableActivitati.setVisible(expr);
        tableActivitatiStudent.getItems().clear();
        tableActivitatiStudent.setVisible(expr);
        numeColumn.setVisible(expr);
        usernameColumn.setVisible(expr);
        tipColumn.setVisible(expr);
        descriereColumn.setVisible(expr);
        nrMaximStudentiColumn.setVisible(expr);

        activityNameLabel.setVisible(expr);
        activityNameField.setVisible(expr);
        searchActivityButton.setVisible(expr);
    }

    /***
     * Genereaza celulele tabelului de utilizatori
     * @param columns coloanele tabelului
     */

    private void generateTableUsers(String[] columns)
    {
        for(String columnName: columns)
        {
            TableColumn<User, String> column = new TableColumn<>(columnName);

            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(getUserPropertyValue(cellData.getValue(), columnName)));

            tableUsers.getColumns().add(column);
        }
    }

    /***
     * Genereaza celulele tabelului de activitati
     */

    private void generateTableActivitati()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiColumn.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        procentColumn.setCellValueFactory(new PropertyValueFactory<>("procentNota"));

        removeEllipses(idColumn);
        removeEllipses(usernameColumn);
        removeEllipses(numeColumn);
        removeEllipses(tipColumn);
        removeEllipses(descriereColumn);
        removeEllipses(nrMaximStudentiColumn);
        removeEllipses(procentColumn);
    }

    private void generateTableActivitatiStudent()
    {
        idColumnStudent.setCellValueFactory(new PropertyValueFactory<>("idActivitate"));
        usernameColumnStudent.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeColumnStudent.setCellValueFactory(new PropertyValueFactory<>("numeCurs"));
        tipColumnStudent.setCellValueFactory(new PropertyValueFactory<>("tip"));
        descriereColumnStudent.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        nrMaximStudentiColumnStudent.setCellValueFactory(new PropertyValueFactory<>("nrMaximStudenti"));
        procentColumnStudent.setCellValueFactory(new PropertyValueFactory<>("procentNota"));

        removeEllipses(idColumnStudent);
        removeEllipses(usernameColumnStudent);
        removeEllipses(numeColumnStudent);
        removeEllipses(tipColumnStudent);
        removeEllipses(descriereColumnStudent);
        removeEllipses(nrMaximStudentiColumnStudent);
        removeEllipses(procentColumnStudent);
    }

    private void populateTableActivitatiStudent()
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] allInfo = null;
        try
        {
            allInfo = Query.getStudentActivites(connection);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitateStudent(row);
            list.add(activitate);
        }

        tableActivitatiStudent.setItems(list);
    }

    private void populateTableActivitatiStudentOnActivity(String numeCurs)
    {
        Connection connection = Connect.getConnection();
        if(connection == null)
        {
            return;
        }
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        String[][] allInfo = null;
        try
        {
            allInfo = Query.getStudentActivitesOnActivity(connection, numeCurs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitateStudent(row);
            list.add(activitate);
        }

        tableActivitatiStudent.setItems(list);
    }

    private Activitate rowToActivitateStudent(String[] row)
    {
        int idActivitate = -1;
        int nrMaximStudenti = -1;
        int procentNota = -1;

        try
        {
            idActivitate = Integer.parseInt(row[0]);
            nrMaximStudenti = Integer.parseInt(row[5]);
            procentNota = Integer.parseInt(row[6]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new Activitate(idActivitate, row[1], row[2], row[3], row[4],nrMaximStudenti, procentNota);
    }

    /***
     * Face ca textul sa apara fara ...
     * @param column coloana
     */

    private void removeEllipses(TableColumn<?, String> column)
    {
        column.setMinWidth(200);
    }

    /***
     * Metoda ajutatoare la construirea celulelor tabelei
     * @param user utilizatorul
     * @param propertyName numele coloanei
     * @return atributul sub forma de String
     */

    private String getUserPropertyValue(User user, String propertyName) {
        if(user instanceof SuperAdministrator)
        {
            switch(propertyName)
            {
                case "idSuperAdmin":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Administrator)
        {
            switch(propertyName)
            {
                case "idAdmin":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Profesor)
        {
            switch(propertyName)
            {
                case "idProfesor":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "departament":
                    return ((Profesor) user).getDepartament();
                case "nrMinOre":
                    return String.valueOf(((Profesor) user).getNrMinOre());
                case "nrMaxOre":
                    return String.valueOf(((Profesor) user).getNrMaxOre());
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        else if(user instanceof Student)
        {
            switch(propertyName)
            {
                case "idStudent":
                    return String.valueOf(user.getId());
                case "nume":
                    return user.getNume();
                case "cnp":
                    return user.getCnp();
                case "anStudiu":
                    return String.valueOf(((Student) user).getAnStudiu());
                case "numarOre":
                    return String.valueOf(((Student) user).getNumarOre());
                case "adresa":
                    return user.getAdresa();
                case "email":
                    return user.getEmail();
                case "nrTelefon":
                    return user.getNrTelefon();
                case "parola":
                    return user.getParola();
                case "username":
                    return user.getUsername();
                case "idRol":
                    return String.valueOf(user.getIdRol());
                default:
                    return "";
            }
        }
        return null;
    }

    /***
     * Populeaza tabela de utilizatori
     * @param connection conexiunea la baza de date
     * @param tableName numele tabelei
     */

    private void populateTableUsers(Connection connection, String tableName)
    {
        ObservableList<User> list = FXCollections.observableArrayList();
        // toata informatia dintr-un tabel sub forma de matrice de String-uri
        String[][] userInfo = Query.getTableInfo(connection, tableName);

        if(userInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: userInfo)
        {
            User user = rowToUserInfo(row, tableName);
            list.add(user);
        }

        tableUsers.setItems(list);
    }

    /***
     * Populeaza tabela de activitati
     * @param connection conexiunea la db_platforma
     */

    private void populateTableActivitati(Connection connection)
    {
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        // tot continutul tabelului activitateprofesor
        String[][] allInfo = Query.getActivitateTableInfo(connection);

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitate(row);
            list.add(activitate);
        }

        tableActivitati.setItems(list);
    }

    private void populateTableActivitati(Connection connection, String username)
    {
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        // tot continutul tabelului activitateprofesor
        String[][] allInfo = Query.getActivitateTableInfoOnUser(connection, username);

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitate(row);
            list.add(activitate);
        }

        tableActivitati.setItems(list);
    }

    private void populateTableActivitatiOnActivity(Connection connection, String activity)
    {
        ObservableList<Activitate> list = FXCollections.observableArrayList();
        // tot continutul tabelului activitateprofesor
        String[][] allInfo = Query.getActivitateTableInfoOnActivity(connection, activity);

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            Activitate activitate = rowToActivitate(row);
            list.add(activitate);
        }

        tableActivitati.setItems(list);
    }

    /***
     * Converteste o intrare din tabela de utilizatori intr-o clasa
     * @param row informatia sub forma de Array de String-uri
     * @param tableName numele tabelei din care face parte
     * @return un nou utilizator
     */

    private User rowToUserInfo(String[] row, String tableName)
    {
        switch(tableName)
        {
            case "Superadministrator":
                return new SuperAdministrator(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        row[4], row[5], row[6], row[7], Integer.parseInt(row[8]));
            case "Administrator":
                return new Administrator(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        row[4], row[5], row[6], row[7], Integer.parseInt(row[8]));
            case "Profesor":
                return new Profesor(Integer.parseInt(row[0]), row[1], row[2], row[3],
                        Integer.parseInt(row[4]), Integer.parseInt(row[5]), row[6], row[7],
                        row[8], row[9], row[10], Integer.parseInt(row[11]));
            case "Student":
                return new Student(Integer.parseInt(row[0]), row[1], row[2], Integer.parseInt(row[3]),
                        Integer.parseInt(row[4]), row[5], row[6], row[7], row[8], row[9],
                        Integer.parseInt(row[10]));
            default:
                return null;
        }
    }

    /***
     * Converteste o intrare din tabela de activitati intr-o clasa
     * @param row informatia sub forma de Array de String-uri
     * @return o noua activitate
     */

    private Activitate rowToActivitate(String[] row)
    {
        int idActivitate = -1;
        int nrMaximStudenti = -1;
        int procentNota = -1;

        try
        {
            idActivitate = Integer.parseInt(row[0]);
            nrMaximStudenti = Integer.parseInt(row[5]);
            procentNota = Integer.parseInt(row[6]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new Activitate(idActivitate, row[1], row[2], row[3], row[4],nrMaximStudenti, procentNota);
    }
}
