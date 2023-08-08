package it.ralisin.littlefarmers.enums;

public enum RequestStatus {
    WAITING("waiting"),
    ACCEPTED("accepted"),
    DENIED("denied"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    final String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
