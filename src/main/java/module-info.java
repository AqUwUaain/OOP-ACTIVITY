module com.example.oopactivity {

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires org.postgresql.jdbc;

    requires io.github.cdimascio.dotenv.java;

    opens com.example.oopactivity to javafx.fxml;
    opens com.example.oopactivity.controller to javafx.fxml;

    exports com.example.oopactivity;
    exports com.example.oopactivity.controller;
}