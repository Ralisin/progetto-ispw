package dao;

import it.ralisin.littlefarmers.dao.queries.ProductsDAO;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ProductsDAOTest {
    @Test
    void testGetProductsByRegion() throws SQLException, DAOException {
        // Check all regions
        for(Regions region : Regions.values()) {
            List<Product> productList = ProductsDAO.getProductsByRegion(region);
            Assertions.assertNotNull(productList);
        }
    }

    @Test
    void testInsertNewProduct() throws DAOException {
        Company company = new Company("company1@gmail.com", "test","test", "test", Regions.ABRUZZO);
        Product product = new Product(0, "test", "test", 1f, Regions.ABRUZZO, "test", "test");

        Boolean result = ProductsDAO.insertProduct(company, product);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetProductsByCompany() throws DAOException, SQLException {
        Company company = new Company("company1@gmail.com", "test","test", "test", Regions.ABRUZZO);

        List<Product> productList = ProductsDAO.getProductByCompany(company);

        Assertions.assertNotNull(productList);
    }
}
