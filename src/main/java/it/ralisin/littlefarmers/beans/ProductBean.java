package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.model.Product;

import java.util.List;

public class ProductBean {
    List<Product> productList;

    public ProductBean(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
