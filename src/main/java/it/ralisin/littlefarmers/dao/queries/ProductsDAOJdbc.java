package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.AbsDAOJdbc;
import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDAOJdbc extends AbsDAOJdbc implements ProductDAO {
    static final String SQL = "select * from products";

    public List<Product> getProducts() {
        Connection conn = ConnectionFactory.getConnection();

        try (
            PreparedStatement ps = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery()
        ) {
            return getProductsList(rs);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting products %s", e));
        }

        return new ArrayList<>();
    }

    public List<Product> getProductsByRegion(Regions region) {
        return executeQuery(region.getRegionString());
    }

    public List<Product> getProductByCompany(User company) {
        return executeQuery(company.getEmail());
    }

    private static List<Product> getProductsList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            productList.add(getProductFromResultSet(rs, false));
        }

        return productList;
    }

    private List<Product> executeQuery(String toBeInserted) {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = SQL + " where region = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, toBeInserted);
            rs = ps.executeQuery();

            productList = getProductsList(rs);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting products by region %s", e));
        }  finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on closing rs %s", e));
                }
            }
        }

        return productList;
    }
}
