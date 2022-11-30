package com.photos.model;

import java.util.ArrayList;

import com.photos.shared.constants.USER_TYPE;

public class Adminstrator extends User {
    PhotoManagementSystem ps = new PhotoManagementSystem();
    PhotoManagementSystem PSInstance = PhotoManagementSystem.instance;

    public Adminstrator(String username, String password) {
        super(username, password);
    }
    @Override
    public USER_TYPE getType() {
        return USER_TYPE.Admin;
    }

    
    
}
