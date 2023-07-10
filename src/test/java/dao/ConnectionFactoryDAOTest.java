package dao;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryDAOTest {
    @Test
    void connectionFactoryTest() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        Assertions.assertNotNull(conn);
    }
}
