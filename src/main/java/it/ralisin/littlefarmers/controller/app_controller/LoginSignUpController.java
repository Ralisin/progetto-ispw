package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.LoginDAO;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.sql.SQLException;

public class LoginSignUpController {
    public boolean login(LoginCredentialsBean credentials) throws DAOException, SQLException {
        User user = LoginDAO.getUser(credentials.getEmail(), credentials.getPassword());

        SessionManager.getInstance().setUser(user);

        CartController instance = CartController.getInstance();
        if(user != null) {
            CartBean cartBean = new CartBean();
            cartBean.setProductList(CustomerDAO.getCart(user));

            instance.addProductList(cartBean);
        }

        return user != null;
    }

    public boolean signUp(LoginCredentialsBean credentials) throws DAOException {
        boolean result = LoginDAO.addUser(credentials.getEmail(), credentials.getPassword(), credentials.getRole());

        if(result) {
            User user = new User(credentials.getEmail(), credentials.getPassword(), credentials.getRole());

            SessionManager.getInstance().setUser(user);
        }

        return result;
    }
}
