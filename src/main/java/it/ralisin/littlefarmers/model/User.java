package it.ralisin.littlefarmers.model;

import it.ralisin.littlefarmers.enums.UserRole;

public class User {
    private final String email;
    private final String pass;
    private final UserRole role;

    public User(String email, String pass, UserRole role) {
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("User [\"email\": %s, \"pass\": %s, \"role\": %s]", this.email, this.pass, this.role);
    }
}
