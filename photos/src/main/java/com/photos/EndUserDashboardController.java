package com.photos;

import java.io.IOException;
import java.util.ArrayList;

import com.photos.model.PhotoManagementSystem;
import com.photos.model.EndUser;
import com.photos.model.Album;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class EndUserDashboardController {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;
    EndUser currentUser = (EndUser) PSInstance.getCurrentUser();

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
    @FXML 
    public void initialize() {
        setGrid(currentUser.getAlbums());
    }
    private void setGrid(ArrayList<Album> albums) {
        this.gridPane = new GridPane();
        this.gridPane.setHgap(25);
        this.gridPane.setVgap(25);
        this.gridPane.setPadding(new Insets(0,75,0,75));
        int j = 0;
        for(int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            if(i%COLUMNS == 0) {
                j+=1;
            }
            StackPane stack = new StackPane();
            Rectangle background = new Rectangle(325, 325);
            Image img = new Image("file:images/beach.jpg");
            background.setFill(new ImagePattern(img));
            Text albumName = new Text(album.getAlbumName());
            Text albumLength = new Text(album.getAlbumSize() + " photos");
            Text albumDate = new Text(album.getDateRange());

            VBox vbox = new VBox(albumName, albumDate);
            albumName.setTextAlignment(TextAlignment.CENTER);
            albumDate.setTextAlignment(TextAlignment.CENTER);

            albumName.setWrappingWidth(325); 
            albumDate.setWrappingWidth(325);

            albumName.setFont(Font.font("Futura", FontWeight.BOLD, FontPosture.REGULAR, 45)); 
            albumLength.setFont(Font.font("Futura", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
            albumDate.setFont(Font.font("Futura", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 

            albumName.setTranslateY(20);
            albumLength.setTranslateY(-20);
            albumDate.setTranslateY(20);

            stack.getChildren().addAll(background, vbox, albumLength);

            StackPane.setAlignment(albumName, Pos.TOP_CENTER);
            StackPane.setAlignment(albumDate, Pos.TOP_CENTER);
            StackPane.setAlignment(albumLength, Pos.BOTTOM_CENTER);

            GridPane.setConstraints(stack, i%COLUMNS, j);

            this.gridPane.getChildren().add(stack);
        }
        albumScroll.setContent(gridPane);

    }
    @FXML
    private void searchAlbumAction() throws IOException {
    }

    @FXML
    private void createAlbumAction() throws IOException {
        
        currentUser.createAlbum("Summer Days on Beach");
        ArrayList<Album> albums = currentUser.getAlbums();
        setGrid(albums);
    }

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
}
