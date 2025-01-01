package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public TextField loginEmail;
    public PasswordField loginPassword;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button goToRegisterPageButton;

    @FXML
    private Button loginButton;

    private Stage stage;
    private Scene scene;

    //from login page to register page
    public void goToRegisterPage(ActionEvent event) throws IOException {

        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/register.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Page");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //from login page to dashboard page
    public void goToLandingPage(ActionEvent event) throws Exception {
        try {

            // to special character check
            //String pattern = ".*[A-Z].*.*[a-z].*.*[!@#$%^&*(),.?\":{}|<>].*";
            String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*(),.?\":{}|<>]).*$";


            this.loginEmail.setText("asdf@gmail.com");
            this.loginPassword.setText("Asdf12@");

            // get the value of email and password
            String email = loginEmail.getText();
            String password = loginPassword.getText();


            if(email == null || email.isEmpty() || password == null || password.isEmpty()){
                Alert nullEmailPassAlert = new Alert(Alert.AlertType.ERROR);
                nullEmailPassAlert.setTitle("Login Error");
                nullEmailPassAlert.setHeaderText("Enter Email and Password to Login");

                // change the alert box size
                DialogPane dialogPane = nullEmailPassAlert.getDialogPane();
                dialogPane.setPrefSize(600,300);
                nullEmailPassAlert.showAndWait();
            }

            else if(email.contains("@") && password.length()>=6 && password.matches(pattern)){
                // Load the Register page FXML file

                Boolean isValidUser = DataBase.validUser(email,password);

                if (isValidUser){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/dashboard.fxml"));
                    Parent root = loader.load();

                    // Get the current stage and set the new scene
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Dashboard");
                    stage.show();

                    Alert loginSuccessfulAlert = new Alert(Alert.AlertType.INFORMATION);
                    loginSuccessfulAlert.setTitle("Login Successful");
                    loginSuccessfulAlert.setHeaderText("Successfully logged in");

                    DialogPane dialogPane = loginSuccessfulAlert.getDialogPane();
                    dialogPane.setPrefSize(600,300);
                    loginSuccessfulAlert.showAndWait();
                } else {
                    System.out.println("User Not Find");
                    Alert userNotFound = new Alert(Alert.AlertType.INFORMATION);
                    userNotFound.setTitle("User Not Find");
                    userNotFound.setHeaderText("User Not Find");

                    DialogPane dialogPane = userNotFound.getDialogPane();
                    dialogPane.setPrefSize(600,300);
                    userNotFound.showAndWait();
                }
            }
            else{
                if(!email.contains("@")){
                    System.out.println("Error : Email must contain @ .");
                    // show an alert if the email does not contain @ sign
                    Alert emailAlert = new Alert(Alert.AlertType.ERROR);
                    emailAlert.setTitle("Login Error");
                    emailAlert.setHeaderText("Email must contain @");

                    DialogPane dialogPane = emailAlert.getDialogPane();
                    dialogPane.setPrefSize(600,300);
                    emailAlert.showAndWait();
                }
                else if(password.length()<6){
                    System.out.println("Error: Password must be 6 characters.");
                    // show an alert if the length of password is less than 6
                    Alert passwordLengthAlert = new Alert(Alert.AlertType.ERROR);
                    passwordLengthAlert.setTitle("Login Error");
                    passwordLengthAlert.setHeaderText("Password length must be 6 character");

                    DialogPane dialogPane = passwordLengthAlert.getDialogPane();
                    dialogPane.setPrefSize(600,300);
                    passwordLengthAlert.showAndWait();
                }
                else if(!password.contains(pattern)){
                    System.out.println("Error: Password must contain at least one special character.");
                    // show an alert if the password does not contain any special character and uppercase and lowercase character
                    Alert passwordPatternAlert = new Alert(Alert.AlertType.ERROR);
                    passwordPatternAlert.setTitle("Login Error");
                    passwordPatternAlert.setHeaderText("Password must contain at least one special character and uppercase and lowercase character");

                    DialogPane dialogPane = passwordPatternAlert.getDialogPane();
                    dialogPane.setPrefSize(600,300);
                    passwordPatternAlert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TextField getLoginEmail(){
        return loginEmail;
    }
    public void setLoginEmail(TextField loginEmail) {
        this.loginEmail = loginEmail;
    }

    public PasswordField getLoginPassword(){
        return loginPassword;
    }
    public void  setLoginPassword(PasswordField loginPassword){
        this.loginPassword = loginPassword;
    }
}