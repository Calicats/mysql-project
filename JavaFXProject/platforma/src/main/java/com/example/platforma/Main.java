package com.example.platforma;

import com.example.controllers.AddActivitateController;
import com.example.controllers.ControlPanelController;
import com.example.controllers.FindUsersController;
import com.example.controllers.ManageUsersController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage currentStage;

    /***
     * Metoda apelata o singura data, la deschiderea programului
     * @param stage numele fxml-ului cu care se deschide programul (logare.fxml)
     * @throws IOException exceptia pe care o arunca functia
     */

    @Override
    public void start(Stage stage) throws IOException
    {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logare.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setResizable(false);
        stage.setTitle("Logare platforma");
        stage.setScene(scene);
        stage.show();
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

        if(username != null && tableName != null)
        {
            if(tableName.equals("superadministrator") || tableName.equals("administrator"))
            {
                switch (fxml)
                {
                    case "logare.fxml" -> currentStage.setTitle("Logare platforma");

                    case "panouAdministrativ.fxml" ->
                    {
                        currentStage.setTitle("Platforma scolara");
                        ControlPanelController controller = fxmlLoader.getController();
                        controller.initUser(username, tableName);
                    }

                    case "gestionareUtilizatori.fxml" ->
                    {
                        currentStage.setTitle("Gestionare utilizatori");
                        ManageUsersController controller = fxmlLoader.getController();
                        controller.initUser(username, tableName);
                    }

                    case "adaugaActivitate.fxml" ->
                    {
                        currentStage.setTitle("Adauga activitate profesor");
                        AddActivitateController controller = fxmlLoader.getController();
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
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}