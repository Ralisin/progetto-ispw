package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.beans.RegionBean;
import it.ralisin.littlefarmers.controller.app_controller.RegionProductsController;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.utils.AbsCustomerGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class RegionProductsCenterControllerGUI extends AbsCustomerGraphicController {
    @FXML
    private Label regionLabel;
    @FXML
    private Button backBtn;
    @FXML
    private VBox vBoxProducts;

    private Regions region;

    public void initialize() {
        RegionBean regionBean = (RegionBean) getMainStg().getUserData();
        region = regionBean.getRegion();

        backBtn.setOnMouseClicked(mouseEvent -> {
            gotoPageLeft(REMOVE_ELEMENT);
            gotoPageCenter(CUSTOMER_REGION_LIST_CENTER);
        });

        regionLabel.setText(region.getRegionString());

        RegionProductsController controller = new RegionProductsController();
        ProductBean productBean = controller.loadRegionProducts(region);

        List<Product> productList = productBean.getProductList();
        for(Product p: productList) {
            Label productName = new Label(p.getName());
            vBoxProducts.getChildren().add(productName);
        }
    }
}
