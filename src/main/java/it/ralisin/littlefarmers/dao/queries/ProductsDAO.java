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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDAO {
    private ProductsDAO() {}

    static final String SQL = "select * from products";

    public static List<Product> getProducts() throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        try (
            PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery()
        ) {
            return getProductsList(rs);
        } catch (SQLException e) {
            throw new DAOException("Error on getting products", e);
        }
    }

    public static List<Product> getProductsByRegion(Regions region) throws SQLException, DAOException {
        List<Product> productList;

        Connection conn = ConnectionFactory.getConnection();

        String sql = SQL + " where region = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, region.getRegionString());
            rs = ps.executeQuery();

            productList = getProductsList(rs);
        } catch (SQLException e) {
            throw new DAOException("Error on getting products by region", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    public static List<Product> getProductByCompany(Company company) throws DAOException, SQLException {
        List<Product> productList;

        Connection conn = ConnectionFactory.getConnection();

        String sql = SQL + " where companyEmail = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, company.getEmail());
            rs = ps.executeQuery();

            productList = getProductsList(rs);
        } catch (SQLException e) {
            throw new DAOException("Error on getting products by company", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    public static Product getProductFromResultSet(ResultSet rs) throws SQLException {
        String companyEmail = rs.getString("companyEmail");
        int productId = rs.getInt("productId");
        String name = rs.getString("productName");
        String description = rs.getString("productDescription");
        float price = rs.getFloat("price");
        int quantity = 0;
        try { quantity = rs.getInt("quantity"); } catch (SQLException ignored) {
            Logger.getAnonymousLogger().log(Level.INFO, ignored.getMessage());}
        Regions region = Regions.getByRegionString(rs.getString("region"));
        String category = rs.getString("category");
        String imageLink = rs.getString("imageLink");

        return new Product(companyEmail, productId, name, description, price, region, category, imageLink, quantity);
    }

    private static List<Product> getProductsList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            productList.add(getProductFromResultSet(rs));
        }

        return productList;
    }
}
