package it.ralisin.littlefarmers.dao.procedures;

import it.ralisin.littlefarmers.dao.ConnectionFactory;
import it.ralisin.littlefarmers.model.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login {

    public static Role login(String email, String pass) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();

        String query = "select * from users";

        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                String emailDB = rs.getString("email");
                String passDB = rs.getString("password");
                String roleDB = rs.getString("role");

                if(email.equals(emailDB) && pass.equals(passDB))
                    return roleDB.equals("company") ? Role.COMPANY : Role.CUSTOMER;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Role.NONE;
    }
}
