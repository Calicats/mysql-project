package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;

public class ProfesorPanelController {
    public Button catalogButton;
    @FXML
    private Label adresa;
    @FXML
    private Label cnp;
    @FXML
    private Label departament;
    @FXML
    private Label email;
    @FXML
    private Label greetUser;
    @FXML
    private Button logoutButton;
    @FXML
    private Label nrMaxOre;
    @FXML
    private Label nrMinOre;
    @FXML
    private Label nrTelefon;
    @FXML
    private Label nume;
    @FXML
    private Button personalDataButton;
    @FXML
    private Button viewActivityButton;

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

    public void onViewActivity() throws IOException
    {
        Main main = new Main();
        main.changeScene("panouActivitatiProfesorTotal.fxml", username, tableName, 1024, 768);
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
                String departamentString = Query.getSingleInfo(connection, tableName, username, "departament");
                String nrMinOreString = Query.getSingleInfo(connection, tableName, username, "nrMinOre");
                String nrMaxOreString = Query.getSingleInfo(connection, tableName, username, "nrMaxOre");

                showData(true);

                greetUser.setText("Bine ai venit, " + this.username + "!");
                nume.setText("Nume: " + numeString);
                cnp.setText("CNP: " + cnpString);
                adresa.setText("Adresa: " + adresaString);
                email.setText("Email: " + emailString);
                nrTelefon.setText("Nr Telefon: " + nrTelefonString);
                departament.setText("Departament: " + departamentString);
                nrMinOre.setText("Numar minim ore: " + nrMinOreString);
                nrMaxOre.setText("Numar maxim ore " + nrMaxOreString);
            }
            else
            {
                showData(false);
            }

            show = !show;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /***
     * Intoarcere la scena de logare
     * @throws IOException exceptia pe care o arunca metoda
     */

    public void onLogoutButton() throws IOException
    {
        Main main = new Main();
        System.out.println("Bye, " + username);

        main.changeScene("logare.fxml", username, tableName, 600, 400);
    }

    public void onGrupStudii() throws IOException
    {
        Main.main.changeScene("grupuriDeStudii.fxml", username, tableName, 1200, 562);
    }

    private void showData(boolean expr)
    {
        nume.setVisible(expr);
        cnp.setVisible(expr);
        adresa.setVisible(expr);
        email.setVisible(expr);
        nrTelefon.setVisible(expr);
        departament.setVisible(expr);
        nrMinOre.setVisible(expr);
        nrMaxOre.setVisible(expr);
    }

    public void openCatalog(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("catalogProfesori.fxml", username, tableName, 1024, 768);
    }

    public void openProgrameazaActivitate(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("programareCursuriProfesor.fxml", username, tableName, 1024, 768);
    }

    public void activitateDeAzi(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("activitateaDeAzi.fxml", username, tableName, 1024, 768);
    }
}
