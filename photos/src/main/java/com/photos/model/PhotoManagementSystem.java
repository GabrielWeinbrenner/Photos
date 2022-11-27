package com.photos.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.photos.shared.constants;

public class PhotoManagementSystem implements Serializable {
    private User currentUser;
    private ArrayList<User> users;
    public static PhotoManagementSystem instance;

    public PhotoManagementSystem() {
        if(instance == null) {
            try {
                if(!readApp()) {
                    users = new ArrayList<>();
                    users.add(new EndUser("h", "h"));
                    instance = this;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeApp(PhotoManagementSystem pms) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(constants.STORE_DIR + File.separator + constants.STORE_FILE)
        );
        oos.writeObject(pms);
        oos.close();
    }

    public static boolean readApp() throws IOException, ClassNotFoundException {
        try {
            File file = new File(constants.STORE_DIR + File.separator + constants.STORE_FILE);
            if(file.exists()){
                ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file)
                );
                instance = (PhotoManagementSystem) ois.readObject();
                ois.close();
                return true;
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return false;
            }
        } catch (Error e) {
            System.out.println(e);
        }
        return true;
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
