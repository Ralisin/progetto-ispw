package dao;

import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Customer;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class CustomerDAOTest {
    @Test
    void testGetCustomer() throws DAOException, SQLException {
        User user = new User("customer1@gmail.com", "0000", UserRole.CUSTOMER);

        Customer customer = CustomerDAO.getCustomer(user);

        Assertions.assertNotNull(customer);
    }

    @Test
    void testCustomerGetCart() throws DAOException, SQLException {
        Customer customer = new Customer("customer1@gmail.com", null, null, null);
        Product product = new Product(1, "Macinato 500g", null, 6.90F, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
        CustomerDAO.addToCart(customer, product, 5);

        List<Product> productList = CustomerDAO.getCart(customer);
        Assertions.assertFalse(productList.isEmpty());

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
        CustomerDAO.addToCart(customer, product, 5);

        boolean result = CustomerDAO.removeFromCart(customer, product);

        Assertions.assertTrue(result);
    }
}
