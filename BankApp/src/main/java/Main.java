import dao.BankException;
import db.DBConnection;
import dto.Account;
import dto.User;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.BankService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    private static BankService bankService = BankService.getInstance();

    public static void main(String[] args) {
        BasicConfigurator.configure();

        LOG.info("Starting Bank Application.");
       // DBConnection.setUpDB();

        Scanner scanner = new Scanner(System.in);
        showMainMenu(scanner);
    }

    private static void showMainMenu(Scanner scanner) {
        System.out.println();
        System.out.println("*****Welcome to Bank Application*****");
        System.out.println("1. User Login");
        System.out.println("2. Register Customer");
        System.out.println("3. Exit");

        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":

                System.out.print("Enter email id: ");
                String userName = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
                    User result = bankService.login(userName, password);
                    if (result != null) {
                        LOG.info("Logged in user: "+ userName);
                        showUserMenu(result, scanner);
                    } else {
                        showMainMenu(scanner);
                    }
                } catch (BankException e) {
                    LOG.error(e.getMessage());
                    System.out.println(e.getMessage());
                    showMainMenu(scanner);
                }

                break;

            case "2":

                System.out.print("Enter email id: ");
                String email = scanner.nextLine();

                System.out.print("Enter password: ");
                String pass = scanner.nextLine();

                try {
                    User register = bankService.register(email, pass);
                    if (register != null) {
                        LOG.info("New account created by: " + email);
                        System.out.println("Account created successfully...");
                    } else {
                        System.out.println("Account cannot be created!");
                    }
                    showMainMenu(scanner);
                } catch (BankException e) {
                    System.out.println(e.getMessage());
                    LOG.error(e.getMessage());
                    showMainMenu(scanner);
                }

                break;
            case "3":
            	System.out.println("Thank you for your Service!");
                break;
            default:
                showMainMenu(scanner);
        }
    }

    private static void showUserMenu(User user, Scanner scanner) {
        if (user != null) {
            if ("CUSTOMER".equals(user.getRole())) {
                System.out.println();
                System.out.println("****** Customer Menu ******");
                System.out.println("1. Apply new bank account.");
                System.out.println("2. View balance of an account.");
                System.out.println("3. Withdraw from an account.");
                System.out.println("4. Deposit to an account.");
                System.out.println("5. Money transfer to another account");
                System.out.println("6. View all bank account of this customer");
                System.out.println("7. Exit");

                System.out.println();
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                        while (true) {
                            System.out.print("Enter initial balance: ");
                          
                            String balanceStr = scanner.nextLine();
                            System.out.print("Wrong format must not be letters! ");
                            
                            try {
                                float balance = Float.parseFloat(balanceStr);
                                if (balance >0) {
                                    try {
                                        long res = bankService.addBankAccount(user, balance);
                                        if (res > 0) {
                                            LOG.info("New back account created by: " + user.getEmail());
                                            System.out.println("Bank account created successfully");
                                        } else {
                                            System.out.println("Bank account cannot be created");
                                        }
                                    } catch (BankException e) {
                                        LOG.error(e.getMessage());
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                } else {
                                    System.out.println("Invalid balance.");
                                }
                            } catch (NumberFormatException ex) {

                            }
                        }
                        showUserMenu(user, scanner);
                        break;

                    case "2":
                        while (true) {
                            System.out.print("Enter account number: ");
                            String id = scanner.nextLine();
                            try {
                                long cId = Long.parseLong(id);
                                try {
                                    Account account = bankService.getAccountBalance(cId);
                                    if (account != null) {
                                        System.out.println(account);
                                    } else {
                                        System.out.println("Account not found!");
                                    }
                                } catch (BankException e) {
                                    LOG.error(e.getMessage());
                                    System.out.println(e.getMessage());
                                }
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }
                        showUserMenu(user, scanner);

                        break;

                    case "3":
                        long cId = 0;
                        while (true) {
                            System.out.print("Enter account number: ");
                            String id = scanner.nextLine();
                            try {
                               cId  = Long.parseLong(id);
                               break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }

                        float amount = 0.0f;
                        while (true) {
                            System.out.print("Enter amount to withdraw: ");
                            String a = scanner.nextLine();
                            try {
                                amount  = Long.parseLong(a);
                                if (amount > 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid amount: must be > 0");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid amount");
                            }
                        }

                        try {
                            Account account = bankService.getAccountBalance(cId);
                            if (account != null) {
                                if (account.getBalance() >= amount) {
                                    boolean res = bankService.updateAccount(cId, account.getBalance()-amount);
                                    if (res) {
                                        LOG.info("" + user.getEmail() + " withdrawn " + amount + " amount" );
                                        System.out.println("Amount has been withdrawn");
                                    } else {
                                        LOG.info("" + user.getEmail() + " could not withdraw " + amount + " amount");
                                        System.out.println("Amount could not be withdrawn");
                                    }
                                } else {
                                    System.out.println("Account has insufficient balance.");
                                }
                            } else {
                                System.out.println("Account not found!");
                            }
                        } catch (BankException e) {
                            LOG.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }

                        showUserMenu(user, scanner);
                        break;

                    case "4":
                        cId = 0;
                        while (true) {
                            System.out.print("Enter account number: ");
                            String id = scanner.nextLine();
                            try {
                                cId  = Long.parseLong(id);
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }

                        amount = 0.0f;
                        while (true) {
                            System.out.print("Enter amount to deposit: ");
                            String a = scanner.nextLine();
                            try {
                                amount  = Long.parseLong(a);
                                if (amount > 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid amount: must be > 0");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid amount");
                            }
                        }

                        try {
                            Account account = bankService.getAccountBalance(cId);
                            if (account != null) {
                                boolean res = bankService.updateAccount(cId, account.getBalance()+amount);
                                if (res) {
                                    LOG.info("Amount: " + amount +" has been deposited by: " + user.getEmail());
                                    System.out.println("Amount has been deposited");
                                } else {
                                    LOG.info("Amount: " + amount +" could not be deposited by: " + user.getEmail());
                                    System.out.println("Amount could not be deposited");
                                }

                            } else {
                                System.out.println("Account not found!");
                            }
                        } catch (BankException e) {
                            LOG.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }

                        showUserMenu(user, scanner);
                        break;

                    case "5":
                        cId = 0;
                        while (true) {
                            System.out.print("Enter account number from which money to be withdrawn: ");
                            String id = scanner.nextLine();
                            try {
                                cId  = Long.parseLong(id);
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }

                        long otherId = 0;
                        while (true) {
                            System.out.print("Enter account number of other's accont: ");
                            String id = scanner.nextLine();
                            try {
                                otherId  = Long.parseLong(id);
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }

                        amount = 0.0f;
                        while (true) {
                            System.out.print("Enter amount to deposit: ");
                            String a = scanner.nextLine();
                            try {
                                amount  = Long.parseLong(a);
                                if (amount > 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid amount: must be > 0");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid amount");
                            }
                        }

                        try {
                            Account account = bankService.getAccountBalance(cId);
                            if (account != null) {
                                if (account.getBalance() >= amount) {
                                    Account otherAccount = bankService.getAccountBalance(cId);
                                    if (otherAccount != null) {
                                        boolean res = bankService.updateAccount(cId, account.getBalance() - amount);
                                        if (res) {
                                            res = bankService.updateAccount(otherId, otherAccount.getBalance() + amount);

                                            if (res) {
                                                LOG.info("Amount: " + amount +" has been deposited to account number: " +otherAccount.getAccountNumber() + " by: " + user.getEmail());
                                                System.out.println("Amount has been deposited");
                                            } else {
                                                LOG.info("Amount: " + amount +" has been reversed to customer account: " +user.getEmail());
                                                bankService.updateAccount(cId, account.getBalance());
                                            }
                                        } else {
                                            bankService.updateAccount(cId, account.getBalance());
                                            LOG.info("Amount: " + amount +" has been reversed to customer account: " +user.getEmail());
                                            System.out.println("Amount could not be transferred");
                                        }
                                    }
                                } else {
                                    System.out.println("Account has insufficient balance.");
                                }
                            } else {
                                System.out.println("Account not found!");
                            }
                        } catch (BankException e) {
                            System.out.println(e.getMessage());
                        }

                        showUserMenu(user, scanner);
                        break;

                    case "6":
                        try {
                            List<Account> accounts = bankService.getAccounts(user.getId());
                            for (Account u: accounts) {
                                System.out.println(u);
                            }
                        } catch (BankException e) {
                            System.out.println(e.getMessage());
                        }
                        showUserMenu(user, scanner);
                        break;

                    case "7":
                    	System.out.println("Thanks have a good day!");
                        showMainMenu(scanner);
                        break;
                    default:
                        showUserMenu(user, scanner);
                }

            } else if ("EMPLOYEE".equals(user.getRole())) {
                System.out.println("****** Employee Menu ******");
                System.out.println("1. View all customers");
                System.out.println("2. Approve/Reject a customer account");
                System.out.println("3. View all bank account of a specific customer");
                System.out.println("4. View balance of an account.");
                System.out.println("5. View log of all transactions.");
                System.out.println("6. Exit");

                System.out.println();
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                        try {
                            List<User> allCustomers = bankService.getAllCustomers();
                            for (User u: allCustomers) {
                                System.out.println(u);
                            }
                        } catch (BankException e) {
                        }
                        showUserMenu(user, scanner);
                        break;
                    case "2":

                        while (true) {
                            System.out.print("Enter customer id: ");
                            String id = scanner.nextLine();
                            try {
                                long cId = Long.parseLong(id);
                                System.out.print("Decision (Approve/Reject): ");
                                String decision = scanner.nextLine();
                                boolean res = false;
                                try {
                                    if ("Approve".equalsIgnoreCase(decision)) {
                                        res = bankService.updateCustomerStatus(cId, "APPROVED");
                                    } else if ("Reject".equalsIgnoreCase(decision)) {
                                        res = bankService.updateCustomerStatus(cId, "REJECTED");
                                    } else {
                                        System.out.println("Invalid choice");
                                    }
                                    if (res) {
                                        LOG.info("Account status has been changed for: " + cId);
                                        System.out.println("Account updated successfully");
                                    }
                                } catch (BankException ex) {
                                    LOG.error(ex.getMessage());
                                    System.out.println(ex.getMessage());
                                }
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid customer Id.");
                            }
                        }
                        showUserMenu(user, scanner);
                        break;

                    case "3":

                        long cId = 0;
                        while (true) {
                            System.out.print("Enter customer id: ");
                            String id = scanner.nextLine();
                            try {
                                cId = Long.parseLong(id);
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid customer id.");
                            }
                        }

                        try {
                            List<Account> accounts = bankService.getAccounts(cId);
                            for (Account u: accounts) {
                                System.out.println(u);
                            }
                        } catch (BankException e) {
                            LOG.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }
                        showUserMenu(user, scanner);

                        break;

                    case "4":
                        while (true) {
                            System.out.print("Enter account number: ");
                            String id = scanner.nextLine();
                            try {
                                cId = Long.parseLong(id);
                                try {
                                    Account account = bankService.getAccountBalance(cId);
                                    if (account != null) {
                                        System.out.println(account);
                                    } else {
                                        System.out.println("Account not found!");
                                    }
                                } catch (BankException e) {
                                    LOG.error(e.getMessage());
                                    System.out.println(e.getMessage());
                                }
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid account number");
                            }
                        }
                        showUserMenu(user, scanner);
                        break;

                    case "5":

                        System.out.print("Enter log file path: ");
                        String logPath = scanner.nextLine();

                        try {

                            BufferedReader reader = new BufferedReader(new FileReader(logPath));
                            String line = null;
                            while ((line=reader.readLine()) != null) {
                                System.out.println(line);
                            }

                        } catch (IOException ex) {
                            LOG.error(ex.getMessage());
                            System.out.println(ex.getMessage());
                        }

                        showUserMenu(user, scanner);
                        break;

                    case "6":
                        showMainMenu(scanner);
                        break;
                    default:
                        showUserMenu(user, scanner);

                }
            }
        } else {
            showMainMenu(scanner);
        }
    }

}
