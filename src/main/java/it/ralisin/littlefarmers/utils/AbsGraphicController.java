package it.ralisin.littlefarmers.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsGraphicController {
    protected static final String HOME = "Home.fxml";
    protected static final String REGION_LIST = "RegionList.fxml";

    protected void gotoPage(String page) {
        try{
            NavigatorSingleton.getInstance().gotoPage(page);
        }
        catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
