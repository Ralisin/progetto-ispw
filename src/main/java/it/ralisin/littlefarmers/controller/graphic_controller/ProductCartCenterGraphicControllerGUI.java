package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProductCartCenterGraphicControllerGUI extends AbsProductGraphicControllerGUI {
    @FXML
    private Label productQuantity;
    @FXML
    private Button removeQuantityBtn;
    @FXML
    private Button addQuantityBtn;
    @FXML
    private Button deleteBtn;
    private final CartCenterGraphicControllerGUI controller;

    public ProductCartCenterGraphicControllerGUI(ProductBean product, CartCenterGraphicControllerGUI controller) {
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

                CartBean cartBean = new CartBean();
                cartBean.setProduct(productBean.getProduct());

                CartController.getInstance().updateProduct(cartBean);
            } else if(productBean.getProductQuantity() == 1) {
                CartBean cartBean = new CartBean();
                cartBean.setProduct(productBean.getProduct());

                CartController.getInstance().removeProduct(cartBean);

                controller.removeProductById(productBean.getProductId());
            }

            controller.setPriceLabelUI();
        });

        addQuantityBtn.setOnMouseClicked(mouseEvent -> {
            productBean.setProductQuantity(productBean.getProductQuantity() + 1);

            productQuantity.setText(String.format("%d", productBean.getProductQuantity()));

            CartBean cartBean = new CartBean();
            cartBean.setProduct(productBean.getProduct());
            CartController.getInstance().updateProduct(cartBean);

            controller.setPriceLabelUI();
        });

        deleteBtn.setOnMouseClicked(mouseEvent -> {
            CartBean cartBean = new CartBean();
            cartBean.setProduct(productBean.getProduct());

            CartController.getInstance().removeProduct(cartBean);

            controller.removeProductById(productBean.getProductId());

            controller.setPriceLabelUI();
        });
    }
}
