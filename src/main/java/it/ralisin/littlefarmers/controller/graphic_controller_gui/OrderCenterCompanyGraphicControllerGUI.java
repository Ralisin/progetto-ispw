package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.model.Order;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class OrderCenterCompanyGraphicControllerGUI extends AbsOrderGraphicControllerGUI {
    @FXML
    private Label noOrdersLabel;
    @FXML
    private VBox ordersVBox;

    private final OrderController controller = new OrderController();

    public void initialize() {
        OrderBean orderBean = controller.getCompanyOrdersList();
        List<Order> orderList = orderBean.getOrderList();

        if(orderList != null && !orderList.isEmpty()) noOrdersLabel.setVisible(false);

        if (orderList != null)
            for (Order order : orderList) {
                Parent p = orderLoader(new OrderBean(order), controller);
                ordersVBox.getChildren().add(p);
            }
    }
}
