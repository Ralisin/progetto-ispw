package it.ralisin.littlefarmers.controller.GraphicController;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

public abstract class AbGraphicController {
    protected static final String HOME_PAGE = "RegionListScreen.fxml";
    protected static final String REGION_PAGE = "RegionProductsScreen.fxml";

    public void gotoPage(String page) {
        try{
            NavigatorSingleton.getInstance().gotoPage(page);
        }
        catch (IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
