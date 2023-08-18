package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.model.Product;

import java.util.List;

public class ProductsListBean {
    List<Product> productList;

    public ProductsListBean(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
