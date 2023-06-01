package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.AbsGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeControllerGUI extends AbsGraphicController {
    @FXML
    private Button companyBtn;
    @FXML
    private Button customerBtn;
    @FXML
    private Button loginBtn;

    public void initialize() {
        companyBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Home companyBtn clicked"); // TODO: implement goto(COMPANY_LOGIN)
        });

        customerBtn.setOnMouseClicked(mouseEvent -> gotoPage(REGION_LIST));

        loginBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Home loginBtn clicked"); // TODO: implement goto(CUSTOMER_LOGIN)
        });
    }
}
