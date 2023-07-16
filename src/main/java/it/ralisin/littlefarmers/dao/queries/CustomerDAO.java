package it.ralisin.littlefarmers.dao.queries;

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

public class CustomerDAO {
    private CustomerDAO() {}

    public static List<Product> getProductsByRegion(Regions region) throws SQLException {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select productId, productName, productDescription, price, category,  imageLink " +
                "from products join (select * from company where region = ?) as companyParsed " +
                "on products.companyEmail = companyParsed.email";

//        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, region.getRegion());
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                String imageLink = rs.getString("imageLink");

                productList.add(new Product(productId, productName, productDescription, price, category, imageLink, region));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

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

    public static boolean addToCart(User user, Product product, int quantity) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into cart (customerEmail, productId, quantity) " +
                "value (?, ?, ?) on duplicate key update quantity = ?";

        PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, user.getEmail());
        ps.setInt(2, product.getProductId());
        ps.setInt(3, quantity);
        ps.setInt(4, quantity);

        int affectedRows = ps.executeUpdate();
        ps.close();

        return affectedRows > 0;
    }

    public static boolean removeFromCart(User user, Product product) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "delete from cart where productId = ? and customerEmail = ?";

        PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, product.getProductId());
        ps.setString(2, user.getEmail());

        int affectedRows = ps.executeUpdate();
        ps.close();

        return affectedRows > 0;
    }
}
