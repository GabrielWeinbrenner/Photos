package com.photos.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.photos.shared.constants;

import javafx.scene.image.Image;

/**
 * photo with functions to edit
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class Photo implements Serializable{
    /**
     * set up captions
     */
    private String caption;
    /**
     * new empty arraylist to store tags
     */
    private ArrayList<Tag> tags = new ArrayList<>();
    /**
     * set up new date
     */
    private Date dateCreated = new Date();
    /** 
     * empty url
     * 
     */
    private String url = "";
    /** 
     * set up image
     */
    private transient Image image = new Image(constants.DEFAULT_IMAGE);

    /**
     * Creates a Photo that would store the caption, image, and date
     * 
     * @param caption the caption of the given photo
     * @param image   the image instance that will be stored in the photo class
     * @param date    the date the image was last modified
     * @param tags the given tags
     * @see Image, Album
     */
    public Photo(String caption, Image image, Date date, ArrayList<Tag> tags) {
        this.caption = caption;
        this.image = image;
        this.dateCreated = date;
        this.tags = tags;
        this.url = image.getUrl();
    }

    
    /** 
     * return captions
     * @return captions in String
     */
    public String getCaption() {
        return caption;
    }

    
    /** 
     * add new tag in tag arraylist
     * @param tag the new tag to be added
     * @return the updated ArrayList<Tag>
     */
    public ArrayList<Tag> addTag(Tag tag) {
        tags.add(tag);
        return tags;
    }

    
    /** 
     * delete the given tag
     * @param tag to be deleted
     * @return the new collections of tags in ArrayList<Tag> 
     */
    public ArrayList<Tag> deleteTag(Tag tag) {
        tags.remove(tags.indexOf(tag));
        return tags;
    }

    
    /** 
     * copy photos over
     * @param album to be copied
     * @return the updated Album
     */
    public Album copyPhoto(Album album) {
        album.addPhoto(this);
        return album;
    }

    
    /** 
     * return the image of thumbnail
     * @return thumbnail Image
     */
    public Image getThumbnailImage() {
        return new Image(this.url, 325, 325, false, true);
    }

    
    /** 
     * return the current photo
     * @return current photo of the Image
     */
    public Image getImage() {
        if(this.image == null) {
            this.image = new Image(url);
        }
        return this.image;
    }

    
    /** 
     * return the current date
     * @return the current Date
     */
    public Date getDate() {
        return this.dateCreated;
    }

    
    /** 
     * return the date in string
     * @return the date in String
     */
    public String getStringDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(constants.DEFAULT_DATE_FORMAT);
        return dateFormat.format(this.dateCreated);
    }

    
    /** 
     * return all tags
     * @return the tags in ArrayList<Tag>
     */
    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    
    /** 
     * edit the new photo
     * @param caption the caption of the given photo
     * @param image the image instance that will be stored in the photo class
     * @param imageDate new image modifed Date
     * @param tags new given tags
     */
    public void editPhoto(String caption, Image image, Date imageDate, ArrayList<Tag> tags) {
        this.caption = caption;
        this.image = image;
        this.dateCreated = imageDate;
        this.tags = tags;
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }
}
