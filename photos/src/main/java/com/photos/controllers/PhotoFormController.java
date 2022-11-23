package com.photos.controllers;

import java.io.File;
import java.io.IOException;

import com.photos.App;
import com.photos.constants.Controller;
import com.photos.constants.CreationEventListener;
import com.photos.constants.EventObserver;
import com.photos.constants.EventSubject;
import com.photos.constants.constants.USER_TYPE;
import com.photos.model.Album;
import com.photos.model.Photo;
import com.photos.model.PhotoManagementSystem;
import com.photos.model.User;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PhotoFormController extends Controller {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;

    private CreationEventListener listener;

    public void setAddPhoto(CreationEventListener listener) {
        this.listener = listener;
    }
    Album currentAlbum;
    @FXML
    Button addButton;
    @FXML
    Button cancelButton;
    @FXML
    Button uploadPhotoButton;
    @FXML
    TextArea captionArea;
    @FXML
    ImageView photoPreviewer;

    Image image;

    @Override
    public void setData(Object obj) {
        this.currentAlbum = (Album) obj;
    }

    @FXML
    private void addPhotoAction() throws IOException {
        Photo newPhoto = new Photo(captionArea.getText(), this.image);
        currentAlbum.addPhoto(newPhoto);
        listener.onAddPhoto(); 
        App.closePopup();
    }

    @FXML
    private void cancelCreatePhotoAction() throws IOException {
        App.closePopup();
    }

    @FXML
    private void uploadPhotoAction() throws IOException {
        Alert errorAlert = new Alert(AlertType.ERROR);
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(App.popupStage);
            this.image = new Image(selectedFile.toURI().toString());
            photoPreviewer.setImage(this.image);
        } catch (Exception e) {
            errorAlert.setHeaderText("Error in uploading file");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }

    }

}
