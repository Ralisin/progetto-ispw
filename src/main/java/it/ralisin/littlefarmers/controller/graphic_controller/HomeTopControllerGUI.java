package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeTopControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;

    public void initialize() {
        signUpBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Home signUpBtn clicked"); // TODO: implement goto(COMPANY_LOGIN)
        });

        loginBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "Home loginBtn clicked"); // TODO: implement goto(COMPANY_LOGIN)
        });
    }
}
