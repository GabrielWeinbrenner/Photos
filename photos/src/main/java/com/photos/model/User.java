package com.photos.model;

import java.io.Serializable;

import com.photos.shared.constants.USER_TYPE;

/**
 * set up user structure
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */

public abstract class User implements Serializable {
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /** 
     * constructor
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    /** 
     * return user name
     * @return usernaame in String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * return password 
     * @return password in String
     */
    public String getPassword() {
        return password;
    }

    
    /** check if user info is valid to log in 
     * @param username username
     * @param password password
     * @return can the user log in? boolean
     */
    public boolean validateLogin(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    } 
    
    public abstract USER_TYPE getType();

}
