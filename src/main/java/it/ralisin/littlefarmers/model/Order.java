package it.ralisin.littlefarmers.model;

import it.ralisin.littlefarmers.enums.OrderStatus;

public class Order {
    private final int id;
    private final String companyEmail;
    private final String customerEmail;
    private final String date;
    private final OrderStatus status;

    public Order(int id, String companyEmail, String customerEmail, String date, OrderStatus status) {
        this.id = id;
        this.companyEmail = companyEmail;
        this.customerEmail = customerEmail;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format(
                "[id: %d, companyEmail: %s, customerEmail: %s, date: %s, status: %s]",
                id, companyEmail, customerEmail, date, status
        );
    }
}
