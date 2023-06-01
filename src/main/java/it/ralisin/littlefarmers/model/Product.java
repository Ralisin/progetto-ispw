package it.ralisin.littlefarmers.model;

public class Product {
    private String companyEmail;
    private int productId;
    private String name;
    private String description;
    private float price;
    private String imageLink;
    private String category;

    public Product(String companyEmail, int productId, String name, String description, float price, String category, String imageLink) {
        this.companyEmail = companyEmail;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageLink = imageLink;
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

    public String getCategory() {
        return category;
    }
}
