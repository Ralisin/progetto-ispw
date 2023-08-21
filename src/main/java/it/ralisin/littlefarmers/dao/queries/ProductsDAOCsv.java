package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.util.List;

public class ProductsDAOCsv implements ProductDAO{
    public ProductsDAOCsv() {}

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public List<Product> getProductsByRegion(Regions region) {
        return null;
    }

    @Override
    public List<Product> getProductByCompany(User company) {
        return null;
    }
}
