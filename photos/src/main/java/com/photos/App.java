package com.photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import com.photos.constants.Controller;
import com.photos.constants.constants;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Scene popup;
    public static Stage popupStage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), constants.WIDTH, constants.HEIGHT);
        popup = new Scene(loadFXML("login"), constants.FORM_WIDTH, constants.FORM_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, Controller controller, Object obj) throws IOException {
        controller.setData(obj);
        scene.setRoot(loadFXML(fxml, controller));
    }

    public static void setPopup(String fxml, Controller controller, Object obj) throws IOException {
        controller.setData(obj);
        if(popupStage == null) {
            popupStage = new Stage();
        }
        popupStage.setScene(popup);
        popup.setRoot(loadFXML(fxml,controller));
        popupStage.show();
    }
    public static void closePopup() {
        popupStage.close();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch();
    }



}