package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.dao.queries.CompanyDAO;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.OrderDAO;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {
    public OrderBean getCustomerOrdersList() {
        List<Order> orderList;
        User user = SessionManager.getInstance().getUser();

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

    public OrderBean getCompanyOrdersList() {
        List<Order> orderList;
        User user = SessionManager.getInstance().getUser();

        OrderBean orderBean = new OrderBean(new ArrayList<>());

        if(user == null) return orderBean;

        try {
            orderList = CompanyDAO.getOrders(user);
            orderBean.setOrderList(orderList);
        } catch (DAOException | SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting order list with user %s: %s", user, e));
        }

        return orderBean;
    }

    public OrderBean getCompanyOrdersListByStatus(OrderStatus orderStatus) {
        List<Order> orderList;
        User user = SessionManager.getInstance().getUser();

        OrderBean newOrderBean = new OrderBean(new ArrayList<>());

        if(user == null || orderStatus == null) return newOrderBean;

        try {
            orderList = CompanyDAO.getOrdersByStatus(user, orderStatus);
            newOrderBean.setOrderList(orderList);
        } catch (DAOException | SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting order list with user %s: %s", user, e));
        }

        return newOrderBean;
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

    public void updateOrderStatus(OrderBean orderBean) {
        User company = SessionManager.getInstance().getUser();
        Order order = orderBean.getOrder();
        OrderStatus orderStatus = order.getStatus();

        if(company.getRole() == UserRole.COMPANY) {
            try {
                CompanyDAO.updateOrderStatus(company, order, orderStatus);
            } catch (DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, String.format("OrderController updateOrderStatus error: %s", e));
            }
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid user, only COMPANY could update order status");
        }
    }
}
