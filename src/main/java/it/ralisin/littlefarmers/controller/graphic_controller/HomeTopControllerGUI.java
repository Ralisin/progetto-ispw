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
        signUpBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        loginBtn.setOnMouseClicked(mouseEvent -> gotoLoginPage());
    }
}
