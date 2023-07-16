package it.ralisin.littlefarmers.enums;

public enum Categories {
    CARNE("carne"),
    FRUTTA("frutta"),
    VERDURA("verdura"),
    FORMAGGIO("formaggio"),
    PASTA("pasta"),
    LEGUMI("legumi");

    private final String category;

    Categories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
