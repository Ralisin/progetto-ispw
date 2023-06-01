package dao;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class CompanyDAOTest {
    @Test
    void connectDB() throws SQLException {
        Connection conn = null;
        conn = ConnectionFactory.getConnection();


    }
}
