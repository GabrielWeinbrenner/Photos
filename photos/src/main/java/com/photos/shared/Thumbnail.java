package com.photos.shared;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

/**
 * template for thumbnail
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class Thumbnail {
    private StackPane thumbnail;
    /**
     * make all UI changes
     * @param width window width
     * @param height window height
     * @param header string header text
     * @param subheader string secondary header
     * @param subtitle string subs
     * @param image incoming image
     */
    public Thumbnail(int width, int height, String header, String subheader, String subtitle, Image image){
        StackPane stack = new StackPane();
        Rectangle background = new Rectangle(325, 325);
        background.setFill(new ImagePattern(image));
        Text headerText = new Text(header);
        Text subheaderText = new Text(subheader);
        Text subtitleText = new Text(subtitle);

        VBox vbox = new VBox(headerText, subheaderText);
        headerText.setTextAlignment(TextAlignment.CENTER);
        subheaderText.setTextAlignment(TextAlignment.CENTER);
        subtitleText.setTextAlignment(TextAlignment.CENTER);

        headerText.setFill(Color.WHITE);
        headerText.setStrokeWidth(2);
        headerText.setStroke(Color.BLACK);

        subheaderText.setFill(Color.WHITE);
        subheaderText.setStrokeWidth(1.1);
        subheaderText.setStroke(Color.BLACK);
        subtitleText.setFill(Color.WHITE);
        subtitleText.setStrokeWidth(1.1);
        subtitleText.setStroke(Color.BLACK);

        headerText.setWrappingWidth(325); 
        subheaderText.setWrappingWidth(325);
        subtitleText.setWrappingWidth(325);

        headerText.setFont(Font.font("Futura", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 45)); 
        subheaderText.setFont(Font.font("Futura", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25)); 
        subtitleText.setFont(Font.font("Futura", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25)); 

        headerText.setTranslateY(20);
        subheaderText.setTranslateY(5);
        subtitleText.setTranslateY(-20);

        stack.getChildren().addAll(background, vbox, subtitleText);

        StackPane.setAlignment(headerText, Pos.TOP_CENTER);
        StackPane.setAlignment(subheaderText, Pos.TOP_CENTER);
        StackPane.setAlignment(subtitleText, Pos.BOTTOM_CENTER);

        thumbnail = stack;
    }

    
    /** 
     * set up the set button for new thumbnail
     * @param text input string
     * @param event new event
     * @return the updated StackPane with thumbnail
     */
    public StackPane setButton(String text, EventHandler<MouseEvent> event) {
        Button delete = new Button("Delete");
        thumbnail.getChildren().add(delete);
        delete.setOnMouseClicked(event);  
        StackPane.setAlignment(delete, Pos.BOTTOM_RIGHT);
        return thumbnail;
    }

    
    /** 
     * set up remove button
     * @return the new StackPane
     */
    public StackPane removeButtons() {
        ObservableList<Node> children = thumbnail.getChildren();

        for(int i = 0; i < children.size(); i++){
            Object currChild = children.get(i);
            if(currChild.getClass().getSimpleName().equals("Button")){
                children.remove(children.get(i));
            }
        }
        return thumbnail;
    }

    
    /** 
     * return thumbnail
     * @return thumbnail in StackPane
     */
    public StackPane getThumbnail(){
        return thumbnail;
    }

}
