package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TopBarCustomerGraphicControllerGUI extends AbsGraphicControllerGUI {
    @FXML
    private Button signUpBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Button cartBtn;

    public void initialize() {
        signUpBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        loginBtn.setOnMouseClicked(mouseEvent -> gotoLoginPage());

        cartBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(CUSTOMER_CART_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });
    }
}
