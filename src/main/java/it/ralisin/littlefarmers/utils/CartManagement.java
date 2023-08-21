package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartManagement {
    private static CartManagement instance = null;
    private final List<Product> cart;

    private CartManagement() {
        this.cart = new ArrayList<>();
    }

    public static synchronized CartManagement getInstance() {
        if(CartManagement.instance == null) instance = new CartManagement();
        return instance;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void addProduct(Product product) {
        for(Product p : cart)
            if(p.getProductId() == product.getProductId()) {
                p.setQuantity(p.getQuantity()+1);
                updateProduct(p);

                updateRemoteCart();

                return;
            }

        product.setQuantity(1);
        cart.add(product);

        updateRemoteCart();
    }

    public void addProductList(List<Product> productList) {
        for(Product p : productList) {
            boolean found = false;

            for(Product p1 : cart) {
                if (p.getProductId() == p1.getProductId()) {
                    found = true;
                    p.setQuantity(Math.max(p.getQuantity(), p1.getQuantity()));

                    break;
                }
            }

            if(!found) cart.add(p);
        }

        updateRemoteCart();
    }

    public void removeProduct(Product product) {
        cart.remove(product);

        removeFromRemoteCart(product);
    }

    public void updateProduct(Product product) {
        for(int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getProductId() == product.getProductId()) cart.set(i, product);
        }
    }

    public void deleteCart() {
        cart.clear();
    }

    private void updateRemoteCart() {
        User currUser = SessionManagement.getInstance().getUser();
        if(currUser != null && currUser.getRole() == UserRole.CUSTOMER)
            for(Product p : cart) {
                try {
                    CustomerDAO.addToCart(currUser, p, p.getQuantity());
                } catch (DAOException e) {
                    Logger.getAnonymousLogger().log(Level.INFO, String.format("Error updating cart %s", e));
                }
            }
    }

    private void removeFromRemoteCart(Product product) {
        User currUser = SessionManagement.getInstance().getUser();
        if(currUser != null && currUser.getRole() == UserRole.CUSTOMER) {
            try {
                CustomerDAO.removeFromCart(currUser, product);
            } catch (DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, String.format("Error removing product %s from cart %s", product, e));
            }
        }
    }
}
