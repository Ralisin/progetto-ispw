package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderGraphicControllerGUI {
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label companyEmailLabel;
    @FXML
    private Label orderStatusLabel;
    @FXML
    private Label orderDateLabel;
    @FXML
    private ChoiceBox<String> orderStatusChoiceBox;
    @FXML
    private Button confirmNewStatusBtn;
    @FXML
    private VBox orderVBox;

    private boolean showCheckBox = false;
    private Order order;
    private OrderStatus orderStatus;
    private OrderController controller;
    private List<Product> productListOrder = new ArrayList<>();
    private ObservableList<String> choiceList = FXCollections.observableArrayList();

    public OrderGraphicControllerGUI(OrderBean orderBean, OrderController controllerGUI) {
        this.order = orderBean.getOrder();
        this.controller = controllerGUI;
    }

    public OrderGraphicControllerGUI(OrderBean orderBean, OrderController controllerGUI, boolean showCheckBox) {
        this.order = orderBean.getOrder();
        this.orderStatus = this.order.getStatus();
        this.controller = controllerGUI;
        this.showCheckBox = showCheckBox;
    }

    public void initialize() {
        setProductUI();

        if(showCheckBox) {
            HBox hbox = (HBox) orderStatusLabel.getParent();
            hbox.getChildren().remove(orderStatusLabel);

            for(OrderStatus o : OrderStatus.values()) choiceList.add(o.getStatus());

            orderStatusChoiceBox.setItems(choiceList);
            orderStatusChoiceBox.setValue(orderStatus.getStatus());
            orderStatusChoiceBox.setVisible(true);

            orderStatusChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) order.setStatus(OrderStatus.getByString(newValue));
            });

            confirmNewStatusBtn.setOnMouseClicked(mouseEvent -> {
                OrderBean orderBean = new OrderBean(order);
                controller.updateOrderStatus(orderBean);
            });

            confirmNewStatusBtn.setVisible(true);
        }

        ProductsListBean productsListBean = controller.getOrderProducts(new OrderBean(order));
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
