package it.ralisin.littlefarmers.utils;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsCustomerGraphicController {
    protected static final String CUSTOMER_TOP_BAR = "CustomerTopBar.fxml";
    protected static final String CUSTOMER_REGION_LIST_CENTER = "RegionList.fxml";
    protected static final String CUSTOMER_REGION_PRODUCTS_CENTER = "RegionProducts.fxml";
    protected static final String CUSTOMER_REGION_PRODUCTS_FILTER_LEFT = "RegionProductsFilter.fxml";

    protected void gotoPageTop(String page) {
        try {
            NavigatorSingleton.getInstance().gotoTopPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error gotoPageTop " + e.getMessage());
        }
    }

    protected void gotoPageCenter(String page) {
        try {
            NavigatorSingleton.getInstance().gotoCenterPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error gotoPageCenter " + e.getMessage());
        }
    }

    /** Used if needed to pass some data to next page */
    protected void gotoPageCenter(String page, Object o) {
        try {
            NavigatorSingleton.getInstance().getMainStg().setUserData(o);
            NavigatorSingleton.getInstance().gotoCenterPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error gotoPageCenter (with object) " + e.getMessage());
        }
    }

    protected void gotoPageLeft(String page) {
        try {
            NavigatorSingleton.getInstance().gotoLeftPageMain(page);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Error gotoPageLeft " + e.getMessage());
        }
    }
}
