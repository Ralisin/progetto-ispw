package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProductRegionProductsControllerGUI extends AbsProductControllerGUI {
    @FXML
    private Button addToCartBtn;

    public ProductRegionProductsControllerGUI(ProductBean product) {
        super(product);
    }

    public void initialize() {
        setProductUI();

        addToCartBtn.setOnMouseClicked(mouseEvent -> {
            CartBean cartBean = new CartBean();
            cartBean.setProduct(productBean.getProduct());

            CartController.getInstance().addProduct(cartBean);
        });
    }
}
