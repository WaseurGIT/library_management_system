package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController implements Initializable {


    public Button addBook;
    public TextField bookName;
    public TextField bookAuthorName;
    public TextField bookImage;
    public TextField bookDept;
    public GridPane allBooks;

    //from dashboard to book details page
    public void goToBookDetailsPage(ActionEvent event) throws Exception{
        loadBooks();
    }

    // from book details to dashboard page
    public void goToDashboard(ActionEvent event) throws Exception{
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

    public void addBookDetails(ActionEvent event) {

        String bookName = this.bookName.getText();
        String bookAuthorName = this.bookAuthorName.getText();
        String bookImage = this.bookImage.getText();
        String bookDept =  this.bookDept.getText();

        Boolean isInsertBook = DataBase.insertBookDetails(bookName, bookAuthorName, bookImage, bookDept);

        if (isInsertBook){
            Alert bookInsert = new Alert(Alert.AlertType.INFORMATION);
            bookInsert.setTitle("Book Inserted Successfully");
            bookInsert.setHeaderText("Book Inserted Successfully");

            DialogPane dialogPane = bookInsert.getDialogPane();
            dialogPane.setPrefSize(420,180);
            bookInsert.showAndWait();

            this.bookName.setText("");
            this.bookAuthorName.setText("");
            this.bookImage.setText("");
            this.bookDept.setText("");

            loadBooks();

        }
        else {
            Alert bookInsert = new Alert(Alert.AlertType.INFORMATION);
            bookInsert.setTitle("Book insert internal Error");
            bookInsert.setHeaderText("Book insert internal Error");

            DialogPane dialogPane = bookInsert.getDialogPane();
            dialogPane.setPrefSize(420,180);
            bookInsert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooks();
    }

    private void loadBooks() {
        allBooks.getChildren().clear();
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            String query = "SELECT id, bookName, bookAuthor, bookImage, bookDept, bookStatus FROM bookDetails";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                int row = 0;
                int col = 0;

                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String bookName = resultSet.getString("bookName");
                    String bookAuthor = resultSet.getString("bookAuthor");
                    String bookImageURL = resultSet.getString("bookImage");
                    String bookDept = resultSet.getString("bookDept");
                    String bookStatus = resultSet.getString("bookStatus");

                    VBox bookCard = createBookCard(bookId, bookName, bookAuthor, bookImageURL, bookDept, bookStatus);
                    allBooks.add(bookCard, col, row);  // Add bookCard to GridPane

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

    private void loadBooks(String dept) {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            // Clear existing book cards
            allBooks.getChildren().clear();

            String query = "SELECT id, bookName, bookAuthor, bookImage, bookDept, bookStatus FROM bookDetails WHERE bookDept = '"+dept+"'";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                int row = 0;
                int col = 0;

                while (resultSet.next()) {
                    int bookId = resultSet.getInt("id");
                    String bookName = resultSet.getString("bookName");
                    String bookAuthor = resultSet.getString("bookAuthor");
                    String bookImageURL = resultSet.getString("bookImage");
                    String bookDept = resultSet.getString("bookDept");
                    String bookStatus = resultSet.getString("bookStatus");

                    VBox bookCard = createBookCard(bookId, bookName, bookAuthor, bookImageURL, bookDept, bookStatus);
                    allBooks.add(bookCard, col, row);  // Add bookCard to GridPane

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

        // Remove button
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        removeButton.setOnAction(e -> removeBook(bookId, bookCard));  // Set action to remove book

        // Issue button
        Button issueButton = new Button("Issue");
        issueButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        issueButton.setOnAction(e -> openIssueModal(bookId, bookName, bookAuthor, bookImageURL, bookDept));

        bookCard.getChildren().addAll(imageView, nameLabel, authorLabel, deptLabel, statusLabel, idLabel, removeButton, issueButton);
        return bookCard;
    }

    private void openIssueModal(int bookId, String bookName, String bookAuthor, String bookImageURL, String bookDept) {
        // Create a new Stage (modal)
        Stage issueStage = new Stage();
        VBox modalLayout = new VBox(10);
        modalLayout.setStyle("-fx-padding: 20; -fx-background-color: white;");

        // Create the input fields
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Student Name");

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");

        Button submitButton = new Button("Issue Book");
        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            String studentName = studentNameField.getText();
            String studentId = studentIdField.getText();

            if (studentName.isEmpty() || studentId.isEmpty()) {
                showAlert("Error", "Please fill out both fields.");
                return;
            }

            // Insert the data into issuedBook table
            insertIssuedBook(bookId, bookName, bookAuthor, bookImageURL, bookDept, studentName, studentId);
            issueStage.close();
        });

        modalLayout.getChildren().addAll(new Label("Enter Student Details:"), studentNameField, studentIdField, submitButton);

        Scene scene = new Scene(modalLayout, 300, 200);
        issueStage.setScene(scene);
        issueStage.setTitle("Issue Book");
        issueStage.show();
    }

    private void insertIssuedBook(int bookId, String bookName, String bookAuthor, String bookImageURL, String bookDept, String studentName, String studentId) {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            String insertQuery = "INSERT INTO issuedBook (studentId, bookId, studentName, bookName, bookImage, issueDate, authorName) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, studentId);
                statement.setInt(2, bookId);
                statement.setString(3, studentName);
                statement.setString(4, bookName);
                statement.setString(5, bookImageURL);
                statement.setString(6, bookAuthor);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    // Optionally, you can show a success alert
                    showAlert("Success", "Book issued successfully.");
                    // Update book status to 'Issued'
                    updateBookStatus(bookId);
                }
            } catch (SQLException e) {
                System.out.println("Error inserting issued book: " + e.getMessage());
                showAlert("Error", "Failed to issue book.");
            }
        }
    }

    private void updateBookStatus(int bookId) {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            String updateQuery = "UPDATE bookDetails SET bookStatus = 'Issued' WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
                loadBooks();
            } catch (SQLException e) {
                System.out.println("Error updating book status: " + e.getMessage());
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


    private void removeBook(int bookId, VBox bookCard) {
        Connection connection = DataBase.getConnection();
        if (connection != null) {
            String deleteQuery = "DELETE FROM bookDetails WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, bookId);
                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    // If deletion is successful, remove the book card from the UI
                    allBooks.getChildren().remove(bookCard);  // Remove the specific book card
                    loadBooks();

                    // Optionally show a success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Removed");
                    alert.setHeaderText("The book has been removed successfully.");
                    alert.showAndWait();

                } else {
                    // If deletion fails
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Removing Book");
                    alert.setHeaderText("Failed to remove the book.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                System.out.println("Error executing delete query: " + e.getMessage());
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




    public void deptCse(ActionEvent event) {
        loadBooks("CSE");
    }

    public void deptEee(ActionEvent event) {
        loadBooks("EEE");
    }

    public void deptCivil(ActionEvent event) {
        loadBooks("Civil");
    }

    public void deptTextile(ActionEvent event) {
        loadBooks("Textile");
    }

    public void deptAccounting(ActionEvent event) {
        loadBooks("Accounting");
    }

    public void deptEconomics(ActionEvent event) {
        loadBooks("Economics");
    }

    public void deptEnglish(ActionEvent event) {
        loadBooks("English");
    }
}
