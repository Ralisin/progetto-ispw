package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomerTopBarControllerGUI extends AbsCustomerGraphicController {
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
