package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderCustomerGraphicControllerGUI {
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label companyEmailLabel;
    @FXML
    private Label orderStatusLabel;
    @FXML
    private Label orderDateLabel;
    @FXML
    private VBox orderVBox;

    private Order order;
    private OrderController controllerGUI;
    private List<Product> productListOrder = new ArrayList<>();

    public OrderCustomerGraphicControllerGUI(OrderBean orderBean, OrderController controllerGUI) {
        this.order = orderBean.getOrder();
        this.controllerGUI = controllerGUI;
    }

    public void initialize() {
        setProductUI();

        ProductsListBean productsListBean = controllerGUI.getOrderProducts(new OrderBean(order));
        productListOrder = productsListBean.getProductList();

        for(Product p : productListOrder) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CartProduct.fxml"));

            ProductBean pB = new ProductBean(p);
            AnchorPane root = fxmlLoader.getRoot();
            fxmlLoader.setController(new ProductOrderCenterGraphicControllerGUI(pB, root));

            Parent parent;
            try { parent = fxmlLoader.load(); } catch (IOException e) { parent = null; }

            orderVBox.getChildren().add(parent);
        }
    }

    protected void setProductUI() {
        orderIdLabel.setText(String.format("%d",order.getId()));
        companyEmailLabel.setText(order.getCompanyEmail());
        orderStatusLabel.setText(order.getStatus().getStatus());
        orderDateLabel.setText(order.getDate());
    }
}
