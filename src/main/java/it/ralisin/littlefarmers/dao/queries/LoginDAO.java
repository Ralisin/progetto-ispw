package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;

import java.sql.*;

public class LoginDAO {
    private LoginDAO() {}

    public static User getUser(String email, String psw) throws DAOException, SQLException {
        Connection conn = ConnectionFactory.getConnection();

        ResultSet rs = null;

        User user = null;

        String sql = "select * from users where email = ? and password = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, email);
            ps.setString(2, psw);

            rs = ps.executeQuery();

            if (rs.next()) {
                String emailDB = rs.getString("email");
                String pswDB = rs.getString("password");
                String roleDB = rs.getString("role");

                if (roleDB.equals("customer")) user = new User(emailDB, pswDB, UserRole.CUSTOMER);
                else if (roleDB.equals("company")) user = new User(emailDB, pswDB, UserRole.COMPANY);
            }
        } catch (SQLException e) {
            throw new DAOException("Error on getting user", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return user;
    }

    public static boolean addUser(String email, String psw, UserRole role) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        String sql = "insert into users (email, password, role) VALUES (?, ?, ?)";

        int affectedRows;

        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, email);
            ps.setString(2, psw);
            ps.setString(3, role.name());

            affectedRows = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error inserting product", e);
        }

        return affectedRows > 0;
    }
}
