package it.ralisin.littlefarmers.model;

public class Company {
    private String email;
    private String name;
    private String iban;
    private String region;
    private String address;

    public Company(String email, String name, String iban, String region, String address) {
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

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }
}
