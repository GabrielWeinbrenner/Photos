package com.photos.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.photos.shared.constants.USER_TYPE;

/**
 * end user with edit functions
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */

public class EndUser extends User {
    /**
     * create array of albums
     */
    private ArrayList<Album> albums;

    /**
     * initlize the constructor
     * @param username string
     * @param password string
     */
    public EndUser(String username, String password) {
        super(username, password);
        albums = new ArrayList<Album>();
    }

    
    /** 
     * get all the albums in arraylist
     * @return all albums in ArrayList<Album>
     */
    public ArrayList<Album> getAlbums() {
        return this.albums;
    }
    
    
    /** 
     * get the amount of albums
     * @return amount of albums in int
     */
    public int getAlbumCount() {
        return this.albums.size();
    }

    
    /** 
     * make new album
     * @param name of new album in string
     * @return the new created Album 
     */
    public Album createAlbum(String name) {
        Album newAlbum = new Album(name);
        albums.add(newAlbum);
        return newAlbum;
    }

    
    /** 
     * change the name of given album with the given name
     * @param album target album
     * @param newName the new name of album
     * @return updated Album 
     */
    public Album renameAlbum(Album album, String newName) {
        albums.get(albums.indexOf(album)).rename(newName);
        return album;
    }

    
    /** 
     * delete album
     * @param album updated album
     */
    public void deleteAlbum(Album album) {
        albums.remove(album);
    }

    
    /** 
     * is user admin or end user
     * @return USER_TYPE
     */
    @Override
    public USER_TYPE getType() {
        return USER_TYPE.EndUser;
    }

    
    
}
