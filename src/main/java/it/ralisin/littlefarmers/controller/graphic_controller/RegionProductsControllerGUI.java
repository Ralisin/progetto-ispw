package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.utils.AbsGraphicController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionProductsControllerGUI extends AbsGraphicController {
    private Regions region;

    public void initialize() {
        RegionBean regionBean = (RegionBean) getStg().getUserData();
        region = regionBean.getRegions();

        Logger.getAnonymousLogger().log(Level.INFO, region.getRegionString());
    }
}
