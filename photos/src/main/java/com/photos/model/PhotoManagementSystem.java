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
import java.util.Date;

import com.photos.shared.constants;

import javafx.scene.image.Image;

public class PhotoManagementSystem implements Serializable {
    private User currentUser;
    private ArrayList<User> users;
    public static PhotoManagementSystem instance;
    public Photo createPhoto(String img, String caption) {
        File i = new File("file:images/"+img);
        Photo photo = new Photo(caption, new Image("file:images/"+img), new Date(i.lastModified()), new ArrayList<Tag>());
        return photo;
    }
    public PhotoManagementSystem() {
        if(instance == null) {
            try {
                if(!readApp()) {
                    users = new ArrayList<>();
                    EndUser stockUser = new EndUser("stock", "stock");
                    Album rutgersAlbum = stockUser.createAlbum("Rutgers");
                    Album summerAlbum = stockUser.createAlbum("Summer");
                    String[] rutgersPhotos = {"rutgers_sign.jpg", "rutgers_bus.jpg", "rutgers_busch.jpg", "rutgers_livingston.jpg", "rutgers_sidewalk.JPG"};
                    String[] rutgersCaption = {"Sign of Rutgers", "Bus to cook doug", "Busch Campus", "Livingston Capmus", "Rutgers Walkway"};
                    for(int i = 0; i < rutgersPhotos.length; i++) {
                        rutgersAlbum.addPhoto(createPhoto(rutgersPhotos[i], rutgersCaption[i]));
                    }
                    String[] summerPhotos = {"watermelons.jpg", "cloud.jpg", "beach.jpg", "sunflower.jpg"};
                    String[] summerCaptions = {"Watermelons", "Clouds", "Beach", "Sunflower"};
                    for(int i = 0; i < summerPhotos.length; i++) {
                        summerAlbum.addPhoto(createPhoto(summerPhotos[i], summerCaptions[i]));
                    }
                    users.add(stockUser);
                    users.add(new Adminstrator("admin", "admin"));
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
