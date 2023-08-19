package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;

public class LoginCredentialsBean {
    private final String email;
    private final String password;
    private UserRole role;

    public LoginCredentialsBean(String email, String password) throws InvalidFormatException {
        checkEmail(email);
        checkPassword(password);

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    private void checkEmail(String s) throws InvalidFormatException {
        if(s.isEmpty() || s.isBlank())
            throw new InvalidFormatException("Invalid email format");
    }

    private void checkPassword(String s) throws InvalidFormatException {
        if(s.isEmpty() || s.isBlank())
            throw new InvalidFormatException("Invalid password format");
    }
}
