package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private CustomerDAO() {}

    public static List<Product> getProductsByRegion(Regions region) {
        Connection conn = ConnectionFactory.getConnection();
        List<Product> productList = new ArrayList<>();

        try {
            CallableStatement cs = conn.prepareCall(
                    "select productId, productName, productDescription, price, category,  imageLink from products join " +
                    "(select * from company where region = ?) as companyParsed " +
                    "on products.companyEmail = companyParsed.email");
            cs.setString(1, region.getRegion());

            ResultSet rs = cs.executeQuery();

            while(rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                String imageLink = rs.getString("imageLink");

                productList.add(new Product(productId, productName, productDescription, price, category, imageLink, region));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    public static List<Product> getCart(User user) {
        Connection conn = ConnectionFactory.getConnection();
        List<Product> productList = new ArrayList<>();

        try {
            CallableStatement cs = conn.prepareCall(
                    "select products.productId, productName, productDescription, price, category, imageLink " +
                    "from cart join products on cart.productId = products.productId " +
                    "where cart.customerEmail = ?");
            cs.setString(1, user.getEmail());

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("products.productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                float price = rs.getFloat("price");
                String category = rs.getString("category");
                String imageLink = rs.getString("imageLink");

                productList.add(new Product(productId, productName, productDescription, price, category, imageLink));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    public static Boolean addToCart(User user, Product product, int quantity) {
        Connection conn = ConnectionFactory.getConnection();

        try {
            CallableStatement cs = conn.prepareCall("insert into cart (customerEmail, productId, quantity) " +
                            "value (?, ?, ?) on duplicate key update quantity = ?");
            cs.setString(1, user.getEmail());
            cs.setInt(2, product.getProductId());
            cs.setInt(3, quantity);
            cs.setInt(4, quantity);

            int affectedRows = cs.executeUpdate();

            if(affectedRows > 0) return true;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static Boolean removeFromCart(User user, Product product) {
        Connection conn = ConnectionFactory.getConnection();

        try {
            CallableStatement cs = conn.prepareCall("delete from cart where productId = ? and customerEmail = ?");
            cs.setInt(1, product.getProductId());
            cs.setString(2, user.getEmail());

            int affectedRows = cs.executeUpdate();

            if(affectedRows > 0) return true;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
