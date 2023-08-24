package it.ralisin.littlefarmers.controller.graphic_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TopBarHomeGraphicControllerGUI extends AbsGraphicController {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;

    public void initialize() {
        signUpBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        loginBtn.setOnMouseClicked(mouseEvent -> gotoLoginPage());
    }
}
