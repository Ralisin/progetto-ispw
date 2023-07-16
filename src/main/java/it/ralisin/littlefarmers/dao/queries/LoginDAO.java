package it.ralisin.littlefarmers.dao.queries;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class LoginDAO {
    public static User getUser(String email, String psw) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs;
        ResultSet rs;

        User user = null;

        try (InputStream input = new FileInputStream("src/main/resources/it/ralisin/littlefarmers/conf/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            cs = conn.prepareCall("select * from users where email = ? and password = ?");
            cs.setString(1, email);
            cs.setString(2, psw);

            rs = cs.executeQuery();

            if (rs.next()) {
                String emailDB = rs.getString("email");
                String pswDB = rs.getString("password");
                String roleDB = rs.getString("role");

                if (roleDB.equals("customer")) {
                    String userCustomer = properties.getProperty("USER_CUSTOMER");
                    String pswCustomer = properties.getProperty("PASSWORD_CUSTOMER");

                    ConnectionFactory.changeConnection(userCustomer, pswCustomer);

                    user = new User(emailDB, pswDB, UserRole.CUSTOMER);
                } else if (roleDB.equals("company")) {
                    String userCompany = properties.getProperty("USER_COMPANY");
                    String pswCompany = properties.getProperty("PASSWORD_COMPANY");

                    ConnectionFactory.changeConnection(userCompany, pswCompany);

                    user = new User(emailDB, pswDB, UserRole.COMPANY);
                }
            }
        } catch (SQLException | IOException e) {
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

    public static void logOut() {
        try (InputStream input = new FileInputStream("src/main/resources/it/ralisin/littlefarmers/conf/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String userLogin = properties.getProperty("USER_LOGIN");
            String pswLogin = properties.getProperty("PASSWORD_LOGIN");
            ConnectionFactory.changeConnection(userLogin, pswLogin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
