package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Company;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO extends OrderDAO {
    private CompanyDAO() {}

    public static Company getCompany(User user) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "select * from company where email = ?";

        Company company = null;

        ResultSet rs = null;

        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, user.getEmail());

            rs = ps.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String name = rs.getString("name");
                String iban = rs.getString("iban");
                String address = rs.getString("address");

                company = new Company(email, name, iban, address);
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting company", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return company;
    }

    // Valid also for updates
    public static boolean insertProduct(Company company, Product product) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into products(companyEmail, productId, productName, productDescription, price, region, category, imageLink) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?) on duplicate key update productName = ?, productDescription = ?, price = ?, region = ?, category = ?, imageLink = ?";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, company.getEmail());
            ps.setInt(2, product.getProductId());
            ps.setString(3, product.getName());
            ps.setString(4, product.getDescription());
            ps.setFloat(5, product.getPrice());
            ps.setString(6, product.getRegion().getRegionString());
            ps.setString(7, product.getCategory());
            ps.setString(8, product.getImageLink());

            // On duplicate
            ps.setString(9, product.getName());
            ps.setString(10, product.getDescription());
            ps.setFloat(11, product.getPrice());
            ps.setString(12, product.getRegion().getRegionString());
            ps.setString(13, product.getCategory());
            ps.setString(14, product.getImageLink());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting product", e);
        }

        return affectedRows > 0;
    }

    public static boolean deleteProduct(Company company, Product product) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "delete from products where productId = ? and companyEmail = ?";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, product.getProductId());
            ps.setString(2, company.getEmail());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting product", e);
        }

        return affectedRows > 0;
    }

    public static List<Order> getOrders(Company company) throws DAOException, SQLException {
        String sql = "select * from orders where companyEmail = ?";

        return getOrders(company.getEmail(), sql);
    }

    public static List<Order> getOrdersByStatus(Company company, OrderStatus status) throws DAOException, SQLException {
        String sql = "select * from orders where companyEmail = ? and status = '" + status.getStatus() + "'";

        return getOrders(company.getEmail(), sql);
    }

    public static boolean updateOrderStatus(Company company, Order order, OrderStatus newStatus) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "update orders set status = ? where companyEmail = ? and id = ?";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, newStatus.getStatus());
            ps.setString(2, company.getEmail());
            ps.setInt(3, order.getId());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating order status", e);
        }

        return affectedRows > 0;
    }
}
