package it.ralisin.littlefarmers.controller.graphic_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeControllerGUI extends AbsControllerGUI {
    @FXML
    private Button companyBtn;
    @FXML
    private Button customerBtn;
    @FXML
    private Button loginBtn;

    public void initialize() {
        companyBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Goto company login screen"); // TODO: implement goto(COMPANY_LOGIN)
        });

        customerBtn.setOnMouseClicked(mouseEvent -> {
            gotoPage(REGION_LIST);
        });

        loginBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Goto login screen"); // TODO: implement goto(CUSTOMER_LOGIN)
        });
    }
}
