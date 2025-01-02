package com.example.library_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.library_management_system.utils.DataBase;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IssuedBookController implements Initializable {
    public GridPane allIssuedBooks;

    public void backToDashboard(ActionEvent event) throws Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/dashboard.fxml"));
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

    public void goToStudentDetailsPage(ActionEvent event) throws Exception {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/studentdetailscontroller.fxml"));
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

    public void goToReturnedBookPage(ActionEvent event) throws Exception {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/returnedbook.fxml"));
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooks();
    }
    private void loadBooks() {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            // Updated query with JOIN to include bookStatus from bookDetails
            String query = "SELECT ib.id AS issuedBookId, ib.studentId, ib.bookId, ib.studentName, ib.bookName, ib.bookImage, ib.issueDate, ib.authorName, bd.bookStatus " +
                    "FROM issuedBook ib " +
                    "JOIN bookDetails bd ON ib.bookId = bd.id " +
                    "WHERE bd.bookStatus = 'Issued'"; // Corrected column reference to bookDetails

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                int row = 0;
                int col = 0;

                while (resultSet.next()) {
                    int issuedBookId = resultSet.getInt("issuedBookId");
                    int bookId = resultSet.getInt("bookId");
                    String studentName = resultSet.getString("studentName");
                    String bookName = resultSet.getString("bookName");
                    String bookImageURL = resultSet.getString("bookImage");
                    String authorName = resultSet.getString("authorName");
                    Date issueDate = resultSet.getDate("issueDate");
                    String bookStatus = resultSet.getString("bookStatus");

                    // Create a card for the issued book
                    VBox bookCard = createIssuedBookCard(issuedBookId, bookId, studentName, bookName, bookImageURL, authorName, issueDate, bookStatus);
                    allIssuedBooks.add(bookCard, col, row);  // Add bookCard to GridPane

                    col++;
                    if (col == 4) {  // 4 books per row
                        col = 0;
                        row++;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error loading books: " + e.getMessage());
            }
        }
    }


    private VBox createIssuedBookCard(int issuedBookId, int bookId, String studentName, String bookName, String bookImageURL, String authorName, Date issueDate, String bookStatus) {
        VBox bookCard = new VBox();
        bookCard.setSpacing(15);
        bookCard.setStyle("-fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(bookImageURL, true);
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

        Label studentLabel = new Label("Issued to: " + studentName);
        studentLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");

        Label issueDateLabel = new Label("Issued on: " + issueDate.toString());
        issueDateLabel.setStyle("-fx-font-size: 12;");

        // Display book status
        Label statusLabel = new Label("Status: " + bookStatus);
        statusLabel.setStyle(bookStatus.equals("Available") ?
                "-fx-text-fill: green; -fx-font-size: 12;" :
                "-fx-text-fill: red; -fx-font-size: 12;");

        // Hidden ID for reference
        Label idLabel = new Label("ID: " + issuedBookId);
        idLabel.setVisible(false);  // Hide but accessible for operations later

        // You can add other actions like returning the book if needed
        Button returnButton = new Button("Return");
        returnButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        returnButton.setOnAction(e -> returnBook(issuedBookId,bookCard));  // Set action to return the book

        bookCard.getChildren().addAll(imageView, nameLabel, authorLabel, studentLabel, issueDateLabel, statusLabel, idLabel, returnButton);
        return bookCard;
    }


    private void returnBook(int issuedBookId, VBox bookCard ) {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            // First, get the book and student details from the issuedBook table
            String selectQuery = "SELECT ib.studentId, ib.bookId, ib.studentName, ib.bookName, ib.bookImage, ib.authorName, ib.issueDate " +
                    "FROM issuedBook ib WHERE ib.id = ?";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, issuedBookId);
                ResultSet resultSet = selectStmt.executeQuery();

                if (resultSet.next()) {
                    int studentId = resultSet.getInt("studentId");
                    int bookId = resultSet.getInt("bookId");
                    String studentName = resultSet.getString("studentName");
                    String bookName = resultSet.getString("bookName");
                    String bookImage = resultSet.getString("bookImage");
                    String authorName = resultSet.getString("authorName");
                    Date issueDate = resultSet.getDate("issueDate");
                    Date returnedDate = new Date(System.currentTimeMillis());  // Current date for returnedDate

                    // Insert the returned book information into the returnedBook table
                    String insertQuery = "INSERT INTO returnedBook (studentId, bookId, studentName, bookName, bookImage, returnedDate, authorName) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, studentId);
                        insertStmt.setInt(2, bookId);
                        insertStmt.setString(3, studentName);
                        insertStmt.setString(4, bookName);
                        insertStmt.setString(5, bookImage);
                        insertStmt.setDate(6, returnedDate);
                        insertStmt.setString(7, authorName);
                        insertStmt.executeUpdate();
                    }

                    // Update the bookStatus in bookDetails to 'Available'
                    String updateQuery = "UPDATE bookDetails SET bookStatus = 'Available' WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                    }

                    // Optionally, delete the issuedBook record
                    String deleteQuery = "DELETE FROM issuedBook WHERE id = ?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setInt(1, issuedBookId);
                        deleteStmt.executeUpdate();
                    }

                    allIssuedBooks.getChildren().remove(bookCard);
                    loadBooks();
                    showAlert("Book returned "," Book information inserted into returnedBook.");
                }
            } catch (SQLException e) {
                System.out.println("Error returning book: " + e.getMessage());
            }
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private VBox createBookCard(int bookId, String bookName, String bookAuthor, String bookImageURL, String bookDept, String bookStatus) {
        VBox bookCard = new VBox();
        bookCard.setSpacing(15);
        bookCard.setStyle("-fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(bookImageURL, true);
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image for: " + bookName);
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
        }

        imageView.setFitHeight(150);
        imageView.setFitWidth(200);

        Label nameLabel = new Label(bookName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label authorLabel = new Label("By " + bookAuthor);
        authorLabel.setStyle("-fx-font-size: 12;");

        Label deptLabel = new Label("Dept: " + bookDept);
        deptLabel.setStyle("-fx-font-size: 12; -fx-text-fill: gray;");

        Label statusLabel = new Label("Status: " + bookStatus);
        statusLabel.setStyle(bookStatus.equals("Available") ?
                "-fx-text-fill: green; -fx-font-size: 12;" :
                "-fx-text-fill: red; -fx-font-size: 12;");

        // Hidden ID
        Label idLabel = new Label("ID: " + bookId);
        idLabel.setVisible(false);  // Hide but accessible for operations later

        bookCard.getChildren().addAll(imageView, nameLabel, authorLabel, deptLabel, statusLabel, idLabel);
        return bookCard;
    }
}
