package dao;

import db.DBConnection;
import dto.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    private static final Logger LOG = LogManager.getLogger(LoginDao.class);

    public User login(String email, String password) throws BankException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=? AND password=?;");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = new User();
                user.setEmail(email);
                user.setId(result.getInt("id"));
                user.setStatus(result.getString("status"));
                user.setRole(result.getString("role"));
                if ("APPROVED".equals(user.getStatus())) {
                    return user;
                } else if ("PENDING".equals(user.getStatus())) {
                    throw new BankException("Your account hasn't been approved yet.");
                } else if ("REJECTED".equals(user.getStatus())) {
                    throw new BankException("Your account application has been reject.");
                } else {
                    throw new BankException("No account registered with email id: " + email);
                }
            } else {
                throw new BankException("No account registered with email id: " + email);
            }

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }
    }
}
