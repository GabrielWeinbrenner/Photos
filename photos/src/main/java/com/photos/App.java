package com.photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.photos.model.PhotoManagementSystem;
import com.photos.shared.Controller;
import com.photos.shared.constants;

/**
 * JavaFX App
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class App extends Application {
    /**
     * set up new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
     * set up new instance for PhotoManagementSystem
     */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;

    public static Scene scene;
    private static Scene popup;
    public static Stage popupStage;



    
    /** 
     * start running the app
     * @param stage initial stage
     * @throws Exception error
     */
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("login"), constants.WIDTH, constants.HEIGHT);
        popup = new Scene(loadFXML("login"), constants.FORM_WIDTH, constants.FORM_HEIGHT);
        // PhotoManagementSystem.readApp();
        stage.setScene(scene);
        stage.show();
    }

    
    /** 
     * stop
     * @throws Exception error
     */
    @Override
    public void stop() throws Exception {
        PhotoManagementSystem.writeApp(PSInstance);
    }

    
    /** 
     * make up scenes
     * @param fxml basic template
     * @throws IOException error
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    
    /** 
     * make up scenes with data
     * @param fxml given template
     * @param controller controls with functions
     * @param arg0 new data
     * @throws IOException error
     */
    public static void setRoot(String fxml, Controller controller, Object... arg0) throws IOException {
        controller.setData(arg0);
        scene.setRoot(loadFXML(fxml, controller));
    }

    
    /** 
     * set up pop up windows
     * @param fxml basic template
     * @param controller controls with functions
     * @param arg0 new data
     * @throws IOException error
     */
    public static void setPopup(String fxml, Controller controller, Object... arg0) throws IOException {
        controller.setData(arg0);
        if(popupStage == null) {
            popupStage = new Stage();
        }
        popupStage.setScene(popup);
        popup.setRoot(loadFXML(fxml,controller));
        popupStage.show();
        popupStage.setHeight(constants.FORM_HEIGHT);
    }

    
    /** 
     * set new pop up
     * @param fxml template
     * @param controller controls with function listeners
     * @param obj new data piece
     * @param height size
     * @throws IOException error
     */
    public static void setPopup(String fxml, Controller controller, Object obj, int height) throws IOException {
        setPopup(fxml, controller, obj);
        popupStage.setHeight(height);
    }

    /**
     * clsoe pop up
     */
    public static void closePopup() {
        popupStage.close();
    }

    
    /** 
     * make the actual scenes
     * @param fxml template
     * @return Parent parent template
     * @throws IOException error
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    
    /** 
     * loading content
     * @param fxml template
     * @param controller controller with functions
     * @return Parent parent of template
     * @throws IOException error
     */
    private static Parent loadFXML(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    
    /** 
     * start
     * @param args start
     */
    public static void main(String[] args) {
        launch();
    }

}