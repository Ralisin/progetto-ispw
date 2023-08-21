package it.ralisin.littlefarmers.controller.graphic_controller;

import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.utils.CartManagement;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductControllerGUI {
    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productDescription;
    @FXML
    private Label productPrice;
    @FXML
    private Label productRegion;
    @FXML
    private Label productCategory;
    @FXML
    private Button addToCartBtn;
    private final ProductBean productBean;

    public ProductControllerGUI(ProductBean product) {
        this.productBean = product;
    }

    public void initialize() {
        productName.setText(productBean.getProductName());
        productDescription.setText(productBean.getProductDescription());
        productPrice.setText(productBean.getProductPrice());
        productCategory.setText(productBean.getProductCategory());
        productRegion.setText(productBean.getProductRegion());

        String imageLink = productBean.getProductImageLink();
        if(imageLink != null) {
            // Create a Task to load the image from a URL
            Task<Image> loadImageTask = new Task<>() {
                @Override
                protected Image call() {
                    try (InputStream stream = getInputStreamFromUrl(imageLink)) {
                        return new Image(stream);
                    } catch (IOException e) {
                        Logger.getAnonymousLogger().log(Level.INFO, String.format("loadImageTast error %s", e));
                        return null;
                    }
                }

                private InputStream getInputStreamFromUrl(String urlString) throws IOException {
                    URI uri = URI.create(urlString);
                    HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                    return connection.getInputStream();
                }
            };

            // Update the UI when the image is loaded
            loadImageTask.setOnSucceeded(event -> {
                Image loadedImage = loadImageTask.getValue();
                if (loadedImage != null) {
                    productImage.setImage(loadedImage);
                }
            });

            new Thread(loadImageTask).start();
        }

        addToCartBtn.setOnMouseClicked(mouseEvent -> {
            CartManagement.getInstance().addProduct(productBean.getProduct());
        });
    }
}
