package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    private CompanyDAO() {}

    public static List<Company> getCompanyList() throws DAOException, SQLException {
        List<Company> companyList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql = "select * from company";

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            rs = ps.executeQuery();

            while(rs.next()) {
                String emailDb = rs.getString("email");
                String nameDb = rs.getString("name");
                String ibanDb = rs.getString("iban");
                String addressDb = rs.getString("address");
                Regions regionDb = Regions.getByRegion(rs.getString("region"));

                Company company = new Company(emailDb, nameDb, ibanDb, addressDb, regionDb);
                companyList.add(company);
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting companies list", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return companyList;
    }
}
