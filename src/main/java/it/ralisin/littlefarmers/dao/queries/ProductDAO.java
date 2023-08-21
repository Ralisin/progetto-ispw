package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.util.List;

public interface ProductDAO {
    List<Product> getProducts();

    List<Product> getProductsByRegion(Regions region);

    List<Product> getProductByCompany(User company);
}
