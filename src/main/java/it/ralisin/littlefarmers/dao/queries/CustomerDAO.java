package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Customer;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private CustomerDAO() {}

    /** Method to get Customer by given User, needed on login action */
    public static Customer getCustomer(User user) throws DAOException, SQLException {
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

        String sql = "select products.productId, productName, productDescription, price, region, category, imageLink " +
                "from cart join products on cart.productId = products.productId " +
                "where cart.customerEmail = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, customer.getEmail());

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = ProductsDAO.getProductFromResultSet(rs);

                productList.add(product);
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

        String sql = "insert into cart(customerEmail, productId, quantity)" +
                "value (?, ?, ?)" +
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

    public static List<Order> getOrders(Customer customer) throws DAOException, SQLException {
        List<Order> orderList = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        String sql = "select * from orders where customerEmail = ?";

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, customer.getEmail());

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String companyEmail = rs.getString("companyEmail");
                String customerEmail = rs.getString("customerEmail");
                String date = rs.getString("date");
                String status = rs.getString("status");

                orderList.add(new Order(id, companyEmail, customerEmail, date, OrderStatus.getByString(status)));
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting orders", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return orderList;
    }

    public static List<Product> getOrderProducts(Order order) throws DAOException, SQLException {
        List<Product> productList = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        ResultSet rs = null;

        String sql = "select * from orderItems join products on orderItems.productId = products.productId where orderId = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, order.getId());

            rs = ps.executeQuery();

            while (rs.next()) {
                String email = rs.getString("companyEmail");
                int productId = rs.getInt("productId");
                String name = rs.getString("productName");
                String description = rs.getString("productDescription");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                String region = rs.getString("region");
                String category = rs.getString("category");
                String imageLink = rs.getString("imageLink");

                productList.add(new Product(email, productId, name, description, price, quantity, Regions.getByRegionString(region), category, imageLink));
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting orders", e);
        }  finally {
            if (rs != null) {
                rs.close();
            }
        }

        return productList;
    }

    /*
    * Sql to insert order:
    *   INSERT INTO orders (`id`, `companyEmail`, `customerEmail`, `date`, `status`)
        VALUES ('0', 'company2@gmail.com', 'customer2@gmail.com', current_date(), 'waiting');
    * */
}
