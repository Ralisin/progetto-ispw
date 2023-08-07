package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Customer;
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

    public static Customer getCustomerInfo(User user) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();
        ResultSet rs = null;

        String sql = "select * from customer where email = ?";
        Customer customer = null;

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, user.getEmail());
            rs = ps.executeQuery();

            if (rs.next()) {
                String emailDb = rs.getString("email");
                String nameDb = rs.getString("name");
                String surnameDb = rs.getString("surname");
                String addressDb = rs.getString("address");

                customer = new Customer(emailDb, nameDb, surnameDb, addressDb);
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting products by region", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return customer;
    }

    public static List<Product> getCart(Customer customer) throws SQLException, DAOException {
        List<Product> productList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        String sql = "select products.productId, productName, productDescription, price, category, imageLink " +
                "from cart join products on cart.productId = products.productId " +
                "where cart.customerEmail = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, customer.getEmail());

            rs = ps.executeQuery();

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
            throw new DAOException("Error on getting cart", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    public static boolean addToCart(Customer customer, Product product, int quantity) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into cartItem (shoppingSession_id, productId, quantity) " +
                "value ((select max(id) from shoppingSession where customer_email = ?), ?, ?) " +
                "on duplicate key update quantity = ?";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, customer.getEmail());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error on adding to cart", e);
        }

        return affectedRows > 0;
    }

    public static boolean removeFromCart(Customer customer, Product product) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "delete from cart where productId = ? and customerEmail = ?";
        int affectedRows;

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, product.getProductId());
            ps.setString(2, customer.getEmail());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error on deleting from cart", e);
        }

        return affectedRows > 0;
    }

    public static boolean requestCart(Customer customer) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();

        List<Company> companyList = CompanyDAO.getCompanyList();

        for(Company company : companyList) {

        }

        return true;
    }
}
