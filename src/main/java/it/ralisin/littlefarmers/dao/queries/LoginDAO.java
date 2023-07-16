package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public static User getUser(String email, String psw) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs;
        ResultSet rs;

        User user = null;

        try {
            cs = conn.prepareCall("select * from users where email = ? and password = ?");
            cs.setString(1, email);
            cs.setString(2, psw);

            rs = cs.executeQuery();

            if (rs.next()) {
                String emailDB = rs.getString("email");
                String pswDB = rs.getString("password");
                String roleDB = rs.getString("role");

                if (roleDB.equals("customer")) user = new User(emailDB, pswDB, UserRole.CUSTOMER);
                else if (roleDB.equals("company")) user = new User(emailDB, pswDB, UserRole.COMPANY);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new DAOException("Error closing instances");
        }

        return user;
    }
}
