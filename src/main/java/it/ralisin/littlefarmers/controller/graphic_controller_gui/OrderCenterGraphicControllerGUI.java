package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.model.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class OrderCenterGraphicControllerGUI extends AbsGraphicControllerGUI {
    @FXML
    private Button backBtn;
    @FXML
    private VBox ordersCenterVBox;
    private final OrderController controller = new OrderController();

    public void initialize() {
        OrderBean orderBean = controller.getCustomerOrdersList();
        List<Order> orderList = orderBean.getOrderList();

        for(Order order : orderList) {
            Parent p = orderLoader(new OrderBean(order), controller);
            ordersCenterVBox.getChildren().add(p);
        }

        backBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });
    }

    private Parent orderLoader(OrderBean oB, OrderController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("OrderCustomer.fxml"));
        fxmlLoader.setController(new OrderGraphicControllerGUI(oB, controller));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }
}
