package com.photos.model;

import com.photos.constants.constants.USER_TYPE;

public class Adminstrator extends User {

    public Adminstrator(String username, String password) {
        super(username, password);
    }

    @Override
    public USER_TYPE getType() {
        return USER_TYPE.Admin;
    }

    
    
}
