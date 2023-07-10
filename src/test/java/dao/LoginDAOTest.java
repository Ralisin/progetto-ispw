package dao;

import it.ralisin.littlefarmers.dao.procedures.Login;
import it.ralisin.littlefarmers.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LoginDAOTest {
    @Test
    void testLoginFail() throws SQLException {
        Role userRole = Login.login("email", "pass");

        Assertions.assertEquals(userRole, Role.NONE);
    }

    @Test
    void testLoginCompanySuccess() throws SQLException {
        Role userRole = Login.login("company1@gmail.com", "0000");

        Assertions.assertEquals(userRole, Role.COMPANY);
    }

    @Test
    void testLoginCustomerSuccess() throws SQLException {
        Role userRole = Login.login("customer1@gmail.com", "0000");

        Assertions.assertEquals(userRole, Role.CUSTOMER);
    }
}
