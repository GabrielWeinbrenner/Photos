package com.photos;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.model.EndUser;
import com.photos.model.Album;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EndUserDashboardController {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();

    final int COLUMNS = 3;
    @FXML
    ScrollPane albumScroll;
    @FXML
    Button searchButton;
    @FXML
    Button createButton;
    @FXML
    Button logoutButton;

    GridPane gridPane;
    @FXML 
    public void initialize() {
        setGrid(currentUser.getAlbums());
    }
    private void setGrid(ArrayList<Album> albums) {
        this.gridPane = new GridPane();
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(25);
        this.gridPane.setPadding(new Insets(0,75,0,75));
        int j = 0;
        for(int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            if(i%COLUMNS == 0) {
                j+=1;
            }
            Rectangle b = new Rectangle(325, 325);
            b.setFill(Color.rgb(85, 85, 85));
            GridPane.setConstraints(b, i%COLUMNS, j);
            this.gridPane.getChildren().add(b);
        }
        albumScroll.setContent(gridPane);

    }
    @FXML
    private void searchAlbumAction() throws IOException {
    }

    @FXML
    private void createAlbumAction() throws IOException {
        
        currentUser.createAlbum("hello");
        ArrayList<Album> albums = currentUser.getAlbums();
        setGrid(albums);
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
