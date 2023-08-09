package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Customer;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "select companyEmail, products.productId, productName, productDescription, price, region, category, imageLink, quantity " +
                "from cart join products on cart.productId = products.productId " +
                "where cart.customerEmail = ?";
        return executeQueryAndProcessProducts(ConnectionFactory.getConnection(), sql, customer.getEmail());
    }

    // Valid also for updates
    public static boolean addToCart(Customer customer, Product product, int quantity) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into cart(customerEmail, productId, quantity) value (?, ?, ?) on duplicate key update quantity = ?";

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
        String sql = "select * from orderItems join products on orderItems.productId = products.productId where orderId = ?";
        return executeQueryAndProcessProducts(ConnectionFactory.getConnection(), sql, order.getId());
    }

    /** Make an order based on cart */
    public static boolean makeOrder(Customer customer) throws DAOException, SQLException {
        // Get cart products
        List<Product> cart = getCart(customer);
        if(cart.isEmpty()) throw new DAOException("Error, cart is empty, impossible to make an order");

        // Map company to products
        Map<String, List<Product>> companyEmailToProductsMap = new HashMap<>();
        for(Product p : cart) {
            String companyEmail = p.getCompanyEmail();
            companyEmailToProductsMap.putIfAbsent(companyEmail, new ArrayList<>());
            companyEmailToProductsMap.get(companyEmail).add(p);
        }

        // Insert all products into order
        for(Map.Entry<String, List<Product>> entry : companyEmailToProductsMap.entrySet()) {
            String key = entry.getKey();
            if(!insertOrderProducts(customer, companyEmailToProductsMap.get(key))) return false;
        }

        return true;
    }

    private static boolean insertOrderProducts(Customer customer, List<Product> productList) throws DAOException {
        if(productList.isEmpty()) throw new DAOException("Error, productList is empty");

        Connection conn = ConnectionFactory.getConnection();

        // Insert new order in list requests
        String sql = "insert into orders (id, companyEmail, customerEmail, date, status) " +
                "values ('0', ?, ?, current_date(), 'waiting')";

        String companyEmail = productList.get(0).getCompanyEmail();
        int orderId;

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, companyEmail);
            ps.setString(2, customer.getEmail());

            int affectedRows = ps.executeUpdate();

            if(affectedRows < 1) return false;

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) orderId = rs.getInt(1);
            else return false;
        } catch (SQLException e) {
            throw new DAOException("Error on inserting new order", e);
        }

        sql = "insert into orderitems (orderId, productId, quantity) values (?, ?, ?)";

        for(Product p : productList) {
            try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ps.setInt(1, orderId);
                ps.setInt(2, p.getProductId());
                ps.setInt(3, p.getQuantity());

                int affectedRows = ps.executeUpdate();

                if(affectedRows < 1) return false;
            } catch (SQLException e) {
                throw new DAOException("Error on inserting new order", e);
            }
        }

        sql = "delete from cart where customerEmail = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, customer.getEmail());

            int affectedRows = ps.executeUpdate();

            if(affectedRows <= 0) return false;
        } catch (SQLException e) {
            throw new DAOException("Error on cleaning cart for customer " + customer.getEmail(), e);
        }

        return true;
    }

    private static List<Product> executeQueryAndProcessProducts(Connection conn, String sql, Object... params) throws DAOException, SQLException {
        List<Product> productList = new ArrayList<>();
        ResultSet rs = null;

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = ProductsDAO.getProductFromResultSet(rs);
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
}
