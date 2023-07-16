package dao;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.dao.queries.LoginDAO;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class DAOTest {
    @Test
    void testConnectionFactory() {
        Connection conn = ConnectionFactory.getConnection();

        Assertions.assertNotNull(conn);
    }

    @Test
    void testGetUser() throws DAOException {
        ConnectionFactory.changeConnection("login", "login");

        User user = LoginDAO.getUser("customer1@gmail.com", "0000");
        assert user != null;
        Assertions.assertEquals(user.getRole(), UserRole.CUSTOMER);
        LoginDAO.logOut();

        user = LoginDAO.getUser("company1@gmail.com", "0000");
        assert user != null;
        Assertions.assertEquals(user.getRole(), UserRole.COMPANY);
        LoginDAO.logOut();

        user = LoginDAO.getUser("test@gmail.com", "0000");
        Assertions.assertNull(user);
    }

    @Test
    void testCustomerDAO() throws DAOException {
        ConnectionFactory.changeConnection("customer", "customer");

        // Check all regions
        for(Regions region : Regions.values()) {
            List<Product> productList = CustomerDAO.getProductsByRegion(region);
            Assertions.assertNotNull(productList);
        }
    }

    @Test
    void testCustomerGetCart() throws DAOException {
        ConnectionFactory.changeConnection("customer", "customer");

        List<Product> productList = CustomerDAO.getCart(new User("customer2@gmail.com", "0000", UserRole.CUSTOMER));
        Assertions.assertNotNull(productList);

        productList.forEach(System.out::println);
    }

    @Test
    void testCustomerAddToCart() throws DAOException {
        ConnectionFactory.changeConnection("customer", "customer");

        User user = new User("customer1@gmail.com", "0000", UserRole.CUSTOMER);
        Product product = new Product(1, "Macinato 500g", null, 6.90F, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
        Boolean result = CustomerDAO.addToCart(user, product, 5);

        Assertions.assertTrue(result);
    }

    @Test
    void testCustomerRemoveFromCart() throws DAOException {
        ConnectionFactory.changeConnection("customer", "customer");

        User user = new User("customer1@gmail.com", "0000", UserRole.CUSTOMER);
        Product product = new Product(1, "Macinato 500g", null, 6.90F, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
        Boolean result = CustomerDAO.removeFromCart(user, product);

        Assertions.assertTrue(result);
    }
}
