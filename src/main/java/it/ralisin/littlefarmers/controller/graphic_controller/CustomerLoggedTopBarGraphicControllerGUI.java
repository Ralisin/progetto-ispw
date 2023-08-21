package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.CartManagement;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerLoggedTopBarGraphicControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Button logOutBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button cartBtn;

    public void initialize() {
        logOutBtn.setOnMouseClicked(mouseEvent -> {
            SessionManagement.getInstance().clear();
            CartManagement.getInstance().deleteCart();

            gotoPageTop(HOME_TOP);
            gotoPageCenter(HOME_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        ordersBtn.setOnMouseClicked(mouseEvent -> {
            Logger.getAnonymousLogger().log(Level.INFO, "CustomerLoggedTopBarGraphicControllerGUI ordersBtn clicked");
        });

        cartBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(CUSTOMER_CART_CENTER);
//            gotoPageLeft(REMOVE_ELEMENT);
        });
    }
}
