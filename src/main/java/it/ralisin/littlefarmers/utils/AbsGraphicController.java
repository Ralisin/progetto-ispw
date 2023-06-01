package it.ralisin.littlefarmers.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbsGraphicController {
    protected static final String HOME = "Home.fxml";
    protected static final String REGION_LIST = "RegionList.fxml";

    protected void goToPage(String page) {
        try{
            NavigatorSingleton.getInstance().gotoPage(page);
        }
        catch (IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
