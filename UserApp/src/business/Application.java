package business;

import util.ImageHandler;

import java.nio.file.Path;
import java.util.*;

public class Application implements Runnable{

    private Path new_image, old_image;
    private Set<User> users;
    private CommandExecutor commandExecutor;
    private List<Observer> observers = new ArrayList<>();

    public Application()    {
        users = new HashSet<>();
        commandExecutor = new CommandExecutor();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(new Observable(), new_image);
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

                if(u.isSequenceComplete())  {
                    evaluateSequence(u);
                    if(u.getCurrentSequence().isCheat())    {
                        if(!u.getPreviousSequence().isCheat())  {
                            Alert alert = new Alert(u);
                        }
                        else {
                            Alert alert = AlertHandler.getInstance().findLastAlertByUser(u);
                            alert.addImage(new_image);
                        }
                        notifyObservers();
                    }
                }
                old_image = new_image;
            }
        }

    }

    private void evaluateSequence(User u) {
        Path[] sequence = u.getCurrentSequence().getSecuencia();
        String evaluation = commandExecutor.execute(sequence);
        if(Integer.parseInt(evaluation) == 1)
            u.getCurrentSequence().setCheat(true);
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
