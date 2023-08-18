package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.controller.app_controller.RegionProductsController;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class RegionProductsCenterControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Label regionLabel;
    @FXML
    private Button backBtn;
    @FXML
    private VBox vBoxProducts;

    public void initialize() {
        RegionBean regionBean = (RegionBean) getMainStg().getUserData();
        Regions region = regionBean.getRegion();

        backBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageLeft(REMOVE_ELEMENT);
            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
        });

        regionLabel.setText(region.getRegionString());

        RegionProductsController controller = new RegionProductsController();
        ProductsListBean productsListBean = controller.loadRegionProducts(region);

        List<Product> productList = productsListBean.getProductList();
        for(Product p: productList) {
            Parent product = productCustomerLoader(new ProductBean(p));

            vBoxProducts.getChildren().add(product);
        }
    }

    private static Parent productCustomerLoader(ProductBean pB) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProductCustomer.fxml"));
        fxmlLoader.setController(new ProductControllerGUI(pB));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }


}
