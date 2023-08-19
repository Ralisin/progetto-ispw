package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.controller.app_controller.LoginSignUpController;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import it.ralisin.littlefarmers.utils.SessionSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSignUpHomeControllerGUI extends AbsCustomerGraphicController {
    @FXML
    TextField emailTextField;
    @FXML
    TextField pwdTextField;
    @FXML
    Button accessBtn;

    private LoginSignUpController loginSignUpController;

    public void initialize() {
        loginSignUpController = new LoginSignUpController();

        accessBtn.setOnMouseClicked(mouseEvent -> {
            boolean result = userLogin();

            Stage loginStg = NavigatorSingleton.getInstance().getLoginStg();
            if(result && loginStg != null) {
                loginStg.fireEvent(new WindowEvent(loginStg, WindowEvent.WINDOW_CLOSE_REQUEST));

                UserRole role = SessionSingleton.getInstance().getUser().getRole();
                if(role == UserRole.CUSTOMER) {
                    gotoPageTop(CUSTOMER_LOGGED_TOP_BAR);
                    gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
                } else if (role == UserRole.COMPANY) {
                    // TODO
                    Logger.getAnonymousLogger().log(Level.INFO, "User logged is a company, goto...");
                } else {
                    Logger.getAnonymousLogger().log(Level.INFO, "User logged is boh...");
                }
            }
        });
    }

    public boolean userLogin() {
        boolean result = false;

        try {
            LoginCredentialsBean credentials = new LoginCredentialsBean(emailTextField.getText(), pwdTextField.getText());

            result = loginSignUpController.login(credentials);

            if(!result) setLoginError();
        } catch (InvalidFormatException e) {
            setLoginError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Invalid data inserted %s", e));

            System.out.println(e.getMessage());
        } catch (DAOException | SQLException e) {
            setLoginError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on login %s", e));
        }

        return result;
    }

    private void setLoginError() {
        emailTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        pwdTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
    }
}
