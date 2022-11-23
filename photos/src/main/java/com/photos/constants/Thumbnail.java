package com.photos.constants;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Thumbnail {
    private StackPane thumbnail;
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

        headerText.setWrappingWidth(325); 
        subheaderText.setWrappingWidth(325);

        headerText.setFont(Font.font("Futura", FontWeight.BOLD, FontPosture.REGULAR, 45)); 
        subheaderText.setFont(Font.font("Futura", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
        subtitleText.setFont(Font.font("Futura", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 

        headerText.setTranslateY(20);
        subheaderText.setTranslateY(10);
        subtitleText.setTranslateY(-20);

        stack.getChildren().addAll(background, vbox, subtitleText);

        StackPane.setAlignment(headerText, Pos.TOP_CENTER);
        StackPane.setAlignment(subheaderText, Pos.TOP_CENTER);
        StackPane.setAlignment(subtitleText, Pos.BOTTOM_CENTER);

        thumbnail = stack;
    }

    public StackPane getThumbnail(){
        return thumbnail;
    }

}
