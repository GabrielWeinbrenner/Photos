package com.photos.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.App;
import com.photos.model.Album;
import com.photos.model.EndUser;
import com.photos.model.Photo;
import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MovePhotoFormController extends Controller {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();

    Photo currPhoto;
    Album currAlbum;

    @FXML
    TableView<Album> albumTable;
    @FXML
    Button cancelButton;
    @FXML
    Button moveButton;
    @FXML
    TableColumn<Album, String> albumNameColumn;
    @FXML
    TableColumn<Album, String> photoSizeColumn;

    @FXML
    public void initialize() {
        setTable(currentUser.getAlbums());
        refreshRow();
    }

    @FXML 
    public void moveAction() throws IOException {
        Album currentSelectedAlbum = albumTable.getSelectionModel().getSelectedItem();
        currAlbum.removePhoto(currPhoto);
        currentSelectedAlbum.addPhoto(currPhoto);
        AlbumDashboardController adc = new AlbumDashboardController();
        App.setRoot("album-dashboard", adc, currentSelectedAlbum);
        App.closePopup();
    }

    @FXML 
    public void copyAction() {
        Album currentSelectedAlbum = albumTable.getSelectionModel().getSelectedItem();
        currentSelectedAlbum.addPhoto(currPhoto);

        App.closePopup();
    }

    @FXML 
    public void cancelAction() {
        App.closePopup();
    }

    public void refreshRow() {
        this.albumNameColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
        this.photoSizeColumn.setCellValueFactory(new PropertyValueFactory<Album, String>("albumSize"));
    }

    private void setTable(ArrayList<Album> albums) {
        ObservableList<Album> albumData = FXCollections.<Album>observableArrayList();
        albumData.addAll(albums);
        albumTable.setItems(albumData);
    }

    @Override
    public void setData(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if ("Photo".equals(args[i].getClass().getSimpleName())) {
                currPhoto = (Photo) args[i];
            }
            if ("Album".equals(args[i].getClass().getSimpleName())) {
                currAlbum = (Album) args[i];
            }
        }
    }

    

}
