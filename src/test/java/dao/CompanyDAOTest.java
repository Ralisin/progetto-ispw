package dao;

import it.ralisin.littlefarmers.dao.queries.CompanyDAO;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class CompanyDAOTest {
    User company = new User("company1@gmail.com", "test", UserRole.COMPANY);
    Product product = new Product("company1@gmail.com", 0, "test1", "test", Regions.ABRUZZO, "test", "test");
    Order order = new Order(1, "company1@gmail.com", "customer1@gmail.com", null, OrderStatus.DELIVERED);

    private CompanyDAOTest() {}

    @Test
    void testInsertProduct() throws DAOException {
        boolean result = CompanyDAO.insertProduct(company, product);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteProduct() throws DAOException {
        boolean result = CompanyDAO.deleteProduct(company, product);

        Assertions.assertTrue(result);
    }

    @Test
    void testGetOrders() throws DAOException, SQLException {
        List<Order> orderList = CompanyDAO.getOrders(company);

        Assertions.assertFalse(orderList.isEmpty());
    }

    @Test
    void testGetOrdersByStatus() throws DAOException, SQLException {
        List<Order> orderList = CompanyDAO.getOrdersByStatus(company, OrderStatus.ACCEPTED);

        Assertions.assertFalse(orderList.isEmpty());
    }

    @Test
    void testUpdateOrderStatus() throws DAOException {
        boolean result = CompanyDAO.updateOrderStatus(company, order, OrderStatus.DELIVERED);

        Assertions.assertTrue(result);
    }
}
