package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.utils.CartManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProductCartCenterControllerGUI extends AbsProductControllerGUI {
    @FXML
    private Label productQuantity;
    @FXML
    private Button removeQuantityBtn;
    @FXML
    private Button addQuantityBtn;
    @FXML
    private Button deleteBtn;
    private final CartCenterGraphicControllerGUI controller;

    public ProductCartCenterControllerGUI(ProductBean product, CartCenterGraphicControllerGUI controller) {
        super(product);
        this.controller = controller;
    }

    public void initialize() {
        setProductUI();

        productQuantity.setText(String.format("%d", productBean.getProductQuantity()));

        removeQuantityBtn.setOnMouseClicked(mouseEvent -> {
            if(productBean.getProductQuantity() > 1) {
                productBean.setProductQuantity(productBean.getProductQuantity() - 1);

                productQuantity.setText(String.format("%d", productBean.getProductQuantity()));

                CartManagement.getInstance().updateProduct(productBean.getProduct());
            } else if(productBean.getProductQuantity() == 1) {
                CartManagement.getInstance().removeProduct(productBean.getProduct());

                controller.removeProductById(productBean.getProductId());
            }

            controller.setPriceLabelUI();
        });

        addQuantityBtn.setOnMouseClicked(mouseEvent -> {
            productBean.setProductQuantity(productBean.getProductQuantity() + 1);

            productQuantity.setText(String.format("%d", productBean.getProductQuantity()));

            CartManagement.getInstance().updateProduct(productBean.getProduct());

            controller.setPriceLabelUI();
        });

        deleteBtn.setOnMouseClicked(mouseEvent -> {
            CartManagement.getInstance().removeProduct(productBean.getProduct());

            controller.removeProductById(productBean.getProductId());

            controller.setPriceLabelUI();
        });
    }
}
