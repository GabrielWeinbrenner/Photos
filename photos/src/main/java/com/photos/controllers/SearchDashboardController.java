package com.photos.controllers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.photos.model.PhotoManagementSystem;
import com.photos.model.Tag;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SearchDashboardController implements CreationEventListener {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();
    ArrayList<Photo> photos = new ArrayList<>();
    final int COLUMNS = 3;

    @FXML
    ScrollPane searchScroll;
    @FXML
    Button searchButton;
    @FXML
    Button renameButton;
    @FXML
    Text albumName;
    @FXML
    TextField searchInput;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;

    GridPane gridPane;

    @FXML
    public void initialize() {
        setGrid(photos);
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
            Thumbnail photoThumbnail = new Thumbnail(325, 325, "", "", photo.getStringDate(),
                    photo.getThumbnailImage());
            StackPane photoStack = photoThumbnail.getThumbnail();

            GridPane.setConstraints(photoStack, i % COLUMNS, j);
            this.gridPane.getChildren().add(photoStack);

        }
        searchScroll.setContent(gridPane);
    }

    @FXML
    private void backAction() throws IOException {
        App.setRoot("end-user-dashboard");
    }

    @FXML
    private void makeAlbumAction() {
        AlbumFormController afc = new AlbumFormController();
        Album newAlbum = currentUser.createAlbum("");
        for(Photo photo : photos) {
            newAlbum.addPhoto(photo);
        }
        afc.setAddAlbum(this);
        try {
            App.setPopup("album-form", afc, newAlbum, 320);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchAction() {
        Alert errorAlert = new Alert(AlertType.ERROR);

        String searchText = searchInput.getText();
        String[] searchArgs = searchText.split(" ");
        int lengthOfSearch = searchArgs.length;
        if (lengthOfSearch != 0) {
            errorAlert.setHeaderText("Error In Searching with Tags");
            if (lengthOfSearch != 3 && lengthOfSearch != 1) {
                errorAlert.setContentText("Incorrect number of tags given");
                errorAlert.showAndWait();
                return;
            }
            if (lengthOfSearch == 3
                    && (!searchArgs[1].toUpperCase().equals("OR") && !searchArgs[1].toUpperCase().equals("AND"))) {
                errorAlert.setContentText("OR or AND was not provided");
                errorAlert.showAndWait();
                return;
            }
            if (lengthOfSearch == 3 && !(searchArgs[0].contains("=") && searchArgs[2].contains("="))) {
                errorAlert.setContentText("Tags Do not contain =");
                errorAlert.showAndWait();
                return;
            }
            if (lengthOfSearch == 1 && searchText.length() > 0 && !(searchArgs[0].contains("="))) {
                errorAlert.setContentText("Tags Do not contain =");
                errorAlert.showAndWait();
                return;
            }
        }
        boolean dateFilter = startDatePicker.getValue() != null && endDatePicker.getValue() != null;
        Date startDate = null;
        Date endDate = null;
        if (dateFilter) {
            LocalDate startDateLocal = startDatePicker.getValue();
            Instant startDateInstant = Instant.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()));
            startDate = Date.from(startDateInstant);

            LocalDate endDateLocal = endDatePicker.getValue();
            Instant endDateInstant = Instant.from(endDateLocal.atStartOfDay(ZoneId.systemDefault()));
            endDate = Date.from(endDateInstant);
        }

        photos = new ArrayList<Photo>();
        for (Album album : currentUser.getAlbums()) {
            for (Photo photo : album.getPhotos()) {
                boolean shouldAddPhoto = true;
                if (searchArgs.length == 3) {
                    if (photo.getTags().size() == 0) {
                        shouldAddPhoto = false;
                    }
                    String[] firstTag = searchArgs[0].split("=");
                    String[] secondTag = searchArgs[2].split("=");
                    if (!searchArgs[1].equalsIgnoreCase("OR")) {
                        int c = 0;
                        // Check if the photo contains both tags
                        for (Tag tag : photo.getTags()) {
                            if (!((firstTag[0].equals(tag.getTagName()) && firstTag[0].equals(tag.getTagValue()))
                                    || (secondTag[0].equals(tag.getTagName())
                                            && secondTag[0].equals(tag.getTagValue())))) {
                                c++;
                            }
                        }
                        if (c <= 2) {
                            shouldAddPhoto = false;
                        }
                    }
                    if (!searchArgs[1].equalsIgnoreCase("AND")) {
                        for (Tag tag : photo.getTags()) {
                            if (!((firstTag[0].equals(tag.getTagName()) && firstTag[0].equals(tag.getTagValue()))
                                    || !(secondTag[0].equals(tag.getTagName())
                                            && secondTag[0].equals(tag.getTagValue())))) {
                                shouldAddPhoto = false;
                            }
                        }
                    }

                }
                if (searchArgs.length == 1 && searchText.length() > 0) {
                    if (photo.getTags().size() == 0) {
                        shouldAddPhoto = false;
                    }
                    for (Tag tag : photo.getTags()) {
                        String[] firstTag = searchArgs[0].split("=");
                        if (!firstTag[0].equals(tag.getTagName()) && !firstTag[1].equals(tag.getTagValue())) {
                            shouldAddPhoto = false;
                        }
                    }
                }
                if (dateFilter && !(photo.getDate().after(startDate) && photo.getDate().before(endDate))) {
                    shouldAddPhoto = false;
                }
                if (shouldAddPhoto)
                    photos.add(photo);
            }
        }
        setGrid(photos);
    }

    @Override
    public void onMagicPhoto() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMagicAlbum() {
        try {
            App.setRoot("end-user-dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}