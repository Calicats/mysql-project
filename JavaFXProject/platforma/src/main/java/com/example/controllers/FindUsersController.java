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

public class FindUsersController {
    @FXML
    private Button backButton;
    @FXML
    private Button findUsernameButton;
    @FXML
    private Button searchActivityButton;
    @FXML
    private Label searchActivityLabel;
    @FXML
    private TextField findUsernameField;
    @FXML
    private TextField searchActivityField;
    @FXML
    private ComboBox<String> selectTableComboBox;
    @FXML
    private Label foundUser;
    @FXML
    private Label foundActivity;
    @FXML
    private TableView<User> tableUsers;
    // tabela tableActivitati cu coloane hardcodate, ca tabela ii construita cu un JOIN
    @FXML
    private TableView<ActivitateProfesor> tableActivitati;
    @FXML
    private TableColumn<ActivitateProfesor, String> numeColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> usernameColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> tipActivitateColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> descriereColumn;
    @FXML
    private TableColumn<ActivitateProfesor, String> maxStudentColumn;
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

        tableUsers.setVisible(false);
        tableActivitati.setVisible(false);
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
        foundUser.setText("");
        foundActivity.setText("");

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
                }
                generateTableUsers(columnsUsers);
                populateTableUsers(connection, tableName);
            }
        }
        catch(Exception e)
        {
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
            foundUser.setText("Selecteaza o tabela!");
            clearAllFields();
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
                foundUser.setText("Introdu un utilizator!");
                clearAllFields();
                return;
            }

            String[][] usersInfo = Query.getUsersFromUsersPanel(connection, findUsernameString, tableName);
            String[] columns = Query.getColumnNames(connection, tableName);

            if(usersInfo == null || columns == null || !Query.userExistsInTable(connection, findUsernameString, tableName))
            {
                foundUser.setText("Utilizatorul nu exista!");
                clearAllFields();
                return;
            }

            ObservableList<User> listUsers = FXCollections.observableArrayList();

            generateTableUsers(columns);
            for(String[] row: usersInfo)
            {
                listUsers.add(rowToUserInfo(row, tableName));
            }
            tableUsers.setItems(listUsers);

            if(tableName.equals("Profesor"))
            {
                String[][] activitateInfo = Query.getActivityTableFromUsername(connection, findUsernameString);

                if(activitateInfo == null || !Query.existsProfesorInActivity(connection, findUsernameString))
                {
                    foundUser.setText("Utilizatorul cautat nu are nicio activitate!");
                    clearAllFields();
                    return;
                }

                showActivities(activitateInfo);
                foundUser.setText("");
                foundActivity.setText("");
            }
        }
        catch(Exception e)
        {
            foundUser.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Cauta activitatea dupa un input
     */

    public void onSearchActivity()
    {
        String activityString = searchActivityField.getText();
        if(activityString.isEmpty())
        {
            foundActivity.setText("Introdu o activitate!");
            clearAllFields();
            return;
        }

        try
        {
            Connection connection = Connect.getConnection();
            if(connection == null)
            {
                return;
            }

            String[][] activitateInfo = Query.getActivityTableFromActivity(connection, activityString);

            if(activitateInfo == null || !Query.existsActivity(connection, activityString))
            {
                foundActivity.setText("Activitatea cautata nu exista!");
                clearAllFields();
                return;
            }

            generateTableActivitati();
            setActivitateTableVisible(true);

            ObservableList<ActivitateProfesor> list = FXCollections.observableArrayList();
            for(String[] row: activitateInfo)
            {
                ActivitateProfesor activitateProfesor = rowToActivitateProfesor(row);
                list.add(activitateProfesor);
            }

            tableActivitati.setItems(list);
            foundUser.setText("");
            foundActivity.setText("");
        }
        catch(Exception e)
        {
            foundActivity.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Afiseaza activitatile printr-un ObservableList care e mai apoi atasat la tabela
     * @param activitateInfo tabela sub forma de String[][]
     */

    private void showActivities(String[][] activitateInfo)
    {
        setActivitateTableVisible(true);
        ObservableList<ActivitateProfesor> listActivitate = FXCollections.observableArrayList();
        for(String[] row: activitateInfo)
        {
            listActivitate.add(rowToActivitateProfesor(row));
        }
        foundUser.setText("");
        foundActivity.setText("");
        tableActivitati.setItems(listActivitate);
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
        foundUser.setText("");
        foundActivity.setText("");
    }

    private void setActivitateTableVisible(boolean expr)
    {
        tableActivitati.getItems().clear();
        tableActivitati.setVisible(expr);
        numeColumn.setVisible(expr);
        usernameColumn.setVisible(expr);
        tipActivitateColumn.setVisible(expr);
        descriereColumn.setVisible(expr);
        maxStudentColumn.setVisible(expr);
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
        numeColumn.setCellValueFactory(new PropertyValueFactory<ActivitateProfesor, String>("nume"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<ActivitateProfesor, String>("username"));
        tipActivitateColumn.setCellValueFactory(new PropertyValueFactory<ActivitateProfesor, String>("tipActivitate"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<ActivitateProfesor, String>("descriere"));
        maxStudentColumn.setCellValueFactory(new PropertyValueFactory<ActivitateProfesor, String>("nrMaximStudenti"));
        removeEllipses(numeColumn);
        removeEllipses(usernameColumn);
        removeEllipses(tipActivitateColumn);
        removeEllipses(descriereColumn);
        removeEllipses(maxStudentColumn);
    }

    /***
     * Face ca textul sa apara fara ...
     * @param column coloana
     */

    private void removeEllipses(TableColumn<ActivitateProfesor, String> column)
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
        ObservableList<ActivitateProfesor> list = FXCollections.observableArrayList();
        // tot continutul tabelului activitateprofesor
        String[][] allInfo = Query.getActivitateTableInfo(connection);

        if(allInfo == null)
        {
            System.out.println("Tabela vida!");
            return;
        }

        for(String[] row: allInfo)
        {
            ActivitateProfesor activitateProfesor = rowToActivitateProfesor(row);
            list.add(activitateProfesor);
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

    private ActivitateProfesor rowToActivitateProfesor(String[] row)
    {
        return new ActivitateProfesor(row[0], row[1], row[2], row[3], Integer.parseInt(row[4]));
    }

    private void clearAllFields()
    {
        findUsernameField.clear();
        searchActivityField.clear();
    }
}
