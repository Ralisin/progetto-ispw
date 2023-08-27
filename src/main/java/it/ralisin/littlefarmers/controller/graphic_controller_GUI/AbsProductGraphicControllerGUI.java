package it.ralisin.littlefarmers.controller.graphic_controller_GUI;

import it.ralisin.littlefarmers.beans.ProductBean;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsProductGraphicControllerGUI {
    @FXML
    protected ImageView productImage;
    @FXML
    protected Label productName;
    @FXML
    protected Label productDescription;
    @FXML
    protected Label productRegion;
    @FXML
    protected Label productCategory;
    @FXML
    protected Label productPrice;
    protected final ProductBean productBean;

    protected AbsProductGraphicControllerGUI(ProductBean product) {
        this.productBean = product;
    }

    protected void setProductUI() {
        productName.setText(productBean.getProductName());
        productDescription.setText(productBean.getProductDescription());
        productPrice.setText(productBean.getProductPrice());
        productCategory.setText(productBean.getProductCategory());
        productRegion.setText(productBean.getProductRegion());

        String imageLink = productBean.getProductImageLink();
        if(imageLink != null) loadImage(imageLink);
    }

    protected void loadImage(String imageLink) {
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
            if (loadedImage != null) productImage.setImage(loadedImage);
        });

        new Thread(loadImageTask).start();
    }
}
