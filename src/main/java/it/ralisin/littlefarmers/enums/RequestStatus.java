package it.ralisin.littlefarmers.enums;

public enum RequestStatus {
    SENT("sent"),
    ACCEPTED("accepted"),
    DENIED("denied"),
    ACCEPTEDNOTIFIED("acceptedNotified"),
    DENIEDNOTIFIED("deniedNotified"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
