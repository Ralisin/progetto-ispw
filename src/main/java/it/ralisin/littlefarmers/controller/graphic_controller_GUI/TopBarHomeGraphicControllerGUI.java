package it.ralisin.littlefarmers.controller.graphic_controller_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TopBarHomeGraphicControllerGUI extends AbsGraphicControllerGUI {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;

    public void initialize() {
        signUpBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        loginBtn.setOnMouseClicked(mouseEvent -> gotoLoginPage());
    }
}
