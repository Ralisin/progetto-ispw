package dao;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.dao.queries.LoginDAO;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOTest {
    @Test
    void testConnectionFactory() {
        Connection conn = ConnectionFactory.getConnection();

        Assertions.assertNotNull(conn);
    }

    @Test
        // Test method getUser of LoginDAO
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
}
