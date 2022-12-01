package com.photos.shared;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Useful constants
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public final class constants {
    /**
     * window width
     */
    public static final int WIDTH = 1212;
    /**
     * window height
     */
    public static final int HEIGHT = 682;
    /**
     * form size
     */
    public static final int FORM_HEIGHT = 900;
    /**
     * form width
     */
    public static final int FORM_WIDTH = 500;
    /**
     * default image 
     */
    public static final String DEFAULT_IMAGE = "file:images/default.jpg";
    /**
     * set up date format
     */
    public static final String DEFAULT_DATE_FORMAT = "MMM d, yyyy";
    /**
     * store the input
     */
    public static final String STORE_DIR = "dat";
    /**
     * put all stored info into dat folder
     */
    public static final String STORE_FILE = "pms.dat";

    public static enum USER_TYPE {
        EndUser,
        Admin,
    }
    
    /** 
     * change the cursor shape when hover cover
     * @param component the area where we hover over
     */
    public static void setHoverComponent(Node component) {
        if(component == null) {
            return;
        }
        component.setOnMouseEntered(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    component.getScene().setCursor(Cursor.HAND);
                }
            }
        );
        component.setOnMouseExited(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    component.getScene().setCursor(Cursor.DEFAULT);
                }
            }
        );
    }
}
