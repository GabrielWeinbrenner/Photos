package com.photos.controllers;

import com.photos.App;
import com.photos.model.Album;
import com.photos.model.EndUser;
import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
public class AlbumFormController extends Controller {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    private enum STATE {
        RENAME, 
        CREATE
    };
    private CreationEventListener listener;
    private Album currAlbum;
    private STATE state;

    @FXML
    TextField albumNameInput;
    @FXML
    Button addButton;
    @FXML
    Text formTitle;

    @FXML 
    private void initialize() {
        if(state == STATE.RENAME) {
            albumNameInput.setText(currAlbum.getAlbumName());
            addButton.setText("Rename");
            formTitle.setText("Rename Album");
        }
    }

    @Override
    public void setData(Object obj) {
        String asdf = obj.getClass().getSimpleName();
        if(asdf.equals("Album")) {
            state = STATE.RENAME;
            this.currAlbum = (Album) obj;
        }
    }

    public void setAddAlbum(CreationEventListener listener) {
        this.listener = listener;
    }
    @FXML 
    private void cancelAddAlbumAction() {
        App.closePopup();
    }

    @FXML
    private void magicAlbumAction() throws Exception {
        Alert errorAlert = new Alert(AlertType.ERROR);
        if(albumNameInput.getText() == null) {
            errorAlert.setHeaderText("No Album Title provided ");
            errorAlert.showAndWait();
        } else if(state == STATE.RENAME) {
            currAlbum.rename(albumNameInput.getText());
            listener.onMagicAlbum();
            App.closePopup();
        } else {
            EndUser currentUser =  (EndUser) PSInstance.getCurrentUser();
            currentUser.createAlbum(albumNameInput.getText());
            listener.onMagicAlbum();
            App.closePopup();
        }
    }
}
