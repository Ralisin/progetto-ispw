package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.model.User;

public class SessionSingleton {
    private static SessionSingleton instance = null;
    private User user;

    private SessionSingleton() {
        user = null;
    }

    public static synchronized SessionSingleton getInstance() {
        if(SessionSingleton.instance == null)
            SessionSingleton.instance = new SessionSingleton();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getUserRole() {
        return user.getRole();
    }
}
