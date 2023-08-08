package it.ralisin.littlefarmers.model;

import it.ralisin.littlefarmers.enums.Regions;

public class Product {
    private final String companyEmail;
    private final int productId;
    private final String name;
    private final String description;
    private float price;
    private final Regions region;
    private final String category;
    private final String imageLink;
    private int quantity = -1;

    public Product(String companyEmail, int productId, String name, String description, float price, int quantity, Regions region, String category, String imageLink) {
        this.companyEmail = companyEmail;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageLink = imageLink;
        this.region = region;
    }

    public Product(String companyEmail, int productId, String name, String description, float price, Regions region, String category, String imageLink) {
        this.companyEmail = companyEmail;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageLink = imageLink;
        this.region = region;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public Regions getRegion() {
        return region;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "[id: %d, name: %s, description: %s, price: %f, category: %s, imageLink: %s, region: %s]",
                productId, name, description, price, category, imageLink, region
        );
    }
}
