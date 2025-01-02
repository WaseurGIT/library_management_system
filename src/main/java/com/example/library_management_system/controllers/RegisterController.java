package com.example.library_management_system.controllers;

import com.example.library_management_system.utils.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    public TextField userName;
    public TextField regEmail;
    public PasswordField regPassword;
    public Button registered;
    public Button goToLoginPage;

    public void goToLoginPage(ActionEvent event) throws Exception{
        try {
            // Load the Register page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/login.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userRegistered(ActionEvent event) {

        String userName = this.userName.getText();
        String regEmail = this.regEmail.getText();
        String regPassword = this.regPassword.getText();

        Boolean isRegistered = DataBase.userRegistered(userName, regEmail, regPassword);

        if (isRegistered){
            try {
                // Load the Register page FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library_management_system/fxml/login.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login Page");
                stage.show();

                Alert registerSuccessfulAlert = new Alert(Alert.AlertType.INFORMATION);
                registerSuccessfulAlert.setTitle("Registration Successful");
                registerSuccessfulAlert.setHeaderText("Successfully Registered");

                DialogPane dialogPane = registerSuccessfulAlert.getDialogPane();
                dialogPane.setPrefSize(420,180);
                registerSuccessfulAlert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert registrationError = new Alert(Alert.AlertType.INFORMATION);
            registrationError.setTitle("Registration Error");
            registrationError.setHeaderText("Registration Error");

            DialogPane dialogPane = registrationError.getDialogPane();
            dialogPane.setPrefSize(420,180);
            registrationError.showAndWait();
        }


    }
}
