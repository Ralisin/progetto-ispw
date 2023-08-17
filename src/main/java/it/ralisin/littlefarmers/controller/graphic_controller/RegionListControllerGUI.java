package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.event.ActionEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionListControllerGUI extends AbsCustomerGraphicController {
    public void onClickRegionButton(ActionEvent event) {
        String str = event.getSource().toString();
        String regionName = str.substring(str.indexOf("'")+1, str.lastIndexOf("'"));

        Regions region = Regions.getByRegionString(regionName);

        RegionBean regionBean = new RegionBean(region);
//        gotoPageCenter(CUSTOMER_REGION_PRODUCTS_CENTER, regionBean);
//        gotoPageLeft(CUSTOMER_REGION_PRODUCTS_FILTER_LEFT);

        gotoPageLeft(CUSTOMER_REGION_PRODUCTS_CENTER);


        Logger.getAnonymousLogger().log(Level.INFO, "RegionListControllerGUI " + region.getRegionString());
    }
}
