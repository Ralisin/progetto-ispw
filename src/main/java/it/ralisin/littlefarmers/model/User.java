package it.ralisin.littlefarmers.model;

public class User {
    private final String email;
    private final String pass;
    private final String role;

    public User(String email, String pass, String role) {
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPass() {
        return this.pass;
    }

    public String getRole() {
        return this.role;
    }

     @Override
    public String toString() {
        return String.format("User [\"email\": %s, \"pass\": %s, \"role\": %s]", this.email, this.pass, this.role);
    }
}
