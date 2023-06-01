package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

public abstract class AbsControllerGUI {
    protected static final String REGION_LIST = "RegionList.fxml";
    protected static final String REGION_PRODUCTS = "RegionProducts.fxml";

    public void gotoPage(String page) {
        try{
            NavigatorSingleton.getInstance().gotoPage(page);
        }
        catch (IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
