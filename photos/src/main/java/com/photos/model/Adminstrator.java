package com.photos.model;

import java.util.ArrayList;

import com.photos.shared.constants.USER_TYPE;

/**
 * Administrator class 
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */

public class Adminstrator extends User {
    /**
     * create new PhotoManagementSystem
     */
    PhotoManagementSystem ps = new PhotoManagementSystem();
    /**
     * create instance of PhotoManagementSystem
     */
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;

    /**
     * Constructor
     * @param username string
     * @param password string
     * it inherets the User class
     */
    public Adminstrator(String username, String password) {
        super(username, password);
    }
    
    /** 
     * @return the USER_TYPE to determine whether the user is an admin or not
     */
    @Override
    public USER_TYPE getType() {
        return USER_TYPE.Admin;
    }

    
    
}
