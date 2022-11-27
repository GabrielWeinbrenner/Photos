package com.photos.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.photos.shared.constants;

import javafx.scene.image.Image;

public class Photo {
    private String caption;
    private ArrayList<Tag> tags = new ArrayList<>();
    private Date dateCreated = new Date();
    private Image image = new Image(constants.DEFAULT_IMAGE);

    /**
     * Creates a Photo that would store the caption, image, and date
     * 
     * @param caption the caption of the given photo
     * @param image   the image instance that will be stored in the photo class
     * @param date    the date the image was last modified
     * @param tags2
     * @see Image, Album
     */
    public Photo(String caption, Image image, Date date, ArrayList<Tag> tags) {
        this.caption = caption;
        this.image = image;
        this.dateCreated = date;
        this.tags = tags;
    }

    public String getCaption() {
        return caption;
    }

    public ArrayList<Tag> addTag(Tag tag) {
        tags.add(tag);
        return tags;
    }

    public ArrayList<Tag> deleteTag(Tag tag) {
        tags.remove(tags.indexOf(tag));
        return tags;
    }

    public Album copyPhoto(Album album) {
        album.addPhoto(this);
        return album;
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

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    public void editPhoto(String caption, Image image, Date imageDate, ArrayList<Tag> tags) {
        this.caption = caption;
        this.image = image;
        this.dateCreated = imageDate;
        this.tags = tags;
    }
}
