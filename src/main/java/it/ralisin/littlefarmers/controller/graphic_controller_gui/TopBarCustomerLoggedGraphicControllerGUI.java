package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.controller.app_controller.SessionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TopBarCustomerLoggedGraphicControllerGUI extends AbsGraphicControllerGUI {
    @FXML
    private Button logOutBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button cartBtn;

    private SessionController controller;

    public void initialize() {
        controller = new SessionController();

        logOutBtn.setOnMouseClicked(mouseEvent -> {
            controller.logOut();

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
