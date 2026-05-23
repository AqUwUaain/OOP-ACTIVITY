package com.example.oopactivity.controller;

import io.github.cdimascio.dotenv.Dotenv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private final Dotenv dotenv = Dotenv.load();

    @FXML
    public void initialize() {

        txtPassword.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER) {

                login(new ActionEvent());
            }
        });
    }

    @FXML
    private void login(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String envUser = dotenv.get("APP_USERNAME");
        String envPass = dotenv.get("APP_PASSWORD");

        if(username.equals(envUser)
                && password.equals(envPass)) {

            try {

                Parent root =
                        FXMLLoader.load(
                                getClass().getResource(
                                        "/com/example/oopactivity/dashboard.fxml"
                                )
                        );

                Stage stage = new Stage();

                stage.setTitle("Dashboard");

                stage.setScene(new Scene(root));

                stage.show();

                if(event.getSource() instanceof Button) {

                    ((Stage)((Button)event.getSource())
                            .getScene()
                            .getWindow())
                            .close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setContentText(
                    "Invalid username or password"
            );

            alert.showAndWait();
        }
    }
}