package service;

import dao.BankException;
import dao.CustomerDao;
import dao.EmployeeDao;
import dao.LoginDao;
import dto.Account;
import dto.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class BankService {

    private LoginDao loginDao;

    private CustomerDao customerDao;

    private EmployeeDao employeeDao;

    private static BankService instance;

    private static final Logger LOG = LogManager.getLogger(BankService.class);

    private BankService() {
        loginDao = new LoginDao();
        customerDao = new CustomerDao();
        employeeDao = new EmployeeDao();
    }

    public User login(String email, String password) throws BankException {
       return loginDao.login(email, password);
    }

    public User register(String email, String password) throws BankException {
        return customerDao.register(email, password);
    }

    public List<User> getAllCustomers() throws BankException {
        return employeeDao.getAllUsers();
    }

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }

    public long addBankAccount(User user, float balance) throws BankException {
        return customerDao.addBankAccount(user, balance);
    }

    public boolean updateCustomerStatus(long id, String approved) throws BankException {
        return employeeDao.updateCustomerStatus(id, approved);
    }

    public List<Account> getAccounts(long id) throws BankException  {
        return customerDao.getAccounts(id);
    }

    public Account getAccountBalance(long cId) throws BankException {
        return customerDao.getAccountBalance(cId);
    }

    public boolean updateAccount(long user, float amount) throws BankException {
        return customerDao.updateAccount(user, amount);
    }

    public User getUser(String email) throws BankException {
        return employeeDao.getUser(email);
    }
}
