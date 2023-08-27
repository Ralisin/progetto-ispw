package it.ralisin.littlefarmers.controller.graphic_controller_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeCenterGraphicControllerGUI extends AbsGraphicControllerGUI {
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
