package it.ralisin.littlefarmers.enums;

import java.util.Objects;

public enum OrderStatus {
    WAITING("waiting"),
    ACCEPTED("accepted"),
    DENIED("denied"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static OrderStatus getByString(String status) {
        for(OrderStatus os : OrderStatus.values()) {
            if(Objects.equals(os.getStatus(), status)) return os;
        }

        throw new IllegalArgumentException("No orderStatus constant with ID: " + status);
    }
}
