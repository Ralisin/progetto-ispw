package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeCenterControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Button companyBtn;
    @FXML
    private Button customerBtn;

    public void initialize() {
        companyBtn.setOnMouseClicked(mouseEvent -> gotoSignUpPage());

        customerBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageTop(CUSTOMER_TOP_BAR);
            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
        });
    }
}
