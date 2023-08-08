package dao;

import it.ralisin.littlefarmers.dao.queries.CompanyDAO;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CompanyDAOTest {
    @Test
    void getCompanyList() throws DAOException {
        List<Company> cl = CompanyDAO.getCompanyList();

        Assertions.assertNotNull(cl);
    }

    @Test
    void testInsertNewProduct() throws DAOException {
        Company company = new Company("company1@gmail.com", "test","test", "test", Regions.ABRUZZO);
        Product product = new Product(0, "test", "test", 1f, Regions.ABRUZZO, "test", "test");

        boolean result = CompanyDAO.insertProduct(company, product);
        Assertions.assertTrue(result);
    }
}
