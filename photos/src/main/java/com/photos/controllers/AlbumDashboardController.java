package com.photos.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;
import com.photos.shared.Thumbnail;
import com.photos.shared.constants;
import com.photos.model.EndUser;
import com.photos.model.Photo;
import com.photos.App;
import com.photos.model.Album;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AlbumDashboardController extends Controller implements CreationEventListener {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();
    Album currentAlbum;

    final int COLUMNS = 3;
    @FXML
    ScrollPane photoScroll;
    @FXML
    Button addButton;
    @FXML
    Button renameButton;
    @FXML
    Text albumName;

    GridPane gridPane;

    @FXML
    public void initialize() {
        albumName.setText(currentAlbum.getAlbumName());
        setGrid(currentAlbum.getPhotos());
    }

    @Override
    public void setData(Object obj) {
        this.currentAlbum = (Album) obj;
    }

    private void setGrid(ArrayList<Photo> photos) {
        this.gridPane = new GridPane();
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(25);
        this.gridPane.setPadding(new Insets(0, 75, 0, 75));
        int j = 0;
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            if (i % COLUMNS == 0) {
                j += 1;
            }
            // String photoDate = LocalDateTime.parse(photo.getDate().toString(), DateTimeFormatter.ofPattern("MM-yyyy")).toString();
            Thumbnail photoThumbnail = new Thumbnail(325, 325, "", "", photo.getStringDate(), photo.getImage());
            StackPane photoStack = photoThumbnail.getThumbnail();
            constants.setHoverComponent(photoStack);
            GridPane.setConstraints(photoStack, i % COLUMNS, j);
            this.gridPane.getChildren().add(photoStack);
        }
        photoScroll.setContent(gridPane);

    }

    @FXML
    private void backAction() throws IOException {
        App.setRoot("end-user-dashboard");
    }

    @FXML
    private void renameAlbumAction() throws IOException {
        AlbumFormController afc = new AlbumFormController();
        afc.setAddAlbum(this);
        App.setPopup("album-form", afc, currentAlbum, 320);
    }

    @FXML
    private void createPhotoAction() throws IOException {
        PhotoFormController pfc = new PhotoFormController();
        pfc.setAddPhoto(this);
        App.setPopup("photo-form", pfc, currentAlbum);
    }

    @Override
    public void onAddPhoto() {
        ArrayList<Photo> photos = currentAlbum.getPhotos();
        setGrid(photos);
    }

    @Override
    public void onMagicAlbum() {
        albumName.setText(currentAlbum.getAlbumName());
    }
}