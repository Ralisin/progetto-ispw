package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.LoginDAO;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.CartManagement;
import it.ralisin.littlefarmers.utils.SessionManagement;

import java.sql.SQLException;

public class LoginSignUpController {
    public boolean login(LoginCredentialsBean credentials) throws DAOException, SQLException {
        User user = LoginDAO.getUser(credentials.getEmail(), credentials.getPassword());

        SessionManagement.getInstance().setUser(user);

        CartManagement instance = CartManagement.getInstance();
        if(user != null) instance.addProductList(CustomerDAO.getCart(user));

        return user != null;
    }

    public boolean signUp(LoginCredentialsBean credentials) throws DAOException {
        boolean result = LoginDAO.addUser(credentials.getEmail(), credentials.getPassword(), credentials.getRole());

        if(result) {
            User user = new User(credentials.getEmail(), credentials.getPassword(), credentials.getRole());

            SessionManagement.getInstance().setUser(user);
        }

        return result;
    }
}
