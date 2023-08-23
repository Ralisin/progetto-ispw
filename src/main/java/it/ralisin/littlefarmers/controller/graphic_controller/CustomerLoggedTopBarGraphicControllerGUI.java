package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.controller.app_controller.CartController;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
            CartController.getInstance().deleteCart();

            gotoPageTop(HOME_TOP);
            gotoPageCenter(HOME_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        ordersBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(CUSTOMER_ORDER_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        cartBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(CUSTOMER_CART_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });
    }
}
