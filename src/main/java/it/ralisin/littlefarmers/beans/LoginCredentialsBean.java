package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;

public class LoginCredentialsBean {
    private final String email;
    private final String password;
    private UserRole role;

    public LoginCredentialsBean(String email, String pwd) throws InvalidFormatException {
        checkEmail(email);
        checkPassword(pwd);

        this.email = email;
        this.password = pwd;
    }

    public LoginCredentialsBean(String email, String emailRep, String pwd, String pwdRep, UserRole role) throws InvalidFormatException {
        checkEmail(email, emailRep);
        checkPassword(pwd, pwdRep);

        this.email = email;
        this.password = pwd;
        this.role = role;
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

    private void checkEmail(String s1, String s2) throws InvalidFormatException {
        if(s1.isEmpty() || s1.isBlank() || !s1.equals(s2))
            throw new InvalidFormatException("Invalid email format");
    }

    private void checkPassword(String s) throws InvalidFormatException {
        if(s.isEmpty() || s.isBlank())
            throw new InvalidFormatException("Invalid password format");
    }

    private void checkPassword(String s1, String s2) throws InvalidFormatException {
        if(s1.isEmpty() || s1.isBlank() || !s1.equals(s2))
            throw new InvalidFormatException("Invalid password format");
    }
}
