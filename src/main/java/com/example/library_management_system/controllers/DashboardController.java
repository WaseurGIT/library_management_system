package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button bookdetailspage;
    public Button studentdetails;
    public Button issuedBooks;
    public Button returnedBookPage;
    public Label studentCount;
    public Label bookCount;
    public Label issuedBookCount;


    public void goToStudentDetailsPage(ActionEvent event) throws  Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/studentdetailscontroller.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void goToIssuedBookPage(ActionEvent event) throws Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/issuedbook.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Issued Book");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToReturnedBookPage(ActionEvent event) throws Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/returnedbook.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Returned Book");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goToBookDetailsPage(ActionEvent event) {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/bookdetails.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Issued Book");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadCount();

    }

    private void loadCount() {
        Connection connection = DataBase.getConnection(); // Establish the connection

        if (connection != null) {
            try {
                // Query to count all students
                String studentQuery = "SELECT COUNT(*) AS studentCount FROM studentDetails";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(studentQuery)) {
                    if (resultSet.next()) {
                        int studentCountValue = resultSet.getInt("studentCount");
                        studentCount.setText(String.valueOf(studentCountValue)); // Set the student count in the label
                    }
                }

                // Query to count all books
                String bookQuery = "SELECT COUNT(*) AS bookCount FROM bookDetails";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(bookQuery)) {
                    if (resultSet.next()) {
                        int bookCountValue = resultSet.getInt("bookCount");
                        bookCount.setText(String.valueOf(bookCountValue)); // Set the total book count in the label
                    }
                }

                // Query to count available books
                String availableBookQuery = "SELECT COUNT(*) AS availableBookCount FROM bookDetails WHERE bookStatus = 'Available'";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(availableBookQuery)) {
                    if (resultSet.next()) {
                        int availableBookCountValue = resultSet.getInt("availableBookCount");
                        bookCount.setText(String.valueOf(availableBookCountValue)); // Set the available book count in the label
                    }
                }

                // Query to count issued books
                String issuedBookQuery = "SELECT COUNT(*) AS issuedBookCount FROM bookDetails WHERE bookStatus = 'Issued'";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(issuedBookQuery)) {
                    if (resultSet.next()) {
                        int issuedBookCountValue = resultSet.getInt("issuedBookCount");
                        issuedBookCount.setText(String.valueOf(issuedBookCountValue)); // Set the issued book count in the label
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error executing queries: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }


    public void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("You are about to log out.");
        alert.setContentText("Are you sure you want to continue?");

        // Wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.close();
            System.out.println("Logged out successfully!");
        } else {
            System.out.println("Logout canceled.");
        }
    }

}
