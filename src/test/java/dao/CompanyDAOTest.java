package dao;

import it.ralisin.littlefarmers.dao.queries.CompanyDAO;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class CompanyDAOTest {
    @Test
    public void getCompanyList() throws DAOException, SQLException {
        List<Company> cl = CompanyDAO.getCompanyList();

        Assertions.assertNotNull(cl);
    }
}
