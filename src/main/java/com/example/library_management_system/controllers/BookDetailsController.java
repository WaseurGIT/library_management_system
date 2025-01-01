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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class BookDetailsController implements Initializable {


    public Button addBook;
    public TextField bookName;
    public TextField bookAuthorName;
    public TextField bookImage;
    public TextField bookDept;
    public GridPane allBooks;

    //from dashboard to book details page
    public void goToBookDetailsPage(ActionEvent event) throws Exception{
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
            dialogPane.setPrefSize(600,300);
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
            dialogPane.setPrefSize(600,300);
            bookInsert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooks();
    }

    private void loadBooks() {
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
                    if (col == 3) {  // 3 books per row
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
                    if (col == 3) {  // 3 books per row
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
        bookCard.setSpacing(10);
        bookCard.setStyle("-fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(bookImageURL, true);
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image for: " + bookName);
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
        }

        imageView.setFitHeight(160);
        imageView.setFitWidth(120);

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
