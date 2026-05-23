module com.example.oopactivity {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.oopactivity to javafx.fxml;
    opens com.example.oopactivity.controller to javafx.fxml;

    exports com.example.oopactivity;
    exports com.example.oopactivity.controller;
    exports com.example.oopactivity.model;
    exports com.example.oopactivity.enums;
}