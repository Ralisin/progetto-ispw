package dao;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.LoginDAO;
import it.ralisin.littlefarmers.dao.queries.ProductsDAO;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Customer;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class CustomerDAOTest {
    @Test
    void testConnectionFactory() {
        Connection conn = ConnectionFactory.getConnection();

        Assertions.assertNotNull(conn);
    }

    @Test
    void testGetUser() throws DAOException, SQLException {
        User user = LoginDAO.getUser("customer1@gmail.com", "0000");
        assert user != null;
        Assertions.assertEquals(UserRole.CUSTOMER, user.getRole());

        user = LoginDAO.getUser("company1@gmail.com", "0000");
        assert user != null;
        Assertions.assertEquals(UserRole.COMPANY, user.getRole());

        user = LoginDAO.getUser("test@gmail.com", "0000");
        Assertions.assertNull(user);
    }

    @Test
    void testCustomerGetProductsByRegion() throws SQLException, DAOException {
        // Check all regions
        for(Regions region : Regions.values()) {
            List<Product> productList = ProductsDAO.getProductsByRegion(region);
            Assertions.assertNotNull(productList);
        }
    }

    @Test
    void testCustomerGetCart() throws DAOException, SQLException {
        List<Product> productList = CustomerDAO.getCart(new Customer("customer2@gmail.com", null, null, null));
        Assertions.assertNotNull(productList);

        productList.forEach(System.out::println);
    }

    @Test
    void testCustomerAddToCart() throws DAOException {
        Customer customer = new Customer("customer1@gmail.com", null, null, null);
        Product product = new Product(1, "Macinato 500g", null, 6.90F, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
        boolean result = CustomerDAO.addToCart(customer, product, 5);

        Assertions.assertTrue(result);
    }

    @Test
    void testCustomerRemoveFromCart() throws DAOException {
        Customer customer = new Customer("customer1@gmail.com", null, null, null);
        Product product = new Product(1, "Macinato 500g", null, 6.90F, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
        boolean result = CustomerDAO.removeFromCart(customer, product);

        Assertions.assertTrue(result);
    }

    @Test
    void testCustomerGetInfo() throws DAOException, SQLException {
        User user = new User("customer1@gmail.com", "0000", UserRole.CUSTOMER);
        Customer customer = CustomerDAO.getCustomerInfo(user);

        Assertions.assertNotNull(customer);
    }
}
