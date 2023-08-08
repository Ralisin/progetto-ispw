package it.ralisin.littlefarmers.model;

public class Company {
    protected String email;
    protected String name;
    protected String iban;
    protected String address;

    public Company(String email, String name, String iban, String address) {
        this.email = email;
        this.name = name;
        this.iban = iban;
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

    public String getAddress() {
        return address;
    }
}
