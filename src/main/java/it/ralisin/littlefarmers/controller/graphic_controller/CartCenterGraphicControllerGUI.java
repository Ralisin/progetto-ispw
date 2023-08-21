package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.patterns.Observer;
import it.ralisin.littlefarmers.utils.CartManagement;
import it.ralisin.littlefarmers.utils.SessionManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartCenterGraphicControllerGUI extends AbsCustomerGraphicController implements Observer {
    @FXML
    private VBox cartCenterVBox;
    @FXML
    private Button backBtn;
    @FXML
    private Button buyCartBtn;
    @FXML
    private Label priceLabel;
    private final Set<Integer> ids = new HashSet<>();

    public void initialize() {
        SessionManagement.getInstance().registerObserver(this);

        if (SessionManagement.getInstance().getUser() == null) buyCartBtn.setDisable(true);

        List<Product> productList = CartManagement.getInstance().getCart();
        for(Product product : productList) {
            Parent p = productCartLoader(new ProductBean(product), this);

            ids.add(product.getProductId());
            p.setId(String.format("%d", product.getProductId()));

            cartCenterVBox.getChildren().add(p);
        }

        setPriceLabelUI();

        backBtn.setOnMouseClicked(mouseEvent -> {
            SessionManagement.getInstance().removeObserver(this);

            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        buyCartBtn.setOnMouseClicked(mouseEvent -> {
            CartManagement.getInstance().buyCart();

            for(int id : ids)
                cartCenterVBox.getChildren().remove(cartCenterVBox.lookup(String.format("#%d", id)));

            ids.clear();

            setPriceLabelUI();
        });
    }

    public void setPriceLabelUI() {
        priceLabel.setText(String.format("%.2f€", CartManagement.getInstance().getTotal()));
    }

    public void removeProductById(int id) {
        cartCenterVBox.getChildren().remove(cartCenterVBox.lookup(String.format("#%d", id)));

        ids.remove(id);
    }

    public Parent productCartLoader(ProductBean pB, CartCenterGraphicControllerGUI controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProductCustomerCart.fxml"));
        fxmlLoader.setController(new ProductCartCenterControllerGUI(pB, controller));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void update() {
        if(SessionManagement.getInstance().getUser() != null) buyCartBtn.setDisable(false);
    }
}