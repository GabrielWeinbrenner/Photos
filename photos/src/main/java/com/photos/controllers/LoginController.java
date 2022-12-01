package com.photos.controllers;

import java.io.IOException;

import com.photos.App;
import com.photos.shared.constants.USER_TYPE;
import com.photos.model.PhotoManagementSystem;
import com.photos.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * controller for loggin in new users
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class LoginController {
    /**
     * create new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
    * create PhotoManagementSystem instance
    */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;

    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    Button signInButton;

    
    /** 
     * sign in handler and move to admin screen or normal user screen
     * @throws IOException error
     */
    @FXML
    private void signInAction() throws IOException {
        Alert errorAlert = new Alert(AlertType.ERROR);
        try {
            User result = PSInstance.login(usernameInput.getText(), passwordInput.getText());
            if(result.getType() == USER_TYPE.Admin) {
                App.setRoot("admin-user-dashboard");
            } else if(result.getType() == USER_TYPE.EndUser) {
                App.setRoot("end-user-dashboard");
            }

        } catch (Exception e) {
            errorAlert.setHeaderText("Error In Signing In");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
