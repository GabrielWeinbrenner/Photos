package com.photos.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

/**
 * the master photo management system class with functions
 * 
 * @author Gabe Weinbrenner gcw35
 * @author Zihe Zhang zz475
 */
public class PhotoManagementSystem implements Serializable {
    /** 
     * current user
     */
    private User currentUser;
    /**
     * all users in database
     */
    private ArrayList<User> users;
    /**
     * new PhotoManagementSystem instance
     */
    public static PhotoManagementSystem instance;
    public Photo createPhoto(String img, String caption) {
        File i = new File("file:images/" + img);
        Photo photo = new Photo(caption, new Image("file:images/" + img), new Date(i.lastModified()),
                new ArrayList<Tag>());
        return photo;
    }

    public PhotoManagementSystem() {
        if (instance == null) {
            try {
                if (!readApp()) {
                    users = new ArrayList<>();
                    EndUser stockUser = new EndUser("stock", "stock");

                    try {
                        Album stockAlbum;
                        stockAlbum = stockUser.createAlbum("stock");
                        String[] rutgersPhotos = { "rutgers_sign.jpg", "rutgers_bus.jpg", "rutgers_busch.jpg",
                                "rutgers_livingston.jpg", "rutgers_sidewalk.JPG" };
                        String[] rutgersCaption = { "Sign of Rutgers", "Bus to cook doug", "Busch Campus",
                                "Livingston Capmus", "Rutgers Walkway" };
                        for (int i = 0; i < rutgersPhotos.length; i++) {
                            stockAlbum.addPhoto(createPhoto(rutgersPhotos[i], rutgersCaption[i]));
                        }
                        String[] summerPhotos = { "watermelons.jpg", "cloud.jpg", "beach.jpg", "sunflower.jpg" };
                        String[] summerCaptions = { "Watermelons", "Clouds", "Beach", "Sunflower" };
                        for (int i = 0; i < summerPhotos.length; i++) {
                            stockAlbum.addPhoto(createPhoto(summerPhotos[i], summerCaptions[i]));
                        }
                        users.add(stockUser);
                        users.add(new Adminstrator("admin", "admin"));
                        instance = this;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeApp(PhotoManagementSystem pms) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(constants.STORE_DIR + File.separator + constants.STORE_FILE));
        oos.writeObject(pms);
        oos.close();
    }

    
    /** 
     * process app
     * @return if the app functioning boolean
     * @throws IOException any error
     * @throws ClassNotFoundException not found error
     */
    public static boolean readApp() throws IOException, ClassNotFoundException {
        try {
            File file = new File(constants.STORE_DIR + File.separator + constants.STORE_FILE);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(file));
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

    
    /** 
     * return all users in database
     * @return all users in ArrayList<User>
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    
    /** 
     * log in user
     * @param username username
     * @param password password
     * @return logedin User
     * @throws Exception error if not logged in 
     */
    public User login(String username, String password) throws Exception {
        for (User user : users) {
            if (user.validateLogin(username, password)) {
                this.currentUser = user;
                return user;
            }
        }
        throw new Exception("Invalid username or password");
    }

    
    /** 
     * log out the current user
     * @throws Exception error
     */
    public void logout() throws Exception {
        if (!currentUser.equals(null)) {
            currentUser = null;
            return;
        }
        throw new Exception("No user is logged in");
    }

    public void createUser(String username, String password) {
        users.add(new EndUser(username, password));
    }

    public void removeUser(EndUser currentSelectedUser) {
        users.remove(currentSelectedUser);
    }

}
