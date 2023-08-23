package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartBean {
    private List<Product> productList = new ArrayList<>();
    private Product product = null;
    private float cartPrice = 0F;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(float cartPrice) {
        this.cartPrice = cartPrice;
    }
}
