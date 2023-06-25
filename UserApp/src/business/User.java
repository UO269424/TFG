package business;

import java.nio.file.Path;

public class User {

    private String name, ip;
    private ImageSequence previousSequence, currentSequence;

    private int counter;

    public User(String name, String ip) {
        counter = 0;
        this.name = name;
        this.ip = ip;
        previousSequence = new ImageSequence();
        currentSequence = new ImageSequence();
    }

    public void newImage(Path image)    {
        previousSequence = new ImageSequence(currentSequence);
        currentSequence.addImage(image);
        if(counter<3)
            counter++;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public ImageSequence getPreviousSequence() {
        return previousSequence;
    }

    public ImageSequence getCurrentSequence() {
        return currentSequence;
    }

    public boolean isSequenceComplete() {
        return counter >=3;
    }
}
