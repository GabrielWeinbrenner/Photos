package com.photos.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

public class Photo {
    private String caption;
    private ArrayList<String> tags = new ArrayList<>();
    private Date dateCreated = new Date();
    private Image image = new Image(constants.DEFAULT_IMAGE);

    /**
    * Creates a Photo that would store the caption, image, and date
    * @param  caption  the caption of the given photo
    * @param  image the image instance that will be stored in the photo class
    * @param  date the date the image was last modified
    * @see    Image, Album
    */
    public Photo(String caption, Image image, Date date) {
        this.caption = caption;
        this.image = image;
        this.dateCreated = date;
    }

    public String getCaption() {
        return caption;
    }
    public ArrayList<String> addTag(String tag) {
        tags.add(tag);
        return tags;
    }

    public ArrayList<String> deleteTag(String tag) {
        tags.remove(tags.indexOf(tag));
        return tags;
    }

    public Album copyPhoto(Album album) {
        album.addPhoto(this);
        return album;
    }

    public Photo recaption(String newCaption) {
        this.caption = newCaption;
        return this;
    }
    public Image getThumbnailImage() {
        return new Image(this.image.getUrl(), 325, 325, false, true);
    }
    public Image getImage() {
        return this.image;
    }

    public Date getDate() {
        return this.dateCreated;
    }
    public String getStringDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(constants.DEFAULT_DATE_FORMAT);
        return dateFormat.format(this.dateCreated);
    }
}
