package com.photos.model;

import java.util.ArrayList;
import java.util.Date;

public class Photo {
    private String caption;
    private ArrayList<String> tags;
    private Date dateCreated;

    public Photo(String caption) {
        this.caption = caption;
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

    public Date getDate() {
        return this.dateCreated;
    }

}
