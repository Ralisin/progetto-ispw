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

public class ProductsDAO {
    private ProductsDAO() {}

    public static List<Product> getProductsByRegion(Regions region) throws SQLException, DAOException {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select productId, productName, productDescription, price, region, category, imageLink\n" +
                "from products where region = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, region.getRegionString());
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                float price = rs.getFloat("price");
                String productRegion = rs.getString("region");
                String imageLink = rs.getString("imageLink");
                String category = rs.getString("category");

                productList.add(new Product(productId, productName, productDescription, price, Regions.getByRegion(productRegion), category, imageLink));
            }
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
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select productId, productName, productDescription, price, region, category, imageLink\n" +
                "from products where companyEmail = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, company.getEmail());
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                float price = rs.getFloat("price");
                String imageLink = rs.getString("imageLink");
                String productRegion = rs.getString("region");
                String category = rs.getString("category");

                productList.add(new Product(productId, productName, productDescription, price, Regions.getByRegion(productRegion), category, imageLink));
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting products by region", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    public static Boolean insertProduct(Company company, Product product) throws DAOException {
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
