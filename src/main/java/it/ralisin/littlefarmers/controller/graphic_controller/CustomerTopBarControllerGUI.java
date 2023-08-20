package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerTopBarControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Button cartBtn;

    public void initialize() {
        signUpBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        loginBtn.setOnMouseClicked(mouseEvent -> {
            gotoLoginPage();

            Logger.getAnonymousLogger().log(Level.INFO, "CustomerTopBarControllerGUI loginBtn clicked"); // TODO: implement open another scene

        });

        cartBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "CustomerTopBarControllerGUI cartBtn clicked"); // TODO: implement open another scene
        });
    }
}
