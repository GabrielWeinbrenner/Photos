package com.photos.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.model.User;
import com.photos.model.EndUser;
import com.photos.App;
import com.photos.shared.CreationEventListener;
import com.photos.shared.Thumbnail;
import com.photos.shared.constants;
import com.photos.model.Adminstrator;
import com.photos.model.Album;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class AdminUserDashboardController {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    Adminstrator currentUser = (Adminstrator) PSInstance.getCurrentUser();

    @FXML
    TableView<User> userTable;
    @FXML
    Button createUserButton;
    @FXML
    Button logoutButton;
    @FXML
    TableColumn<User, String> usernameTableColumn;
    @FXML
    TableColumn<User, String> passwordTableColumn;
    @FXML
    TableColumn<EndUser, String> albumTableColumn;

    GridPane gridPane;

    @FXML
    public void initialize() {
        setTable(PSInstance.getUsers());
        refreshRow();
    }

    private void setTable(ArrayList<User> users) {
        ObservableList<User> userData = FXCollections.<User>observableArrayList();
        userData.addAll(users);
        userTable.setItems(userData);
    }

    public void refreshRow() {
        this.usernameTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        this.passwordTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        this.albumTableColumn.setCellValueFactory(new PropertyValueFactory<EndUser, String>("albumCount"));
    }

    @FXML
    private void createUserAction() throws IOException {
        // Set Create User Popup
        // UserFormController ufc = new UserFormController();
        // try {
        //     App.setPopup("user-create-form", ufc, currentUser, 320);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void logOutAction() throws IOException {
        Alert errorAlert = new Alert(AlertType.ERROR);
        try {
            PSInstance.logout();
            App.setRoot("login");
        } catch (Exception e) {
            errorAlert.setHeaderText("Error In Logging Out");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

}
