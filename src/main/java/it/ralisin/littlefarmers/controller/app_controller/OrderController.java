package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.OrderDAO;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.SessionManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {
    public OrderBean getOrdersList() {
        List<Order> orderList;
        User user = SessionManagement.getInstance().getUser();

        OrderBean orderBean = new OrderBean(new ArrayList<>());

        if(user == null) return orderBean;

        try {
            orderList = CustomerDAO.getOrders(user);
            orderBean.setOrderList(orderList);
        } catch (DAOException | SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting order list with user %s: %s", user, e));
        }

        return orderBean;
    }

    public ProductsListBean getOrderProducts(OrderBean orderBean) {
        Order order = orderBean.getOrder();

        ProductsListBean productsListBean;
        try {
            productsListBean = new ProductsListBean(OrderDAO.getOrderProducts(order));
        } catch (DAOException | SQLException e) {
            productsListBean = new ProductsListBean(new ArrayList<>());

            Logger.getAnonymousLogger().log(Level.INFO, "Error on get order products");
        }

        return productsListBean;
    }
}
