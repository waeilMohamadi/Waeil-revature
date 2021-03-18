package dao;

import db.DBConnection;
import dto.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private static final Logger LOG = LogManager.getLogger(EmployeeDao.class);

    public List<User> getAllUsers() throws BankException {
        List<User> lstUsers = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role=?;");
            statement.setString(1, "CUSTOMER");

            ResultSet result = statement.executeQuery();
            while (result.next()){
                User user = new User();
                user.setId(result.getInt("id"));
                user.setEmail(result.getString("email"));
                user.setStatus(result.getString("status"));
                user.setRole(result.getString("role"));
                lstUsers.add(user);
            }
            return lstUsers;

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }

    }

    public boolean updateCustomerStatus(long id, String status) throws BankException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users set status=? where id=?");
            statement.setString(1, status);
            statement.setLong(2, id);

            int result = statement.executeUpdate();
            if (result == 1){
                return true;
            } else {
                throw new BankException("Customer account cannot be updated.");
            }

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }

    }

    public User getUser(String email) throws BankException {
        User user = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=?;");
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();
            if (result.next()){
                user = new User();
                user.setId(result.getInt("id"));
                user.setEmail(result.getString("email"));
                user.setStatus(result.getString("status"));
                user.setRole(result.getString("role"));
            }
            return user;

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }
    }
}
