package it.ralisin.littlefarmers.dao;

import it.ralisin.littlefarmers.dao.queries.ProductsDAOJdbc;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsDAOJdbc {
    protected static List<Product> executeQueryAndProcessProducts(Connection conn, String sql, Object... params) throws DAOException, SQLException {
        List<Product> productList = new ArrayList<>();
        ResultSet rs = null;

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = ProductsDAOJdbc.getProductFromResultSet(rs);
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while executing query and processing result set", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    protected static Product getProductFromResultSet(ResultSet rs) throws SQLException {
        String companyEmail = rs.getString("companyEmail");
        int productId = rs.getInt("productId");
        String name = rs.getString("productName");
        String description = rs.getString("productDescription");
        float price = rs.getFloat("price");
        int quantity = 0;

        try { quantity = rs.getInt("quantity"); }
        catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        Regions region = Regions.getByRegionString(rs.getString("region"));
        String category = rs.getString("category");
        String imageLink = rs.getString("imageLink");

        return new Product(companyEmail, productId, name, description, price, region, category, imageLink, quantity);
    }

    protected static Product getProductFromResultSet(ResultSet rs, boolean haveQuantity) throws SQLException {
        String companyEmail = rs.getString("companyEmail");
        int productId = rs.getInt("productId");
        String name = rs.getString("productName");
        String description = rs.getString("productDescription");
        float price = rs.getFloat("price");
        int quantity = -1; if(haveQuantity) quantity = rs.getInt("quantity");
        Regions region = Regions.getByRegionString(rs.getString("region"));
        String category = rs.getString("category");
        String imageLink = rs.getString("imageLink");

        return new Product(companyEmail, productId, name, description, price, region, category, imageLink, quantity);
    }
}
