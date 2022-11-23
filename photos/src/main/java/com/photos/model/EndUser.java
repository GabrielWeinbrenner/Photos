package com.photos.model;

import java.util.ArrayList;

import com.photos.shared.constants.USER_TYPE;

public class EndUser extends User{
    private ArrayList<Album> albums;

    public EndUser(String username, String password) {
        super(username, password);
        albums = new ArrayList<Album>();
    }

    public ArrayList<Album> getAlbums() {
        return this.albums;
    }

    public Album createAlbum(String name) {
        Album newAlbum = new Album(name);
        albums.add(newAlbum);
        return newAlbum;
    }

    public Album renameAlbum(Album album, String newName) {
        albums.get(albums.indexOf(album)).rename(newName);
        return album;
    }

    public void deleteAlbum(Album album) {
        albums.remove(album);
    }

    @Override
    public USER_TYPE getType() {
        return USER_TYPE.EndUser;
    }

    
    
}
