package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AbsOrderGraphicControllerGUI extends AbsGraphicControllerGUI {
    Parent orderLoader(OrderBean oB, OrderController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("OrderCustomer.fxml"));
        fxmlLoader.setController(new OrderGraphicControllerGUI(oB, controller, true));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }
}
