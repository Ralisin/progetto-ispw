package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderCustomerControllerGUI {
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label companyEmailLabel;
    @FXML
    private Label orderStatusLabel;
    @FXML
    private Label orderDateLabel;

    private Order order = null;
    public OrderCustomerControllerGUI(OrderBean orderBean) {
        order = orderBean.getOrder();
    }

    public void initialize() {
        setProductUI();
    }

    protected void setProductUI() {
        orderIdLabel.setText(String.format("%d",order.getId()));
        companyEmailLabel.setText(order.getCompanyEmail());
        orderStatusLabel.setText(order.getStatus().getStatus());
        orderDateLabel.setText(order.getDate());
    }
}
