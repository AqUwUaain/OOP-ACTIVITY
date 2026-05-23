package com.example.oopactivity.controller;

import com.example.oopactivity.database.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;

public class DashboardController {

    @FXML
    private StackPane contentPane;

    @FXML
    public void initialize() {

        showHome(null);
    }

    @FXML
    public void showHome(ActionEvent event) {

        contentPane.getChildren().clear();

        VBox mainContainer = new VBox(30);

        mainContainer.setPadding(new Insets(40));

        // TITLE
        Label dashboardTitle =
                new Label("Dashboard Overview");

        dashboardTitle.setStyle("""
                -fx-font-size: 42;
                -fx-font-weight: bold;
                -fx-text-fill: #0f172a;
                """);

        // CARDS CONTAINER
        HBox cards = new HBox(25);

        cards.setAlignment(Pos.CENTER_LEFT);

        try {

            Connection conn = DBConnection.connect();

            // TOTAL STUDENTS
            ResultSet totalRs =
                    conn.createStatement().executeQuery(
                            "SELECT COUNT(*) AS total FROM students"
                    );

            totalRs.next();

            int totalStudents =
                    totalRs.getInt("total");

            // 1ST YEAR
            ResultSet firstRs =
                    conn.createStatement().executeQuery(
                            "SELECT COUNT(*) AS total FROM students WHERE year_level='1st Year'"
                    );

            firstRs.next();

            int firstYear =
                    firstRs.getInt("total");

            // 2ND YEAR
            ResultSet secondRs =
                    conn.createStatement().executeQuery(
                            "SELECT COUNT(*) AS total FROM students WHERE year_level='2nd Year'"
                    );

            secondRs.next();

            int secondYear =
                    secondRs.getInt("total");

            // 3RD YEAR
            ResultSet thirdRs =
                    conn.createStatement().executeQuery(
                            "SELECT COUNT(*) AS total FROM students WHERE year_level='3rd Year'"
                    );

            thirdRs.next();

            int thirdYear =
                    thirdRs.getInt("total");

            // 4TH YEAR
            ResultSet fourthRs =
                    conn.createStatement().executeQuery(
                            "SELECT COUNT(*) AS total FROM students WHERE year_level='4th Year'"
                    );

            fourthRs.next();

            int fourthYear =
                    fourthRs.getInt("total");

            // CARDS
            VBox totalCard =
                    createCard(
                            "TOTAL STUDENTS",
                            String.valueOf(totalStudents),
                            "#2563eb"
                    );

            VBox firstCard =
                    createCard(
                            "1ST YEAR",
                            String.valueOf(firstYear),
                            "#f59e0b"
                    );

            VBox secondCard =
                    createCard(
                            "2ND YEAR",
                            String.valueOf(secondYear),
                            "#0ea5e9"
                    );

            VBox thirdCard =
                    createCard(
                            "3RD YEAR",
                            String.valueOf(thirdYear),
                            "#8b5cf6"
                    );

            VBox fourthCard =
                    createCard(
                            "4TH YEAR",
                            String.valueOf(fourthYear),
                            "#ef4444"
                    );

            cards.getChildren().addAll(
                    totalCard,
                    firstCard,
                    secondCard,
                    thirdCard,
                    fourthCard
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        // WELCOME PANEL
        VBox welcomeBox = new VBox(20);

        welcomeBox.setAlignment(Pos.CENTER);

        welcomeBox.setPrefHeight(400);

        welcomeBox.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 25;
                -fx-padding: 40;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 20, 0, 0, 5);
                """);

        Label welcome =
                new Label("WELCOME ADMIN");

        welcome.setStyle("""
                -fx-font-size: 52;
                -fx-font-weight: bold;
                -fx-text-fill: #0f172a;
                """);

        Label subtitle =
                new Label("Professional School Management System");

        subtitle.setStyle("""
                -fx-font-size: 20;
                -fx-text-fill: #64748b;
                """);

        Label desc =
                new Label(
                        "Manage students, records, and academic data efficiently."
                );

        desc.setStyle("""
                -fx-font-size: 16;
                -fx-text-fill: #94a3b8;
                """);

        welcomeBox.getChildren().addAll(
                welcome,
                subtitle,
                desc
        );

        mainContainer.getChildren().addAll(
                dashboardTitle,
                cards,
                welcomeBox
        );

        contentPane.getChildren().add(mainContainer);
    }

    private VBox createCard(
            String title,
            String value,
            String color
    ) {

        VBox card = new VBox(15);

        card.setAlignment(Pos.CENTER);

        card.setPrefWidth(180);
        card.setPrefHeight(140);

        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 20;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 4);
                """);

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle("""
                -fx-font-size: 14;
                -fx-text-fill: #64748b;
                """);

        Label valueLabel =
                new Label(value);

        valueLabel.setStyle("""
                -fx-font-size: 48;
                -fx-font-weight: bold;
                -fx-text-fill: """ + color + ";");

        card.getChildren().addAll(
                titleLabel,
                valueLabel
        );

        return card;
    }

    @FXML
    public void openStudentManagement(ActionEvent event) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/com/example/oopactivity/main.fxml"
                            )
                    );

            contentPane.getChildren().clear();

            contentPane.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent event) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/com/example/oopactivity/login.fxml"
                            )
                    );

            Stage stage = new Stage();

            stage.setTitle("Login");

            stage.setScene(new Scene(root));

            stage.show();

            ((Stage)((Button)event.getSource())
                    .getScene()
                    .getWindow())
                    .close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}