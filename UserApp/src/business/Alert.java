package business;

import util.ImageHandler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alert {

    private List<Path> images= new ArrayList<>();
    private User user;

    private boolean displayed = false;

    public Alert(User u) {
        this.user = u;
        this.images = Arrays.asList(u.getCurrentSequence().getSecuencia());
        AlertHandler.getInstance().addAlert(this);

    }

    public void addImage(Path path)   {
        images.add(path);
    }

    public List<Path> getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}