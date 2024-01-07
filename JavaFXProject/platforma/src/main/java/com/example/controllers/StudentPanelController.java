package com.example.controllers;

import com.example.platforma.Main;
import com.example.sql.Connect;
import com.example.sql.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class StudentPanelController {
    @FXML
    public Button carnetButton;
    @FXML
    private Label adresa;
    @FXML
    private Label anStudiu;
    @FXML
    private Label cnp;
    @FXML
    private Label email;
    @FXML
    private Label greetUser;
    @FXML
    private Button logoutButton;
    @FXML
    private Label nrTelefon;
    @FXML
    private Label numarOre;
    @FXML
    private Label nume;
    @FXML
    private Button personalDataButton;
    
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
                String anStudiuString = Query.getSingleInfo(connection, tableName, username, "anStudiu");
                String numarOreString = Query.getSingleInfo(connection, tableName, username, "numarOre");

                showData(true);

                greetUser.setText("Bine ai venit, " + this.username + "!");
                nume.setText("Nume: " + numeString);
                cnp.setText("CNP: " + cnpString);
                adresa.setText("Adresa: " + adresaString);
                email.setText("Email: " + emailString);
                nrTelefon.setText("Nr Telefon: " + nrTelefonString);
                anStudiu.setText("An studiu: " + anStudiuString);
                numarOre.setText("Numar ore: " + numarOreString);
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
        main.changeScene("logare.fxml", username, tableName, 600, 400);
    }

    private void showData(boolean expr)
    {
        nume.setVisible(expr);
        cnp.setVisible(expr);
        adresa.setVisible(expr);
        email.setVisible(expr);
        nrTelefon.setVisible(expr);
        anStudiu.setVisible(expr);
        numarOre.setVisible(expr);
    }

    /**
     * Metoda care te duce la scena de afisare a notelor
     */


    public void onShowNoteStudenti(ActionEvent actionEvent) throws IOException {
        Main.main.changeScene("noteStudenti.fxml", username, tableName, 1024, 768);
    }
}
