package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.model.Product;

public class ProductBean {
    private final Product product;

    public ProductBean(Product product) {
        this.product = product;
    }

    public String getProductName() {
        return product.getName();
    }

    public String getProductDescription() {
        return product.getDescription();
    }

    public String getProductCategory() {
        return product.getCategory();
    }

    public String getProductRegion() {
        return product.getRegion().getRegionString();
    }

    public String getProductPrice() {
        return String.format("%.2f€", product.getPrice());
    }

    public String getProductImageLink() {
        return product.getImageLink();
    }
}
