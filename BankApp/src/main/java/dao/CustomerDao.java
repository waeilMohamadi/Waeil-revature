package dao;

import db.DBConnection;
import dto.Account;
import dto.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private static final Logger LOG = LogManager.getLogger(CustomerDao.class);

    public User register(String email, String password) throws BankException {

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password, status, role) VALUES (?,?,?,?);");
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, "PENDING");
            statement.setString(4, "CUSTOMER");

            int result = statement.executeUpdate();
            if (result == 1){
                User user = new User();
                user.setEmail(email);
                user.setRole("CUSTOMER");
                return user;
            } else {
                throw new BankException("Customer account cannot be created.");
            }

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }

    }


    public long addBankAccount(User user, float balance) throws BankException {
        try {
            if (balance < 0) {
                return -1;
            }

            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO account (customer_id, balance) VALUES (?,?) RETURNING account_no;");
            statement.setLong(1, user.getId());
            statement.setFloat(2, balance);

            ResultSet result = statement.executeQuery();
            if (result.next()){
                return result.getLong(1);
            } else {
                throw new BankException("Customer account cannot be created.");
            }

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }
    }

    public List<Account> getAccounts(long id) throws BankException {
        List<Account> lstAccounts = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE customer_id=?;");
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            while (result.next()){
                Account account = new Account();
                account.setUserId(result.getInt("customer_id"));
                account.setAccountNumber(result.getInt("account_no"));
                account.setBalance(result.getFloat("balance"));
                lstAccounts.add(account);
            }
            return lstAccounts;

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }
    }

    public Account getAccountBalance( long cId) throws BankException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE account_no=?;");
            statement.setLong(1, cId);

            ResultSet result = statement.executeQuery();
            if (result.next()){
                Account account = new Account();
                account.setUserId(result.getInt("customer_id"));
                account.setAccountNumber(result.getInt("account_no"));
                account.setBalance(result.getFloat("balance"));
                return account;
            } else {
                throw new BankException("Account not found");
            }

        } catch (SQLException ex) {
            throw new BankException(ex.getMessage());
        }
    }

    public boolean updateAccount(long accountNum, float amount) throws BankException {
        try {
            if (amount < 0) {
                return false;
            }
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE account SET balance=? WHERE account_no=?;");
            statement.setFloat(1, amount);
            statement.setLong(2, accountNum);

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
}
