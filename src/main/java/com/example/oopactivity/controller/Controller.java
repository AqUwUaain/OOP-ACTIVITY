package com.example.oopactivity.controller;

import com.example.oopactivity.database.DBConnection;
import com.example.oopactivity.enums.YearLevel;
import com.example.oopactivity.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCourse;

    @FXML
    private ChoiceBox<YearLevel> cbYear;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Integer> colId;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colYear;

    private ObservableList<Student> list =
            FXCollections.observableArrayList();

    private Connection conn;

    private int selectedId = -1;

    @FXML
    public void initialize() {

        conn = DBConnection.connect();

        cbYear.getItems().setAll(YearLevel.values());

        colId.setCellValueFactory(
                data -> data.getValue().idProperty().asObject());

        colName.setCellValueFactory(
                data -> data.getValue().nameProperty());

        colCourse.setCellValueFactory(
                data -> data.getValue().courseProperty());

        colYear.setCellValueFactory(
                data -> data.getValue().yearLevelProperty());

        loadData();

        table.setOnMouseClicked(e -> {

            Student s = table.getSelectionModel().getSelectedItem();

            if (s != null) {

                selectedId = s.getId();

                txtName.setText(s.getName());
                txtCourse.setText(s.getCourse());

                for (YearLevel y : YearLevel.values()) {

                    if (y.toString().equalsIgnoreCase(s.getYearLevel())) {

                        cbYear.setValue(y);
                        break;
                    }
                }
            }
        });
    }

    private void loadData() {

        list.clear();

        try {

            String query = "SELECT * FROM students ORDER BY id ASC";

            ResultSet rs =
                    conn.createStatement().executeQuery(query);

            while (rs.next()) {

                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("year_level")
                ));
            }

            table.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addStudent() {

        if (txtName.getText().isEmpty()
                || txtCourse.getText().isEmpty()
                || cbYear.getValue() == null) {

            showAlert("Please fill all fields!");
            return;
        }

        try {

            String query =
                    "INSERT INTO students(name, course, year_level) VALUES (?, ?, ?)";

            PreparedStatement pst =
                    conn.prepareStatement(query);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtCourse.getText());
            pst.setString(3, cbYear.getValue().toString());

            pst.executeUpdate();

            loadData();
            clearFields();

            showAlert("Student added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateStudent() {

        if (selectedId == -1) {

            showAlert("Please select a student first!");
            return;
        }

        if (txtName.getText().isEmpty()
                || txtCourse.getText().isEmpty()
                || cbYear.getValue() == null) {

            showAlert("Please fill all fields!");
            return;
        }

        try {

            String query =
                    "UPDATE students SET name=?, course=?, year_level=? WHERE id=?";

            PreparedStatement pst =
                    conn.prepareStatement(query);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtCourse.getText());
            pst.setString(3, cbYear.getValue().toString());
            pst.setInt(4, selectedId);

            pst.executeUpdate();

            loadData();
            clearFields();

            showAlert("Student updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {

        if (selectedId == -1) {

            showAlert("Please select a student first!");
            return;
        }

        try {

            String query =
                    "DELETE FROM students WHERE id=?";

            PreparedStatement pst =
                    conn.prepareStatement(query);

            pst.setInt(1, selectedId);

            pst.executeUpdate();

            loadData();
            clearFields();

            showAlert("Student deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {

        txtName.clear();
        txtCourse.clear();

        cbYear.setValue(null);

        selectedId = -1;

        table.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}