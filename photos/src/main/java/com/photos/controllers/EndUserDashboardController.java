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
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * controller for regular user and not the admin
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class EndUserDashboardController implements CreationEventListener{
    /**
     * create new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
    * create PhotoManagementSystem instance
    */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    /**
     * current user
     */
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();
    /**
     * current user's all albums
     */
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

    
    /**
     * set new album
     */
    public void initialize() {
        setGrid(albums);
    }

    
    /** 
     * delete the input album
     * @param album to be deleted
     */
    private void deleteAlbum(Album album) {
        currentUser.deleteAlbum(album);
        onMagicAlbum();
    }

    
    /**
     * update the data about albums
     * @param albums new data
     */
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
            albumStack.setOnMouseEntered(
                mouseEvent -> {
                    albumStack.getScene().setCursor(Cursor.HAND);
                    albumThumbnail.setButton("Delete", event -> deleteAlbum(album));   
                }
            );
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

    
    /** 
     * Search for albums
     * @throws IOException error
     */
    @FXML
    private void searchAlbumAction() throws IOException {
        App.setRoot("search-dashboard");
    }

    
    /** 
     * listensers to create new album
     * @throws IOException error
     */
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

    
    /** 
     * log out handler
     * @throws IOException error
     */
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

    /**
     * set new albums to gris
     */
    @Override
    public void onMagicAlbum() {
        ArrayList<Album> albums = currentUser.getAlbums();
        setGrid(albums);
    }
    
    @Override
    public void onMagicPhoto() {}
}
