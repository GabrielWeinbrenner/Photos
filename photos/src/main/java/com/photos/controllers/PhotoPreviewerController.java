package com.photos.controllers;

import java.io.IOException;

import com.photos.App;
import com.photos.model.Album;
import com.photos.model.Photo;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;
import com.photos.shared.constants;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class PhotoPreviewerController extends Controller implements CreationEventListener {


    @FXML
    Button previousPhotoButton;
    @FXML
    Button nextPhotoButton;
    @FXML
    Button backButton;
    @FXML
    ImageView photoPreviewView;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Text captionText;
    @FXML
    Text locationText;

    Photo currPhoto;
    Album currAlbum;
    @FXML
    private void initialize() {
        setPhotoPreview();
        App.scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT) {
                previousPhotoAction();
            } else if(keyEvent.getCode() == KeyCode.RIGHT) {
                nextPhotoAction();
            }
        });
    }
    private void setPhotoPreview() {
        captionText.setText(currPhoto.getCaption());
        photoPreviewView.setImage(currPhoto.getImage());
        centerImage();
        locationText.setText(currAlbum.getCurrentLocationString(currPhoto));
    }
    @FXML
    private void previousPhotoAction() {
        currPhoto = currAlbum.getPreviousPhoto(currPhoto);
        setPhotoPreview();
    }

    @FXML
    private void nextPhotoAction() {
        currPhoto = currAlbum.getNextPhoto(currPhoto);
        setPhotoPreview();
    }

    @FXML
    private void backAction() throws IOException {
        AlbumDashboardController adc = new AlbumDashboardController();
        App.setRoot("album-dashboard", adc, currAlbum);
    }
    public void centerImage() {
        Image img = photoPreviewView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = photoPreviewView.getFitWidth() / img.getWidth();
            double ratioY = photoPreviewView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            photoPreviewView.setX((photoPreviewView.getFitWidth() - w) / 2);
            photoPreviewView.setY((photoPreviewView.getFitHeight() - h) / 2);

        }
    }
    @FXML
    private void deleteAction() {
        // Add confirmation
        int currIndex = currAlbum.getPhotos().indexOf(currPhoto);
        if(currIndex == currAlbum.getPhotos().size()-1) {
            currIndex--;
        }
        currAlbum.removePhoto(currPhoto);
        currPhoto = currAlbum.getPhotos().get(currIndex);
        setPhotoPreview();
    }

    @FXML
    private void editAction() throws IOException {
        // Have a popup with the edit caption and edit tags fields
        PhotoFormController pfc = new PhotoFormController();
        pfc.setAddPhoto(this);
        App.setPopup("photo-form", pfc, currPhoto, constants.FORM_HEIGHT);
        
    }

    @Override
    public void setData(Object... args) {
        for(int i = 0; i < args.length; i++){
            if("Photo".equals(args[i].getClass().getSimpleName())){
                currPhoto = (Photo) args[i];
            }
            if("Album".equals(args[i].getClass().getSimpleName())) {
                currAlbum = (Album) args[i];
            }
        }
    }
    @Override
    public void onMagicPhoto() {
        this.captionText.setText(currPhoto.getCaption());
        this.photoPreviewView.setImage(currPhoto.getImage());
        centerImage();
    }
    @Override
    public void onMagicAlbum() {}

    

}
