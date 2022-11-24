package com.photos.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.model.EndUser;
import com.photos.App;
import com.photos.shared.CreationEventListener;
import com.photos.shared.Thumbnail;
import com.photos.shared.constants;
import com.photos.model.Album;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EndUserDashboardController implements CreationEventListener{
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();
    ArrayList<Album> albums = currentUser.getAlbums();

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
        setGrid(albums);
    }

    private void setGrid(ArrayList<Album> albums) {
        this.gridPane = new GridPane();
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(25);
        this.gridPane.setPadding(new Insets(0, 75, 0, 75));
        int j = 0;
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            if (i % COLUMNS == 0) {
                j += 1;
            }
            Thumbnail albumThumbnail = new Thumbnail(325, 325, album.getAlbumName(), album.getDateRange(),
                    album.getAlbumSize() + " photos", album.getThumbnail());
            StackPane albumStack = albumThumbnail.getThumbnail();
            constants.setHoverComponent(albumStack);
            albumStack.setOnMouseClicked(
                e -> {
                    try {
                        AlbumDashboardController adc = new AlbumDashboardController();
                        App.setRoot("album-dashboard", adc, album);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            );
            GridPane.setConstraints(albumStack, i % COLUMNS, j);
            this.gridPane.getChildren().add(albumStack);
        }
        albumScroll.setContent(gridPane);

    }

    @FXML
    private void searchAlbumAction() throws IOException {
    }

    @FXML
    private void createAlbumAction() throws IOException {
        AlbumFormController afc = new AlbumFormController();
        afc.setAddAlbum(this);
        try {
            App.setPopup("album-form", afc, currentUser, 320);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onMagicAlbum() {
        ArrayList<Album> albums = currentUser.getAlbums();
        setGrid(albums);
    }

    @Override
    public void onAddPhoto() {}
}
