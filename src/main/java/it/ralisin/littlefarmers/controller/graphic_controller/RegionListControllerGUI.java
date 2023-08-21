package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.enums.Regions;
import javafx.event.ActionEvent;

public class RegionListControllerGUI extends AbsCustomerGraphicController {
    public void onClickRegionButton(ActionEvent event) {
        String str = event.getSource().toString();
        String regionName = str.substring(str.indexOf("'")+1, str.lastIndexOf("'"));

        Regions region = Regions.getByRegionString(regionName);

        RegionBean regionBean = new RegionBean(region);
        gotoPageCenterWithArg(CUSTOMER_REGION_PRODUCTS_CENTER, regionBean);
        gotoPageLeft(CUSTOMER_REGION_PRODUCTS_FILTER_LEFT);
    }
}
