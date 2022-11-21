package com.photos.model;

import com.photos.constants.constants.USER_TYPE;

public abstract class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean validateLogin(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    } 
    public abstract USER_TYPE getType();

}
