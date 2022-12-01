package com.photos.controllers;

import java.util.ArrayList;

import com.photos.App;
import com.photos.model.Album;
import com.photos.model.EndUser;
import com.photos.model.Photo;
import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
/**
 * controls the album
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class AlbumFormController extends Controller {

    private enum STATE {
        RENAME, 
        CREATE,
        SEARCH,
    }
    /**
     * create new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
     * create PhotoManagementSystem instance
     */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;;
    /**
     * create new listener for event
     */
    private CreationEventListener listener;
    /**
     * current album
     */
    private Album currAlbum;
    /**
     * current State
     */
    private STATE state;

    @FXML
    TextField albumNameInput;
    @FXML
    Button addButton;
    @FXML
    Text formTitle;

    
    /** 
     * Upgrade current album
     * @param obj new data
     */
    @Override
    public void setData(Object... obj) {
        for(int i = 0; i < obj.length; i++){
            if("Album".equals(obj[i].getClass().getSimpleName())) {
                state = STATE.RENAME;
                this.currAlbum = (Album) obj[i];
            } else {
                state = STATE.CREATE;
            }

        }
    }

    
    /** 
     * Litesten to the new Eevent such as adding new albums
     * @param listener listener
     */
    public void setAddAlbum(CreationEventListener listener) {
        this.listener = listener;
    }

    /**
     * Set new album form
     */
    @FXML 
    private void initialize() {
        if(state == STATE.RENAME) {
            albumNameInput.setText(currAlbum.getAlbumName());
            addButton.setText("Rename");
            formTitle.setText("Rename Album");
        }
    }
    /**
     * close the add album pop up
     */
    @FXML 
    private void cancelAddAlbumAction() {
        App.closePopup();
    }

    
    /** 
     * create new magic album
     * @throws Exception error
     */
    @FXML
    private void magicAlbumAction() {
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
            try {
                currentUser.createAlbum(albumNameInput.getText());
                listener.onMagicAlbum();
                App.closePopup();
            } catch (Exception e) {
                errorAlert.setHeaderText("Name already exists");
                errorAlert.showAndWait();
            }
        }
    }
}
