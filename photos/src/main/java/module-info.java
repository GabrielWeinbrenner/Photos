module com.photos {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.photos to javafx.fxml;
    opens com.photos.controllers to javafx.fxml;
    exports com.photos;
    exports com.photos.model;
}
