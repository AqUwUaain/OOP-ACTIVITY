package com.example.oopactivity.controller;

import com.example.oopactivity.database.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;

public class DashboardController {

    @FXML
    private StackPane contentPane;

    private Connection conn;

    @FXML
    public void initialize() {

        conn = DBConnection.connect();

        showHome(null);
    }

    @FXML
    public void showHome(ActionEvent event) {

        contentPane.getChildren().clear();

        VBox mainContainer = new VBox();

        mainContainer.setSpacing(30);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        mainContainer.setStyle("""
                -fx-padding: 40;
                """);

        // TITLE
        Label title =
                new Label("Dashboard Overview");

        title.setStyle("""
                -fx-font-size: 38;
                -fx-font-weight: bold;
                -fx-text-fill: #0f172a;
                """);

        // ================= STATS =================

        HBox statsContainer = new HBox();

        statsContainer.setSpacing(20);
        statsContainer.setAlignment(Pos.CENTER);

        int totalStudents = getCount(
                "SELECT COUNT(*) FROM students"
        );

        int bsitStudents = getCount(
                "SELECT COUNT(*) FROM students WHERE course='BSIT'"
        );

        int firstYear = getCount(
                "SELECT COUNT(*) FROM students WHERE year_level='1st Year'"
        );

        int secondYear = getCount(
                "SELECT COUNT(*) FROM students WHERE year_level='2nd Year'"
        );

        int thirdYear = getCount(
                "SELECT COUNT(*) FROM students WHERE year_level='3rd Year'"
        );

        int fourthYear = getCount(
                "SELECT COUNT(*) FROM students WHERE year_level='4th Year'"
        );

        statsContainer.getChildren().addAll(

                createCard(
                        "TOTAL STUDENTS",
                        String.valueOf(totalStudents),
                        "#2563eb"
                ),

                createCard(
                        "BSIT STUDENTS",
                        String.valueOf(bsitStudents),
                        "#22c55e"
                ),

                createCard(
                        "1ST YEAR",
                        String.valueOf(firstYear),
                        "#f59e0b"
                ),

                createCard(
                        "2ND YEAR",
                        String.valueOf(secondYear),
                        "#0ea5e9"
                ),

                createCard(
                        "3RD YEAR",
                        String.valueOf(thirdYear),
                        "#8b5cf6"
                ),

                createCard(
                        "4TH YEAR",
                        String.valueOf(fourthYear),
                        "#ef4444"
                )
        );

        // =============== WELCOME CARD ===============

        VBox welcomeCard = new VBox();

        welcomeCard.setAlignment(Pos.CENTER);
        welcomeCard.setSpacing(20);

        welcomeCard.setPrefWidth(1100);
        welcomeCard.setPrefHeight(350);

        welcomeCard.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 25;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 20, 0, 0, 5);
                """);

        Label welcome =
                new Label("WELCOME ADMIN");

        welcome.setStyle("""
                -fx-font-size: 48;
                -fx-font-weight: bold;
                -fx-text-fill: #0f172a;
                """);

        Label subtitle =
                new Label("Professional School Management System");

        subtitle.setStyle("""
                -fx-font-size: 22;
                -fx-text-fill: #64748b;
                """);

        Label desc =
                new Label("Manage students, records, and academic data efficiently.");

        desc.setStyle("""
                -fx-font-size: 17;
                -fx-text-fill: #94a3b8;
                """);

        welcomeCard.getChildren().addAll(
                welcome,
                subtitle,
                desc
        );

        mainContainer.getChildren().addAll(
                title,
                statsContainer,
                welcomeCard
        );

        contentPane.getChildren().add(mainContainer);
    }

    private VBox createCard(
            String title,
            String value,
            String color
    ) {

        VBox card = new VBox();

        card.setAlignment(Pos.CENTER);
        card.setSpacing(12);

        card.setPrefWidth(170);
        card.setPrefHeight(120);

        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 20;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 15, 0, 0, 5);
                """);

        Label lblTitle =
                new Label(title);

        lblTitle.setStyle("""
                -fx-font-size: 15;
                -fx-text-fill: #64748b;
                """);

        Label lblValue =
                new Label(value);

        lblValue.setStyle("""
                -fx-font-size: 42;
                -fx-font-weight: bold;
                -fx-text-fill: """ + color + ";");

        card.getChildren().addAll(
                lblTitle,
                lblValue
        );

        return card;
    }

    private int getCount(String query) {

        try {

            ResultSet rs =
                    conn.createStatement().executeQuery(query);

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
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