package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;

public class ControlPanelController {
    @FXML
    private Button personalDataButton;
    @FXML
    private Button manageUsersButton;
    @FXML
    private Button findUsersButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label greetUser;
    @FXML
    private Label nume;
    @FXML
    private Label cnp;
    @FXML
    private Label adresa;
    @FXML
    private Label email;
    @FXML
    private Label nrTelefon;
    private String username;
    private String tableName;
    private boolean show = true;

    /***
     * "Constructor", in ghilimele
     * @param username numele utilizatorului logat
     * @param tableName tabela de care apartine utilizatorul
     */

    public void initUser(String username, String tableName)
    {
        this.username = username;
        this.tableName = tableName;

        greetUser.setText("Bine ai venit, " + this.username + "!");
    }

    /***
     * Intoarcere la scena de logare
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onUserLogout() throws IOException
    {
        Main main = new Main();
        System.out.println("Bye, " + username);

        main.changeScene("logare.fxml", username, tableName, 600, 400);
    }

    /***
     * Modifica scena la gestionare
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onManageUsers() throws IOException
    {
        Main main = new Main();
        main.changeScene("gestionareUtilizatori.fxml", username, tableName, 1024, 768);
    }

    /***
     * Modifica scena la cautare
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onFindUsers() throws IOException
    {
        Main main = new Main();
        main.changeScene("cautareUtilizatori.fxml", username, tableName, 1024, 768);
    }

    /***
     * Daca apesi pe butonul de Date Personale, iti afiseaza datele personale, cu toggle
     */

    public void onShowPersonalData()
    {
        try
        {
            if(show)
            {
                Connection connection = Connect.getConnection();
                String numeString = Query.getSingleInfo(connection, tableName, username, "nume");
                String cnpString= Query.getSingleInfo(connection, tableName, username, "CNP");
                String adresaString = Query.getSingleInfo(connection, tableName, username, "adresa");
                String emailString = Query.getSingleInfo(connection, tableName, username, "email");
                String nrTelefonString = Query.getSingleInfo(connection, tableName, username, "nrTelefon");

                nume.setVisible(true);
                cnp.setVisible(true);
                adresa.setVisible(true);
                email.setVisible(true);
                nrTelefon.setVisible(true);

                greetUser.setText("Bine ai venit, " + this.username + "!");
                nume.setText("Nume: " + numeString);
                cnp.setText("CNP: " + cnpString);
                adresa.setText("Adresa: " + adresaString);
                email.setText("Email: " + emailString);
                nrTelefon.setText("Nr Telefon: " + nrTelefonString);
            }
            else
            {
                nume.setVisible(false);
                cnp.setVisible(false);
                adresa.setVisible(false);
                email.setVisible(false);
                nrTelefon.setVisible(false);
            }

            show = !show;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
