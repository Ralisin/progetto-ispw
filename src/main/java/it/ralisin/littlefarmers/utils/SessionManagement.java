package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.patterns.Observable;
import it.ralisin.littlefarmers.patterns.Observer;

import java.util.ArrayList;
import java.util.List;

public class SessionManagement implements Observable {
    private static SessionManagement instance = null;
    private List<Observer> observerList = new ArrayList<>();
    private User user;

    private SessionManagement() {
        user = null;
    }

    public static synchronized SessionManagement getInstance() {
        if(SessionManagement.instance == null) instance = new SessionManagement();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        notifyObservers();
    }

    public void clear() {
        this.user = null;
        this.observerList = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if(observerList != null) for(Observer o : observerList) o.update();
    }
}
