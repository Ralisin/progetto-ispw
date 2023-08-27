package it.ralisin.littlefarmers.controller.graphic_controller_GUI;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsGraphicControllerGUI {
    protected static final String CUSTOMER_TOP_BAR = "TopBarCustomer.fxml";
    protected static final String CUSTOMER_LOGGED_TOP_BAR = "TopBarCustomerLogged.fxml";
    protected static final String CUSTOMER_REGION_LIST_CENTER = "RegionList.fxml";
    protected static final String CUSTOMER_REGION_PRODUCTS_CENTER = "RegionProductsCenter.fxml";
    protected static final String CUSTOMER_REGION_PRODUCTS_FILTER_LEFT = "RegionProductsFilter.fxml";
    protected static final String CUSTOMER_CART_CENTER = "CartCenter.fxml";
    protected static final String CUSTOMER_ORDER_CENTER = "OrderCustomerCenter.fxml";
    protected static final String COMPANY_LOGGED_TOP_BAR = "TopBarCompanyLogged.fxml";
    protected static final String COMPANY_HOME_CENTER = "CompanyHomeCenter.fxml";
    protected static final String COMPANY_ORDER_CENTER = "OrderCompanyCenter.fxml";
    protected static final String HOME_TOP = "HomeTop.fxml";
    protected static final String HOME_CENTER = "HomeCenter.fxml";
    protected static final String LOGIN_SIGN_UP = "LoginSignUp.fxml";
    protected static final String REMOVE_ELEMENT = null;

    protected void gotoPageTop(String page) {
        try {
            NavigatorSingleton.getInstance().gotoTopPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoPageTop %s", e.getMessage()));
        }
    }

    protected void gotoPageCenter(String page) {
        try {
            NavigatorSingleton.getInstance().gotoCenterPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoPageCenter %s", e.getMessage()));
        }
    }

    /** Used if needed to pass some data to next page */
    protected void gotoPageCenterWithArg(String page, Object arg) {
        try {
            NavigatorSingleton.getInstance().getMainStg().setUserData(arg);
            NavigatorSingleton.getInstance().gotoCenterPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoPageCenterWithArg %s", e.getMessage()));
        }
    }

    protected void gotoPageLeft(String page) {
        try {
            NavigatorSingleton.getInstance().gotoLeftPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoPageLeft %s", e.getMessage()));
        }
    }

    protected Stage getMainStg() {
        return NavigatorSingleton.getInstance().getMainStg();
    }

    protected void gotoLoginPage() {
        try {
            NavigatorSingleton.getInstance().gotoLoginSignUpPage(LOGIN_SIGN_UP, false);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoLoginPage %s", e.getMessage()));
        }
    }

    protected void gotoSignUpPage() {
        try {
            NavigatorSingleton.getInstance().gotoLoginSignUpPage(LOGIN_SIGN_UP, true);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error gotoLoginPage %s", e.getMessage()));
        }
    }
}
