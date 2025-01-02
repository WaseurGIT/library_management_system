package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.sql.*;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import java.net.URL;
import java.util.ResourceBundle;

public class ReturnedBookController implements Initializable {

    public GridPane allBooks;

    // from returned book page to dashboard page
    public void backToDashboard(ActionEvent event) throws Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/dashboard.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //from returned book to student details page
    public void goToStudentDetailsPage(ActionEvent event) throws Exception {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/studentdetailscontroller.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // from returned book page to book details page
    public void goToBookDetailsPage(ActionEvent event) throws Exception {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/bookdetails.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // from returned books to issued book page
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooks();
    }

    private void loadBooks() {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            // Get today's date
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            String query = "SELECT id, studentId, bookId, studentName, bookName, bookImage, returnedDate, authorName " +
                    "FROM returnedBook WHERE returnedDate = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, today); // Binding the current date to the query

                ResultSet resultSet = statement.executeQuery();
                int row = 0;
                int col = 0;

                while (resultSet.next()) {
                    int returnedBookId = resultSet.getInt("id");
                    String studentName = resultSet.getString("studentName");
                    String bookName = resultSet.getString("bookName");
                    String bookImage = resultSet.getString("bookImage");
                    java.sql.Date returnedDate = resultSet.getDate("returnedDate");
                    String authorName = resultSet.getString("authorName");

                    // Create a book card for each returned book
                    VBox bookCard = createReturnedBookCard(returnedBookId, studentName, bookName, bookImage, returnedDate, authorName);

                    // Add book card to GridPane
                    allBooks.add(bookCard, col, row);
                    col++;
                    if (col == 4) {  // 4 books per row
                        col = 0;
                        row++;
                    }
                }
            } catch (SQLException e) {
                showAlert("Error", "Error loading returned books", AlertType.ERROR);
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // Closing the connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("Connection Error", "Failed to connect to the database", AlertType.ERROR);
        }
    }

    private VBox createReturnedBookCard(int returnedBookId, String studentName, String bookName, String bookImage, Date returnedDate, String authorName) {
        VBox bookCard = new VBox();
        bookCard.setSpacing(15);
        bookCard.setStyle("-fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(bookImage, true);  // Assuming bookImage contains a valid URL or file path
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image for: " + bookName);
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
        }

        imageView.setFitHeight(150);
        imageView.setFitWidth(200);

        Label nameLabel = new Label(bookName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label authorLabel = new Label("By " + authorName);
        authorLabel.setStyle("-fx-font-size: 12;");

        Label studentLabel = new Label("Returned by: " + studentName);
        studentLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");

        Label dateLabel = new Label("Returned on: " + returnedDate.toString());
        dateLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");

        // Hidden ID
        Label idLabel = new Label("ID: " + returnedBookId);
        idLabel.setVisible(false);  // Hide but accessible for operations later

        bookCard.getChildren().addAll(imageView, nameLabel, authorLabel, studentLabel, dateLabel, idLabel);
        return bookCard;
    }


    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
