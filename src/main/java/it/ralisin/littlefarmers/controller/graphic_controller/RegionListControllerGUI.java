package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.utils.AbsGraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionListControllerGUI extends AbsGraphicController {
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button cartBtn;

    public void initialize() {
        loginBtn.setOnMouseClicked(mouseEvent -> Logger.getAnonymousLogger().log(Level.INFO, "RegionList loginBtn clicked"));

        signUpBtn.setOnMouseClicked(mouseEvent -> Logger.getAnonymousLogger().log(Level.INFO, "RegionList signUpBtn clicked"));

        cartBtn.setOnMouseClicked(mouseEvent -> Logger.getAnonymousLogger().log(Level.INFO, "RegionList cartBtn clicked"));
    }

    public void onClickRegionButton(ActionEvent event) {
        String str = event.getSource().toString();
        String regionName = str.substring(str.indexOf("'")+1, str.lastIndexOf("'"));

        gotoPage(REGION_PRODUCT, new RegionBean(regionName));
    }
}
