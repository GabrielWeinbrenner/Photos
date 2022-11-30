package com.photos.model;

import java.io.Serializable;

import com.photos.shared.constants.USER_TYPE;

public abstract class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean validateLogin(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    } 
    public abstract USER_TYPE getType();

}
