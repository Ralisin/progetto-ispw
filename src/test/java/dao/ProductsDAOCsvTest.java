package dao;

import it.ralisin.littlefarmers.dao.queries.ProductsDAOCsv;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ProductsDAOCsvTest {
    User company = new User("company1@gmail.com", "test", UserRole.COMPANY);

    public ProductsDAOCsvTest() {}

    @Test
    void testGetProducts() throws IOException {
        List<Product> productList = new ProductsDAOCsv().getProducts();

        productList.forEach(System.out::println);

        Assertions.assertNotNull(productList);
    }

    @Test
    void testGetProductsByRegion() throws IOException {
        List<Product> productList = new ProductsDAOCsv().getProductsByRegion(Regions.ABRUZZO);

        productList.forEach(System.out::println);

        Assertions.assertNotNull(productList);
    }

    @Test
    void testGetProductsByCompany() throws IOException {
        List<Product> productList = new ProductsDAOCsv().getProductByCompany(company);

        productList.forEach(System.out::println);

        Assertions.assertNotNull(productList);
    }
}
