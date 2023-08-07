package it.ralisin.littlefarmers.model;

import it.ralisin.littlefarmers.enums.Regions;

public class Company {
    protected String email;
    protected String name;
    protected String iban;
    protected String address;
    protected Regions region;

    public Company(String email, String name, String iban, String address, Regions region) {
        this.email = email;
        this.name = name;
        this.iban = iban;
        this.region = region;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getIban() {
        return iban;
    }

    public Regions getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }
}
