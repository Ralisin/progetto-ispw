package it.ralisin.littlefarmers.enums;

public enum UserRole {
    CUSTOMER("customer"),
    COMPANY("company"),
    NONE("none");

    private final String id;

    UserRole(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
