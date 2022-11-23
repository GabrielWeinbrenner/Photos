package com.photos.model;

import java.util.ArrayList;
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

public class Album {
    private String albumName;
    private ArrayList<Photo> photos;

    public Album(String name) {
        this.albumName = name;
        this.photos = new ArrayList<Photo>();
    }

    public void rename(String newName) {
        this.albumName = newName;
    }

    public Album addPhoto(Photo photo) {
        this.photos.add(photo);
        return this;
    }

    public Album removePhoto(Photo photo) {
        this.photos.remove(this.photos.indexOf(photo));
        return this;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public int getAlbumSize() {
        return this.photos.size();
    }

    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }
    public Image getThumbnail() {
        if(this.photos.size() <= 0) {
            return new Image(constants.DEFAULT_IMAGE);
        }
        return this.photos.get(0).getImage();
    } 
    public String getDateRange() {
        if(this.photos.size() <= 0) {
            return "No Date Range";
        }
        Date minDate = this.photos.get(0).getDate();
        Date maxDate = this.photos.get(0).getDate();
        for(Photo i : this.photos)  {
            Date currPhotoDate = i.getDate();
            if(maxDate.before(currPhotoDate)) {
                maxDate = currPhotoDate;
            } 
            if(minDate.after(currPhotoDate)) {
                minDate = currPhotoDate;
            }
        }
        return minDate.toString() + " - " + maxDate.toString();
    }

}
