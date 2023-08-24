package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartController {
    private static CartController instance = null;
    private final List<Product> cart;

    private CartController() {
        this.cart = new ArrayList<>();
    }

    public static synchronized CartController getInstance() {
        if(CartController.instance == null) instance = new CartController();
        return instance;
    }

    public CartBean getCart() {
        CartBean cartBean = new CartBean();
        cartBean.setProductList(cart);

        return cartBean;
    }

    public CartBean getTotal() {
        CartBean cartBean = new CartBean();

        float totalPrice = 0F;
        for(Product p : cart) totalPrice += p.getPrice() * p.getQuantity();

        cartBean.setCartPrice(totalPrice);

        return cartBean;
    }

    public void addProduct(CartBean cartBean) {
        Product product = cartBean.getProduct();

        for(Product p : cart)
            if(p.getProductId() == product.getProductId()) {
                p.setQuantity(p.getQuantity()+1);

                cartBean.setProduct(p);
                updateProduct(cartBean);

                updateRemoteCart();

                return;
            }

        product.setQuantity(1);
        cart.add(product);

        updateRemoteCart();
    }

    public void addProductList(CartBean cartBean) {
        List<Product> productList = cartBean.getProductList();

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

    public void removeProduct(CartBean cartBean) {
        Product product = cartBean.getProduct();

        cart.remove(product);

        removeFromRemoteCart(product);
    }

    public void updateProduct(CartBean cartBean) {
        Product product = cartBean.getProduct();

        for(int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getProductId() == product.getProductId()) cart.set(i, product);
        }
    }

    public void deleteCart() {
        cart.clear();
    }

    public void buyCart() {
        makeOrder();

        deleteCart();
    }

    private void updateRemoteCart() {
        User currUser = SessionManager.getInstance().getUser();
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
        User currUser = SessionManager.getInstance().getUser();
        if(currUser != null && currUser.getRole() == UserRole.CUSTOMER) {
            try {
                CustomerDAO.removeFromCart(currUser, product);
            } catch (DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, String.format("Error removing product %s from cart %s", product, e));
            }
        }
    }

    private void makeOrder() {
        User currUser = SessionManager.getInstance().getUser();
        if(currUser != null && currUser.getRole() == UserRole.CUSTOMER && !cart.isEmpty()) {
            try {
                CustomerDAO.makeOrder(currUser);
            } catch (DAOException | SQLException e) {
                Logger.getAnonymousLogger().log(Level.INFO, String.format("Error making order for user %s: %s", currUser, e));
            }
        }
    }
}
