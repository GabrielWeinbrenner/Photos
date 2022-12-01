package com.photos.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

/**
 * Album class with edit functions
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class Album implements Serializable {
    /**
     * current album name
     */
    private String albumName;
    /**
     * set up collections of photos
     */
    private ArrayList<Photo> photos;

    /**
     * set up all instances
     * @param name string
     */
    public Album(String name) {
        this.albumName = name;
        this.photos = new ArrayList<Photo>();
    }

    
    /** 
     * rename the album and update it
     * @param newName string
     */
    public void rename(String newName) {
        this.albumName = newName;
    }

    
    /** 
     * add the new photo into collection
     * @param photo Photo
     * @return the updated Album
     */
    public Album addPhoto(Photo photo) {
        this.photos.add(photo);
        return this;
    }

    
    /** 
     * remove the input photo 
     * @param photo Photo
     * @return the updated photos with the new pic removed
     */
    public Album removePhoto(Photo photo) {
        this.photos.remove(this.photos.indexOf(photo));
        return this;
    }

    
    /** 
     * get the album name
     * @return album name in String
     */
    public String getAlbumName() {
        return this.albumName;
    }

    
    /** 
     * get the size of album
     * @return size of album in int
     */
    public int getAlbumSize() {
        return this.photos.size();
    }

    
    /** 
     * return the current collection of photos
     * @return the collection in ArrayList<Photo>
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    
    /** 
     * return the thumbnail pic
     * @return the thumbnail pic
     */
    public Image getThumbnail() {
        if(this.photos.size() <= 0) {
            return new Image(constants.DEFAULT_IMAGE);
        }
        return this.photos.get(0).getImage();
    } 

    
    /** 
     * get the range of the time
     * @return a String consist of a range of time
     */
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

        SimpleDateFormat dateFormat = new SimpleDateFormat(constants.DEFAULT_DATE_FORMAT);
        String minDateString = dateFormat.format(minDate);
        String maxDateString = dateFormat.format(maxDate);
        if ( minDateString.equals(maxDateString)) {
            return minDateString;
        }
        return minDateString + " - " + maxDateString ;
    }

    
    /** 
     * get the last photo of the input photo
     * @param p a photo
     * @return Photo that is before the input photo
     */
    public Photo getPreviousPhoto(Photo p) {
        int index = this.photos.indexOf(p) - 1;
        if(index < 0) {
            index = this.photos.size() - 1;
        }
        return this.photos.get(index);
    }

    
    /** 
     * return the photo after the given
     * @param p a photo
     * @return a Photo after the given 
     */
    public Photo getNextPhoto(Photo p) {
        int index = this.photos.indexOf(p) + 1;
        if(index >= this.photos.size()) {
            index = 0;
        }
        return this.photos.get(index);

    }

    
    /** 
     * return current location
     * @param p photo
     * @return current location in String
     */
    public String getCurrentLocationString(Photo p)  {
        int index = this.photos.indexOf(p);
        return String.format("%d/%d", index+1, this.photos.size());
    }
}
