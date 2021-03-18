import dao.BankException;
import db.Configuration;
import db.DBConnection;
import dto.User;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Test;
import service.BankService;

import java.sql.Connection;

public class BankTests extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testConnection() {
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void  testEmployeeLogin() {
        BankService bankService = BankService.getInstance();
        try {
           // User login = bankService.login(Configuration.EMPLOYEE_EMAIL, Configuration.EMPLOYEE_PASS);
          User login = bankService.login("employee@mybank.com","admin");
           
            assertNotNull(login);
        } catch (BankException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testGetOrCreateCustomerWithoutApproved() {
        BankService bankService = BankService.getInstance();
//        String email = "xyz@xyz.com";
//        String pass = "xyz";
        String email = "Babo12@yahoo.com";
        String pass = "Babo10";
        
        try {
            User login = bankService.login(email, pass);
            assertEquals(email, login.getEmail());
        } catch (Exception e) {
            assertTrue(e instanceof BankException);
        }
    }

    @Test
    public void testGetOrCreateCustomerWithApproved() {
        BankService bankService = BankService.getInstance();
        String email = "waeil_mohamadi@yahoo.com";
        String pass = "waeil2424";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            } else {
                bankService.updateCustomerStatus(user.getId(), "APPROVED");
                User login = bankService.login(email, pass);
                assertEquals(login.getId(), user.getId());
            }
            //assertEquals(email, login.getEmail());
        } catch (BankException e) {

            assertTrue(false);
        }
    }

    @Test
    public void testGetOrCreateCustomerWithRejected() {
        BankService bankService = BankService.getInstance();
        String email = "xyz@xyz.com";
        String pass = "xyz";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            } else {
                bankService.updateCustomerStatus(user.getId(), "REJECTED");
                User updated = bankService.getUser(email);
                assertEquals("REJECTED", updated.getStatus());
            }
            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testBankAccountValidBalance() {
        BankService bankService = BankService.getInstance();
        String email = "Babo12@yahoo.com";
        String pass = "Babo10";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            }
            bankService.updateCustomerStatus(user.getId(), "APPROVED");
            User updated = bankService.getUser(email);
            assertEquals("APPROVED", updated.getStatus());

            long b = bankService.addBankAccount(updated, 1000);
            assertTrue(b > 0);

            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testBankAccountInValidBalance() {
        BankService bankService = BankService.getInstance();
        String email = " xyz@xyz.com";
        String pass = "xyz";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            }
            bankService.updateCustomerStatus(user.getId(), "APPROVED");
            User updated = bankService.getUser(email);
            assertEquals("APPROVED", updated.getStatus());

            long id = bankService.addBankAccount(updated, -1000);
            assertTrue(id < 0);

            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testWithdrawal() {
        BankService bankService = BankService.getInstance();
        String email = "xyz@xyz.com";
        String pass = "xyz";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            }
            bankService.updateCustomerStatus(user.getId(), "APPROVED");
            User updated = bankService.getUser(email);
            assertEquals("APPROVED", updated.getStatus());

            long id = bankService.addBankAccount(updated, 1000);
            assertTrue(id > 0);
            boolean b = bankService.updateAccount(id, 500);
            assertTrue(b);
            assertEquals(500.0f, bankService.getAccountBalance(id).getBalance());

            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testInvalidWithdrawal() {
        BankService bankService = BankService.getInstance();
        String email = "xyz@xyz.com";
        String pass = "xyz";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            }
            bankService.updateCustomerStatus(user.getId(), "APPROVED");
            User updated = bankService.getUser(email);
            assertEquals("APPROVED", updated.getStatus());

            long id = bankService.addBankAccount(updated, 1000);
            assertTrue(id > 0);
            boolean b = bankService.updateAccount(id, -500);
            assertFalse(b);
            assertEquals(1000.0f, bankService.getAccountBalance(id).getBalance());

            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDeposit() {
        BankService bankService = BankService.getInstance();
        String email = "Babo12@yahoo.com";
        String pass = "Babo10";
        try {
            User user = bankService.getUser(email);
            if (user == null) {
                User register = bankService.register(email, pass);
                assertEquals("PENDING", register.getStatus());
            }
            bankService.updateCustomerStatus(user.getId(), "APPROVED");
            User updated = bankService.getUser(email);
            assertEquals("APPROVED", updated.getStatus());

            long id = bankService.addBankAccount(updated, 1000);
            assertTrue(id > 0);
            boolean b = bankService.updateAccount(id, 1500);
            assertTrue(b);
            assertEquals(1500.0f, bankService.getAccountBalance(id).getBalance());

            //assertEquals(email, login.getEmail());
        } catch (BankException e) {
            assertTrue(false);
        }
    }
}
