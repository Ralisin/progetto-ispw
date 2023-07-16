package it.ralisin.littlefarmers.enums;

public enum Category {
    CARNE("carne"),
    FRUTTA("frutta"),
    VERDURA("verdura"),
    FORMAGGIO("formaggio"),
    PASTA("pasta"),
    LEGUMI("legumi");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
