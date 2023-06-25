package business;

import java.util.ArrayList;
import java.util.List;

public class AlertHandler {

    private static AlertHandler instance = null;
    private List<Alert> alerts;

    public static AlertHandler getInstance()    {
        if(instance == null)
            instance = new AlertHandler();
        return instance;
    }

    private AlertHandler()  {
        this.alerts = new ArrayList<>();
    }

    public void addAlert(Alert alert)   {
        alerts.add(alert);
    }

    public Alert findLastAlertByUser(User u)  {
        for(int i = alerts.size()-1; i>=0; i--) {
            if((alerts.get(i).getUser().getName().equals(u.getName())))
                return alerts.get(i);
        }
        return null;
    }

    public List<Alert> notDisplayedAlerts() {
        List<Alert> notDisplayed = new ArrayList<>();
        for(Alert a : alerts)   {
            if(!a.isDisplayed())
                notDisplayed.add(a);
        }
        return notDisplayed;
    }

    public List<Alert> getAlerts() {
        return new ArrayList<>(alerts);
    }

    public void removeAlert(Alert a)    {
        if(alerts.contains(a))
            alerts.remove(a);
    }

    public void removeDisplayedAlerts() {
        for(Alert a : alerts)   {
            if(a.isDisplayed())
                removeAlert(a);
        }
    }
}
