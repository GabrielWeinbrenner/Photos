package com.photos.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;
import com.photos.shared.Thumbnail;
import com.photos.model.EndUser;
import com.photos.model.Photo;
import com.photos.App;
import com.photos.model.Album;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * controler for album dashboard, you can edit albums names and other properties here
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class AlbumDashboardController extends Controller implements CreationEventListener {
    /**
     * create new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
     * create PhotoManagementSystem instance
     */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    /**
    * The current user who is logged in
    */
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();
    /**
     * the current album
     */
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
    /**
     * Set up current dashboard
     */
    public void initialize() {
        albumName.setText(currentAlbum.getAlbumName());
        setGrid(currentAlbum.getPhotos());
    }

    
    /** 
     * Update all object data
     * @param obj new data
     */
    @Override
    public void setData(Object... obj) {
        this.currentAlbum = (Album) obj[0];
    }

    /**
     * set the photos to new magic photos
     */
    @Override
    public void onMagicPhoto() {
        ArrayList<Photo> photos = currentAlbum.getPhotos();
        setGrid(photos);
    }

    /**
     * change the album name to magic album name
     */
    @Override
    public void onMagicAlbum() {
        albumName.setText(currentAlbum.getAlbumName());
    }

    
    /** 
     * delete current photo from album
     * @param p photo
     */
    private void deletePhoto(Photo p) {
        currentAlbum.removePhoto(p);
        onMagicPhoto();
    }
    
    /** 
     * update the Grid with new photos
     * @param photos photo
     */
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
            Thumbnail photoThumbnail = new Thumbnail(325, 325, "", "", photo.getStringDate(), photo.getThumbnailImage());
            StackPane photoStack = photoThumbnail.getThumbnail();

            photoStack.setOnMouseEntered(
                mouseEvent -> {
                    photoStack.getScene().setCursor(Cursor.HAND);
                    photoThumbnail.setButton("Delete", event -> deletePhoto(photo));   
                }
            );
            photoStack.setOnMouseExited(
                mouseEvent -> {
                    photoStack.getScene().setCursor(Cursor.DEFAULT);
                    photoThumbnail.removeButtons();
                }
            );
            photoStack.setOnMouseClicked(
                mouseEvent -> {
                    try {
                        PhotoPreviewerController ppc = new PhotoPreviewerController();
                        App.setRoot("photo-preview", ppc, currentAlbum, photo);
                    } catch(IOException e1) {
                        e1.printStackTrace();
                    }
                }
            );

            GridPane.setConstraints(photoStack, i % COLUMNS, j);
            this.gridPane.getChildren().add(photoStack);

        }
        photoScroll.setContent(gridPane);
    }

    
    /** 
     * Go back to previous Page
     * @throws IOException error
     */
    @FXML
    private void backAction() throws IOException {
        App.setRoot("end-user-dashboard");
    }

    
    /** 
     * Change the name of current album
     * @throws IOException error
     */
    @FXML
    private void renameAlbumAction() throws IOException {
        AlbumFormController afc = new AlbumFormController();
        afc.setAddAlbum(this);
        App.setPopup("album-form", afc, currentAlbum, 320);
    }

    
    /** 
     * create the photos
     * @throws IOException error
     */
    @FXML
    private void createPhotoAction() throws IOException {
        PhotoFormController pfc = new PhotoFormController();
        pfc.setAddPhoto(this);
        App.setPopup("photo-form", pfc, currentAlbum);
    }

}