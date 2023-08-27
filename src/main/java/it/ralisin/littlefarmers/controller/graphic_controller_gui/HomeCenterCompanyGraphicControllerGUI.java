package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.model.Order;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class HomeCenterCompanyGraphicControllerGUI extends AbsOrderGraphicControllerGUI {
    @FXML
    private Label noNewOrdersLabel;
    @FXML
    private VBox ordersVBox;

    private final OrderController controller = new OrderController();

    public void initialize() {
        OrderStatus orderStatus = OrderStatus.WAITING;
        OrderBean orderBean = controller.getCompanyOrdersListByStatus(orderStatus);
        List<Order> orderList = orderBean.getOrderList();

        if(!orderList.isEmpty()) noNewOrdersLabel.setVisible(false);

        for(Order order : orderList) {
            Parent p = orderLoader(new OrderBean(order), controller);
            ordersVBox.getChildren().add(p);
        }
    }
}
