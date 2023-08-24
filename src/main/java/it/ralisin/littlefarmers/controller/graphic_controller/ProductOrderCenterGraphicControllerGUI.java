package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.ProductBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProductOrderCenterGraphicControllerGUI extends AbsProductGraphicControllerGUI {
    @FXML
    private Label productQuantity;
    @FXML
    private Button removeQuantityBtn;
    @FXML
    private Button addQuantityBtn;
    @FXML
    private Button deleteBtn;
    private AnchorPane root;
    protected ProductOrderCenterGraphicControllerGUI(ProductBean product, AnchorPane root) {
        super(product);
        this.root = root;
    }

    public void initialize() {
        setProductUI();

        productPrice.setText("Quantity");
        productQuantity.setText(String.format("%d", productBean.getProductQuantity()));

        removeQuantityBtn.setVisible(false);
        addQuantityBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }
}
