package business;

import util.ImageHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Alert {

    private List<Path> images;
    private User user;

    private boolean displayed = false;

    public Alert(User u) {
        this.user = u;
        this.images= new CopyOnWriteArrayList<>();
        for (Path path: u.getCurrentSequence().getSecuencia()) {
            addImage(path);
        }
        AlertHandler.getInstance().addAlert(this);

    }

    public void addImage(Path path)   {
        String s = String.valueOf(path);
        StringBuilder sb = new StringBuilder();
        String[] path_split = s.split("\\\\");
        if(path_split[path_split.length-2].equals("miniaturas"))  {
            for(int i =0; i< path_split.length; i++)    {
                if(i!= path_split.length)   {
                    sb.append(path_split[i]);
                    sb.append("\\");
                }
            }
            s = sb.toString();
        }
        images.add(Paths.get(s));
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
