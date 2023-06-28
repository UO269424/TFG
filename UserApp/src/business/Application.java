package business;

import business.observer.MyObserver;
import util.ImageHandler;

import java.nio.file.Path;
import java.util.*;

public class Application implements Runnable{

    private Path new_image, old_image;
    private Set<User> users;
    private CommandExecutor commandExecutor;
    private List<MyObserver> observers = new ArrayList<>();

    public Application()    {
        users = new HashSet<>();
        commandExecutor = new CommandExecutor();
    }

    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MyObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (MyObserver observer : observers) {
            observer.update();
        }
    }
    @Override
    public void run() {
        while (true) {
            boolean condition1= new_image!=null;
            boolean condition2= false;
            try {
                condition2=!old_image.equals(new_image);
            }
            catch (Exception e) {
                condition2 = old_image==null;
            }
            if(condition1 && condition2) {
                String userName = ImageHandler.getUserNameFromImage(new_image);
                String userIp = ImageHandler.getUserIPFromImage(new_image);
                User u = findUser(userName, userIp);

                if (u == null)    {
                    users.add(new User(userName, userIp));
                    u = findUser(userName, userIp);
                }

                u.newImage(new_image);

                evaluateSequence(u);
                if(u.getCurrentSequence().isCheat())    {
                    if(!u.getPreviousSequence().isCheat())  {
                        Alert alert = new Alert(u);
                    }
                    else {
                        Alert alert = AlertHandler.getInstance().findLastAlertByUser(u);
                        if(alert!=null)
                            alert.addImage(new_image);
                        else
                            alert = new Alert(u);
                    }
                    notifyObservers();
                }

                old_image = new_image;
            }
        }

    }

    private void evaluateSequence(User u) {
        Path image = u.getCurrentSequence().getSecuencia()[u.getCurrentSequence().getSecuencia().length-1];
        String evaluation = commandExecutor.execute(u.getName(), image);
        int result = 0;
        try {
            result = Integer.parseInt(evaluation);
        }
        catch(NumberFormatException e) {
            System.err.println(e.getMessage());
            return;
        }
        if( result == 1 && u.isSequenceComplete()) {
            System.out.println("Sequence completed flagged cheat");
            u.getCurrentSequence().setCheat(true);
        }
        else
            System.out.println("Sequence incomplete or not cheat");

    }


    public Path getNew_image() {
        return new_image;
    }

    public Set<User> getUsers() {
        return users;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setNew_image(Path new_image) {
        this.new_image = new_image;
    }

    public boolean addUser(User user)  {
        return users.add(user);
    }

    User findUser(String name, String ip)
    {
        for (User u : users) {
            if ((u.getName().equals(name)) && (u.getIp().equals(ip)))
                return u;
        }

        return null;
    }
}
