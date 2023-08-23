package it.ralisin.littlefarmers.beans;

import it.ralisin.littlefarmers.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {
    List<Order> orderList = new ArrayList<>();
    Order order = null;

    public OrderBean(List<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderBean(Order order) {
        this.order = order;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
