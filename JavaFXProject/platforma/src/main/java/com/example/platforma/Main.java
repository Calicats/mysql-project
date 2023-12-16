package com.example.platforma;

import com.example.controllers.AfterLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logare.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Logare platforma");
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml, String username, String tableName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        currentStage.setScene(scene);

        if(fxml.equals("dupaLogare.fxml") && username != null && tableName != null)
        {
            AfterLoginController controller = fxmlLoader.getController();
            controller.initUser(username, tableName);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}