package com.photos.model;

import java.util.ArrayList;
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

public class Photo {
    private String caption;
    private ArrayList<String> tags = new ArrayList<>();
    private Date dateCreated = new Date();
    private Image image = new Image(constants.DEFAULT_IMAGE);

    public Photo(String caption) {
        this.caption = caption;
    }

    public Photo(String caption, Image image) {
        this.caption = caption;
        this.image = image;
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

    public Image getImage() {
        return this.image;
    }
    public Date getDate() {

        return this.dateCreated;
    }

}
