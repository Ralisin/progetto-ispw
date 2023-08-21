package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.patterns.Observer;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CartGraphicControllerGUI extends AbsCustomerGraphicController implements Observer {
    @FXML
    private Button backBtn;
    @FXML
    private Button buyCartBtn;

    public void initialize() {
        SessionManagement.getInstance().registerObserver(this);

        if (SessionManagement.getInstance().getUser() == null) buyCartBtn.setDisable(true);

        backBtn.setOnMouseClicked(mouseEvent -> {
            SessionManagement.getInstance().removeObserver(this);

            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });
    }

    @Override
    public void update() {
        if(SessionManagement.getInstance().getUser() != null) buyCartBtn.setDisable(false);
    }
}