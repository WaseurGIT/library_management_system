package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import com.example.library_management_system.utils.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentDetailsController implements Initializable {

    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, String> deptColumn;
    @FXML
    private TableColumn<Student, String> intakeColumn;
    @FXML
    private TableColumn<Student, String> secColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableView<Student> studentTable;


    // from dashboard to student details page
    public void goToStudentDetailsPage(ActionEvent event) throws Exception{
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

    // from student details page to dashboard
    public void backToDashboard(ActionEvent event) throws Exception {
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

    // from student details page to book details
    public void goToBookDetailsPage(ActionEvent event) throws  Exception{
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

    // from student details page to issued books
    public void goToIssuedBookPage(ActionEvent event) throws Exception {
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/issuedbook.fxml"));
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

    // from student details page to returned books
    public void goToReturnedBookPage(ActionEvent event) throws Exception{
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
        setupTableColumns();
        loadStudent();

    }
    private void setupTableColumns() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        intakeColumn.setCellValueFactory(new PropertyValueFactory<>("intake"));
        secColumn.setCellValueFactory(new PropertyValueFactory<>("sec"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    private void loadStudent() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        Connection connection = DataBase.getConnection();

        if (connection != null) {
            String query = "SELECT * FROM studentDetails";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String dept = resultSet.getString("dept");
                    String intake = resultSet.getString("intake");
                    String sec = resultSet.getString("sec");
                    String email = resultSet.getString("email");

                    Student student = new Student(id, name, dept, intake, sec, email);
                    studentList.add(student);
                }

                studentTable.setItems(studentList);

            } catch (SQLException e) {
                System.out.println("Error loading students: " + e.getMessage());
            }
        }
    }
}
