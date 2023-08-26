package dao;

import it.ralisin.littlefarmers.dao.queries.ProductsDAOJdbc;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductsDAOJdbcTest {
    User company = new User("company1@gmail.com", "test", UserRole.COMPANY);

    @Test
    void testGetProducts() {
        List<Product> productList = new ProductsDAOJdbc().getProducts();
        Assertions.assertNotNull(productList);
    }

    @Test
    void testGetProductsByRegion() {
        // Check all regions
        for(Regions region : Regions.values()) {
            List<Product> productList = new ProductsDAOJdbc().getProductsByRegion(region);

            System.out.println(productList);

            Assertions.assertNotNull(productList);
        }
    }

    @Test
    void testGetProductsByCompany() {
        List<Product> productList = new ProductsDAOJdbc().getProductByCompany(company);

        productList.forEach(System.out::println);

        Assertions.assertNotNull(productList);
    }
}
