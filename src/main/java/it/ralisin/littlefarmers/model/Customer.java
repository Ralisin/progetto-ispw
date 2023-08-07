package it.ralisin.littlefarmers.model;

public class Customer {
    String email;
    String name;
    String surname;
    String address;

    public Customer(String email, String name, String surname, String address) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }
}
