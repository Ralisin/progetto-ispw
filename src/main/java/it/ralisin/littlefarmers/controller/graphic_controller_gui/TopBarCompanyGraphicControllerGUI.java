package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.controller.app_controller.SessionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TopBarCompanyGraphicControllerGUI extends AbsGraphicControllerGUI {
    @FXML
    private Button productsBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button logOutBtn;

    SessionController controller;

    public void initialize() {
        controller = new SessionController();

        ordersBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(COMPANY_ORDER_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        productsBtn.setOnMouseClicked(mouseEvent -> Logger.getAnonymousLogger().log(Level.INFO, "TopBarCompanyGraphicControllerGUI productsBtn clicked"));

        logOutBtn.setOnMouseClicked(mouseEvent -> {
            controller.logOut();

            gotoPageTop(HOME_TOP);
            gotoPageCenter(HOME_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });
    }
}
