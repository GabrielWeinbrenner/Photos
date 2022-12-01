package com.photos.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.photos.App;
import com.photos.model.Album;
import com.photos.model.Photo;
import com.photos.model.Tag;
import com.photos.shared.Controller;
import com.photos.shared.CreationEventListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * controller for forms to edit photos
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class PhotoFormController extends Controller {
    private enum STATE {
        EDIT,
        CREATE
    }

    /**
     * listen for the new event
     */
    private CreationEventListener listener;
    /** 
     * current state
     */
    private STATE state;
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
    @FXML
    TableView<Tag> tagsTableView;
    @FXML
    TableColumn<Tag, String> tagsNameTableView;
    @FXML
    TableColumn<Tag, String> tagsValueTableView;

    Image image;
    Date imageDate;
    Photo currentPhoto;

    
    /** 
     * add new event listenser
     * @param listener listener
     */
    public void setAddPhoto(CreationEventListener listener) {
        this.listener = listener;
    }

    
    /** 
     * update all the album data
     * @param obj new data
     */
    @Override
    public void setData(Object... obj) {
        for(int i = 0; i < obj.length; i++) {
            if("Album".equals(obj[i].getClass().getSimpleName())) {
                this.currentAlbum = (Album) obj[i];
            } else if("Photo".equals(obj[i].getClass().getSimpleName())) {
                this.currentPhoto = (Photo) obj[i];
                this.state = STATE.EDIT;
            }
        }
        if(currentPhoto == null) {
            this.state = STATE.CREATE;
        }
    }

    
    /** 
     * update the new contents for the table data
     * @param tagList  arraylist of tags
     */
    public void setTableContent(ArrayList<Tag> tagList) {
        ObservableList<Tag> data = FXCollections.<Tag>observableArrayList();
        data.addAll(tagList);
        tagsTableView.setItems(data);
    }

    /**
     * update the displaying UI
     */
    public void refreshRow() {
        this.tagsNameTableView.setCellValueFactory(new PropertyValueFactory<Tag, String>("tagName"));
        this.tagsValueTableView.setCellValueFactory(new PropertyValueFactory<Tag, String>("tagValue"));

        tagsNameTableView.setCellFactory(TextFieldTableCell.<Tag>forTableColumn()); 
        tagsNameTableView.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setTagName(e.getNewValue()));

        tagsValueTableView.setCellFactory(TextFieldTableCell.<Tag>forTableColumn()); 
        tagsValueTableView.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setTagValue(e.getNewValue()));
    }
    /**
     * set up the page with UI
     */
    @FXML
    private void initialize() {
        tagsTableView.setPlaceholder(
            new Label("No tags added"));
        if(this.state == STATE.EDIT) {
            setTableContent(currentPhoto.getTags());
            this.addButton.setText("Edit");
            this.captionArea.setText(currentPhoto.getCaption());
            this.photoPreviewer.setImage(currentPhoto.getImage());
            refreshRow();
            this.image = currentPhoto.getImage();
            this.imageDate = currentPhoto.getDate();

        }
        tagsTableView.setEditable(true);
    }
    
    /** 
     * add the new edited magic photo
     * @throws Exception error
     */
    @FXML
    private void magicPhotoAction() throws Exception {
        Alert errorAlert = new Alert(AlertType.ERROR);
        if(this.image == null) {
            errorAlert.setHeaderText("No Photo Provided");
            errorAlert.showAndWait();        
        } else {
            ArrayList<Tag> tags = new ArrayList<>(tagsTableView.getItems());
            if(this.state == STATE.EDIT) {
                currentPhoto.editPhoto(captionArea.getText(), this.image, this.imageDate, tags);
            } else {
                Photo newPhoto = new Photo(captionArea.getText(), this.image, imageDate, tags);
                currentAlbum.addPhoto(newPhoto);
            }
            listener.onMagicPhoto(); 
            App.closePopup();
        }
    }

    /**
     * add new tags
     */
    @FXML
    private void addTagAction() {
        tagsTableView.getItems().add(new Tag("", ""));
        refreshRow();
    }

    @FXML
    private void removeTagAction() {
        Tag tag = tagsTableView.getSelectionModel().getSelectedItem();
        setTableContent(currentPhoto.getTags());
        currentPhoto.removeTag(tag);
        refreshRow();
    }

    @FXML
    private void cancelCreatePhotoAction() throws IOException {
        App.closePopup();
    }

    
    /** 
     * upload new photos to album
     * @throws IOException error
     */
    @FXML
    private void uploadPhotoAction() throws IOException {
        Alert errorAlert = new Alert(AlertType.ERROR);
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(App.popupStage);
            long milli = selectedFile.lastModified();
            this.imageDate = new Date(milli);
            this.image = new Image(selectedFile.toURI().toString());
            photoPreviewer.setImage(this.image);
        } catch (Exception e) {
            errorAlert.setHeaderText("Error in uploading file");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }

    }

}
