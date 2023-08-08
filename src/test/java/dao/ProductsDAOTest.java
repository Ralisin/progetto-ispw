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

class ProductsDAOTest {
    @Test
    void testGetProducts() throws DAOException {
        List<Product> productList = ProductsDAO.getProducts();
        Assertions.assertNotNull(productList);
    }

    @Test
    void testGetProductsByRegion() throws SQLException, DAOException {
        // Check all regions
        for(Regions region : Regions.values()) {
            List<Product> productList = ProductsDAO.getProductsByRegion(region);
            Assertions.assertNotNull(productList);
        }
    }

    @Test
    void testGetProductsByCompany() throws DAOException, SQLException {
        Company company = new Company("company1@gmail.com", "test","test", "test", Regions.ABRUZZO);

        List<Product> productList = ProductsDAO.getProductByCompany(company);

        Assertions.assertNotNull(productList);
    }
}
