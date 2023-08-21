package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.controller.app_controller.LoginSignUpController;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSignUpControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private TextField emailLoginTxtFld;
    @FXML
    private TextField pwdLoginTxtFld;
    @FXML
    private Button accessBtn;

    @FXML
    private TextField emailSignUpTxtFld;
    @FXML
    private TextField emailRepSignUpTxtFld;
    @FXML
    private TextField pwdSignUpTxtFld;
    @FXML
    private TextField pwdRepSignUpTxtFld;
    @FXML
    private RadioButton customerRB;
    @FXML
    private RadioButton companyRB;
    @FXML
    private Button signUpBtn;

    private LoginSignUpController loginSignUpController;

    public void initialize() {
        loginSignUpController = new LoginSignUpController();

        accessBtn.setOnMouseClicked(mouseEvent -> {
            if(userLogin()) userLoggedSignedUp();
        });

        signUpBtn.setOnMouseClicked(mouseEvent -> {
            if(userSignUp()) userLoggedSignedUp();
        });
    }

    private boolean userLogin() {
        boolean result = false;

        try {
            LoginCredentialsBean credentials = new LoginCredentialsBean(emailLoginTxtFld.getText(), pwdLoginTxtFld.getText());

            result = loginSignUpController.login(credentials);

            if(!result) setLoginError();
        } catch (InvalidFormatException e) {
            setLoginError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Invalid data inserted %s", e));
        } catch (DAOException | SQLException e) {
            setLoginError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on login %s", e));
        }

        return result;
    }

    private boolean userSignUp() {
        boolean result = false;

        try {
            UserRole role;

            if(customerRB.selectedProperty().get()) role = UserRole.CUSTOMER;
            else if(companyRB.selectedProperty().get()) role = UserRole.COMPANY;
            else role = UserRole.NONE;

            LoginCredentialsBean credentials =
                    new LoginCredentialsBean(
                            emailSignUpTxtFld.getText(),
                            emailRepSignUpTxtFld.getText(),
                            pwdSignUpTxtFld.getText(),
                            pwdRepSignUpTxtFld.getText(),
                            role
                    );

            result = loginSignUpController.signUp(credentials);

            if(!result) setSignUpError();
        } catch (InvalidFormatException e) {
            setSignUpError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Invalid data inserted %s", e));
        } catch (DAOException e) {
            setSignUpError();

            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on login %s", e));
        }

        return result;
    }

    private void setLoginError() {
        emailLoginTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        pwdLoginTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
    }

    private void setSignUpError() {
        emailSignUpTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        emailRepSignUpTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        pwdSignUpTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        pwdRepSignUpTxtFld.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
    }

    private void userLoggedSignedUp() {
        Stage loginStg = NavigatorSingleton.getInstance().getLoginStg();
        if(loginStg != null) loginStg.fireEvent(new WindowEvent(loginStg, WindowEvent.WINDOW_CLOSE_REQUEST));

        UserRole role = SessionManagement.getInstance().getUser().getRole();
        if(role == UserRole.CUSTOMER) {
            gotoPageTop(CUSTOMER_LOGGED_TOP_BAR);

            String currCenterPage = NavigatorSingleton.getInstance().getCurrCenterPage();

            if(currCenterPage == null || currCenterPage.equals(HOME_CENTER)) gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
        } else if (role == UserRole.COMPANY) {
            /* TODO COMPANY home page
            // gotoPageTop(COMPANY_LOGGED_TOP_BAR);
            // gotoPageCenter(COMPANY_HOME); */
            Logger.getAnonymousLogger().log(Level.INFO, "User logged is a company, goto...");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid user, contact LittleFarmer assistance");
        }
    }
}
