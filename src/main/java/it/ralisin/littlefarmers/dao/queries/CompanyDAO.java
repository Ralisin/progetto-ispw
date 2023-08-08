package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    private CompanyDAO() {}

    public static List<Company> getCompanyList() throws DAOException {
        List<Company> companyList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select * from company";
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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
        }

        return companyList;
    }

    public static boolean insertProduct(Company company, Product product) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into products(companyEmail, productName, productDescription, price, region, category, imageLink) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, company.getEmail());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setFloat(4, product.getPrice());
            ps.setString(5, product.getRegion().getRegionString());
            ps.setString(6, product.getCategory());
            ps.setString(7, product.getImageLink());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error on adding to cart", e);
        }

        return affectedRows > 0;
    }
}
