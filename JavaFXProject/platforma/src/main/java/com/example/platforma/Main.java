package com.example.platforma;

import com.example.controllers.*;
import com.example.controllers.GrupuriDeStudiiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage currentStage;

    public static Main main;
    public String username;
    public String tablename;


    /***
     * Metoda apelata o singura data, la deschiderea programului
     * @param stage numele fxml-ului cu care se deschide programul (logare.fxml)
     * @throws IOException exceptia pe care o arunca functia
     */

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logare.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setResizable(false);
        stage.setTitle("Logare platforma");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Metoda asta e apelata de fiecare data cand se schimba scena
     * Preia datele utilizatorului din Main.main si le trimite catre controllerul care trebuie sa le preia
     * Si deschide by default un ecran 1024x768
     * @param fxml
     * @throws IOException
     */
    public void changeScene(String fxml) throws IOException
    {
        changeScene(fxml, username, tablename, 1024, 768);
    }

    /***
     * Metoda asta e apelata de fiecare data cand se schimba scena
     * Preia datele utilizatorului din Main.main si le trimite catre controllerul care trebuie sa le preia
     * @param fxml
     * @throws IOException
     */
    public void changeScene(String fxml, int row, int col) throws IOException
    {
        changeScene(fxml, Main.main.username, Main.main.tablename, row, col);
    }

    /***
     * Cu metoda asta, modifici scena, adica ce vede utilizatorul
     * @param fxml numele fisierului fxml
     * @param username username-ul utilizatorului logat
     * @param tableName tabela din care face parte utilizatorul
     * @param row lungimea ferestrei
     * @param col latimea ferestrei
     * @throws IOException exceptia pe care o arunca metoda
     */
    public void changeScene(String fxml, String username, String tableName, int row, int col) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), row, col);

        currentStage.setScene(scene);
        currentStage.setResizable(false);

        if(username != null && tableName != null)
        {
            if(Main.main == null)
                Main.main = new Main();
            Main.main.username = username;
            Main.main.tablename = tableName;
            switch(tableName)
            {
                case "superadministrator", "administrator" ->
                {
                    switch(fxml)
                    {
                        case "logare.fxml" -> currentStage.setTitle("Logare platforma");

                        case "panouAdministrativ.fxml" ->
                        {
                            currentStage.setTitle("Panou administrativ");
                            AdminPanelController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }

                        case "gestionareUtilizatori.fxml" ->
                        {
                            currentStage.setTitle("Gestionare utilizatori");
                            ManageUsersController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }

                        case "gestionareActivitate.fxml" ->
                        {
                            currentStage.setTitle("Gestionare activitate profesor");
                            ManageActivitateController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }

                        case "cautareUtilizatori.fxml" ->
                        {
                            currentStage.setTitle("Cautare utilizatori");
                            FindUsersController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                    }
                }
                case "profesor" ->
                {
                    switch(fxml)
                    {
                        case "logare.fxml" -> currentStage.setTitle("Logare platforma");
                        case "panouProfesor.fxml" ->
                        {
                            currentStage.setTitle("Panou profesor");
                            ProfesorPanelController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "catalogProfesori.fxml" ->
                        {
                            currentStage.setTitle("Catalog profesori");
                            CatalogProfesoriController controller = fxmlLoader.getController();
                            controller.initialize(username, tableName);
                        }
                        case "panouActivitatiProfesorTotal.fxml" ->
                        {
                            currentStage.setTitle("Panou activitati");
                            ViewActivitateTotalProfesor controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "grupuriDeStudii.fxml" ->
                        {
                            currentStage.setTitle("Grupuri de studii");
                            GrupuriDeStudiiController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "creazaModificaGrupDeStudii.fxml" ->
                        {
                            currentStage.setTitle("Gestionare grup de studii");
                            CreazaModificaGrupDeStudii controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                    }
                }
                case "student" ->
                {
                    switch(fxml)
                    {
                        case "logare.fxml" -> currentStage.setTitle("Logare platforma");
                        case "panouStudent.fxml" ->
                        {
                            currentStage.setTitle("Panou student");
                            StudentPanelController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "noteStudenti.fxml" ->
                        {
                            currentStage.setTitle("Note studenti");
                            NoteStudentiController controller = fxmlLoader.getController();
                            controller.initialize(username);
                        }
                        case "inscriereActivitate.fxml" ->
                        {
                            currentStage.setTitle("Inscriere activitate");
                            InscriereActivitateController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "grupuriDeStudii.fxml" ->
                        {
                            currentStage.setTitle("Grupuri de studii");
                            GrupuriDeStudiiController controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "creazaModificaGrupDeStudii.fxml" ->
                        {
                            currentStage.setTitle("Gestionare grup de studii");
                            CreazaModificaGrupDeStudii controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                        case "panouActivitatiStudentiTotal.fxml" ->
                        {
                            currentStage.setTitle("Activitatea ta");
                            ViewActivitateTotalStudent controller = fxmlLoader.getController();
                            controller.initUser(username, tableName);
                        }
                    }
                }
            }
        }
    }



    public static void main(String[] args)
    {
        Main.main = new Main();
        launch();
    }
}