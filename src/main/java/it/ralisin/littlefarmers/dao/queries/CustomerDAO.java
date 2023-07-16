package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private CustomerDAO() {}

    public static List<Product> getProductsByRegion(Regions region) throws SQLException {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select productId, productName, productDescription, price, category,  imageLink " +
                "from products join (select * from company where region = ?) as companyParsed " +
                "on products.companyEmail = companyParsed.email";

        PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, region.getRegion());
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int productId = rs.getInt("productId");
            String productName = rs.getString("productName");
            String productDescription = rs.getString("productDescription");
            float price = rs.getFloat("price");
            String category = rs.getString("category");
            String imageLink = rs.getString("imageLink");

            productList.add(new Product(productId, productName, productDescription, price, category, imageLink, region));
        }

        rs.close();
        ps.close();

        return productList;
    }

    public static List<Product> getCart(User user) throws SQLException {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select products.productId, productName, productDescription, price, category, imageLink " +
                "from cart join products on cart.productId = products.productId " +
                "where cart.customerEmail = ?";

        PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, user.getEmail());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int productId = rs.getInt("products.productId");
            String productName = rs.getString("productName");
            String productDescription = rs.getString("productDescription");
            float price = rs.getFloat("price");
            String category = rs.getString("category");
            String imageLink = rs.getString("imageLink");

            productList.add(new Product(productId, productName, productDescription, price, category, imageLink));
        }

        rs.close();
        ps.close();

        return productList;
    }

    public static boolean addToCart(User user, Product product, int quantity) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();

        boolean result = false;

        CallableStatement cs = conn.prepareCall("insert into cart (customerEmail, productId, quantity) " +
                        "value (?, ?, ?) on duplicate key update quantity = ?");
        cs.setString(1, user.getEmail());
        cs.setInt(2, product.getProductId());
        cs.setInt(3, quantity);
        cs.setInt(4, quantity);

        int affectedRows = cs.executeUpdate();
        if(affectedRows > 0) result = true;

        closeCs(cs);

        return result;
    }

    public static boolean removeFromCart(User user, Product product) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();

        boolean result = false;

        CallableStatement cs = conn.prepareCall("delete from cart where productId = ? and customerEmail = ?");
        cs.setInt(1, product.getProductId());
        cs.setString(2, user.getEmail());

        int affectedRows = cs.executeUpdate();

        if(affectedRows > 0) result = true;

        closeCs(cs);

        return result;
    }

    private static void closeRs(ResultSet rs) throws DAOException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing connection instance");
            }
        }
    }

    private static void closeCs(CallableStatement cs) throws DAOException {
        if (cs != null) {
            try {
                cs.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing connection instance");
            }
        }
    }

    private static void closeConn(Connection conn) throws DAOException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing connection instance");
            }
        }
    }
}
