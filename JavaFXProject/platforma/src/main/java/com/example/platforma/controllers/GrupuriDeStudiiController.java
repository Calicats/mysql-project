package com.example.platforma.controllers;

import com.example.platforma.Main;
import javafx.event.ActionEvent;

public class GrupuriDeStudiiController {

    // you can delete this because we know in main what type of user we have, this is just to show you how to do it
    enum userType{
        STUDENT,
        PROFESOR
    };
    userType user;



    // in csp 15, we have the changeScene function in the Main class without so many variables. it's a bit more elegant
    // but left the logic here for now
    public void goBack(ActionEvent actionEvent) {
        if(user == userType.STUDENT) {
            //Main.main.changeScene("../fxml/student.fxml");
        }
        else {
            //Main.main.changeScene("../fxml/profesor.fxml");
        }
    }
}
