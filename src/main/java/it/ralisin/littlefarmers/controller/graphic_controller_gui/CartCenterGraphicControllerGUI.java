package it.ralisin.littlefarmers.controller.graphic_controller_gui;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.patterns.Observer;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import it.ralisin.littlefarmers.utils.SessionManager;
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

public class CartCenterGraphicControllerGUI extends AbsGraphicControllerGUI implements Observer {
    @FXML
    private VBox cartCenterVBox;
    @FXML
    private Button backBtn;
    @FXML
    private Button buyCartBtn;
    @FXML
    private Label priceLabel;
    private final Set<Integer> idsProductsUI = new HashSet<>();

    public void initialize() {
        SessionManager.getInstance().registerObserver(this);

        if (SessionManager.getInstance().getUser() == null) buyCartBtn.setDisable(true);

        CartBean cartBean = CartController.getInstance().getCart();
        List<Product> productList = cartBean.getProductList();

        for(Product product : productList) {
            Parent p = productCartLoader(new ProductBean(product), this);

            idsProductsUI.add(product.getProductId());
            if(p != null) p.setId(String.format("%d", product.getProductId()));
            cartCenterVBox.getChildren().add(p);
        }

        setPriceLabelUI();

        backBtn.setOnMouseClicked(mouseEvent -> {
            SessionManager.getInstance().removeObserver(this);

            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
            gotoPageLeft(REMOVE_ELEMENT);
        });

        buyCartBtn.setOnMouseClicked(mouseEvent -> {
            CartController.getInstance().buyCart();

            for(int id : idsProductsUI)
                cartCenterVBox.getChildren().remove(cartCenterVBox.lookup(String.format("#%d", id)));
            idsProductsUI.clear();

            setPriceLabelUI();
        });
    }

    public void setPriceLabelUI() {
        CartBean cartBean = CartController.getInstance().getTotal();

        priceLabel.setText(String.format("%.2f€", cartBean.getCartPrice()));
    }

    public void removeProductById(int id) {
        cartCenterVBox.getChildren().remove(cartCenterVBox.lookup(String.format("#%d", id)));
        idsProductsUI.remove(id);
    }

    private Parent productCartLoader(ProductBean pB, CartCenterGraphicControllerGUI controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CartProduct.fxml"));
        fxmlLoader.setController(new ProductCartCenterGraphicControllerGUI(pB, controller));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void update() {
        if(SessionManager.getInstance().getUser() != null) buyCartBtn.setDisable(false);
    }
}