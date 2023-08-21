package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.AbsDAOJdbc;
import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbsDAOJdbc {

    protected static List<Order> getOrders(String email, String sql) throws DAOException, SQLException {
        List<Order> orderList = new ArrayList<>();
        Connection conn = ConnectionFactory.getConnection();

        ResultSet rs = null;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, email);

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
        } finally {
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
}
