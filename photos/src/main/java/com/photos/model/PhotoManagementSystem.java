package com.photos.model;

import java.util.ArrayList;

public class PhotoManagementSystem {
    private User currentUser = null;
    private ArrayList<User> users;
    public static PhotoManagementSystem instance;

    public PhotoManagementSystem() {
        users = new ArrayList<User>();
        User newUser = new EndUser("h", "h");
        users.add(newUser);
        if(instance == null) {
            instance = this;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    } 

    public User login(String username, String password) throws Exception {
        for (User user : users) {
            if(user.validateLogin(username, password)) {
                this.currentUser = user;
                return user;
            }
        }
        throw new Exception("Invalid username or password");
    }

    public void logout() throws Exception {
        if(!currentUser.equals(null)) {
            currentUser = null;
            return;
        }
        throw new Exception("No user is logged in");
    }
    
}
