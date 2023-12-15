module com.example.platforma {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.platforma to javafx.fxml;
    exports com.example.platforma;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
}