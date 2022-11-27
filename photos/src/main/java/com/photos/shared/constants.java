package com.photos.shared;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public final class constants {
    public static final int WIDTH = 1212;
    public static final int HEIGHT = 682;
    public static final int FORM_HEIGHT = 900;
    public static final int FORM_WIDTH = 500;
    public static final String DEFAULT_IMAGE = "file:images/default.jpg";
    public static final String DEFAULT_DATE_FORMAT = "MMM d, yyyy";
    public static final String STORE_DIR = "dat";
    public static final String STORE_FILE = "pms.dat";

    public static enum USER_TYPE {
        EndUser,
        Admin,
    }
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
