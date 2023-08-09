package dao;

import it.ralisin.littlefarmers.dao.queries.CustomerDAO;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Customer;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class CustomerDAOTest {
    private final User user = new User("customer1@gmail.com", "0000", UserRole.CUSTOMER);
    private final Customer customer = new Customer("customer1@gmail.com", null, null, null);
    private final Product product = new Product("company1@gmail.com", 1, "Macinato 500g", null, 6.90F, Regions.ABRUZZO, "carne", "https://i0.wp.com/www.alpassofood.com/wp-content/uploads/2022/07/Carne-macinata.jpeg?fit=853%2C853&ssl=1");
    private final Order order = new Order(1, "company1@gmail.com", "customer1@gmail.com", "2023-08-08", OrderStatus.WAITING);

    @Test
    void testGetCustomer() throws DAOException, SQLException {
        Customer customer = CustomerDAO.getCustomer(user);

        Assertions.assertNotNull(customer);
    }

    @Test
    void testCustomerGetCart() throws DAOException, SQLException {
        CustomerDAO.addToCart(customer, product, 5);

        List<Product> productList = CustomerDAO.getCart(customer);
        Assertions.assertFalse(productList.isEmpty());

        productList.forEach(System.out::println);
    }

    @Test
    void testCustomerAddToCart() throws DAOException {
        boolean result = CustomerDAO.addToCart(customer, product, 2);

        Assertions.assertTrue(result);
    }

    @Test
    void testCustomerRemoveFromCart() throws DAOException {
        CustomerDAO.addToCart(customer, product, 5);

        boolean result = CustomerDAO.removeFromCart(customer, product);

        Assertions.assertTrue(result);
    }

    @Test
    void testGetOrders() throws DAOException, SQLException {
        List<Order> orderList = CustomerDAO.getOrders(customer);

        Assertions.assertFalse(orderList.isEmpty());
    }

    @Test
    void testGetOrderProducts() throws DAOException, SQLException {
        List<Product> productList = CustomerDAO.getOrderProducts(order);

        Assertions.assertNotNull(productList);
    }

    @Test
    void testMakeOrder() throws DAOException, SQLException {
        Assertions.assertTrue(CustomerDAO.makeOrder(customer));
    }

    @Test
    void testGetOrdersByStatus() throws DAOException, SQLException {
        List<Order> orderList = CustomerDAO.getOrdersByStatus(customer, OrderStatus.WAITING);

        Assertions.assertFalse(orderList.isEmpty());
    }
}

