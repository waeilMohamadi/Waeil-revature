package db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static db.Configuration.*;

public class DBConnection {

    private static final Logger LOG = LogManager.getLogger(DBConnection.class);

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://"+HOST+":"+PORT+"/"+DB_NAME,
                    		 db_username, db_password);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return connection;
    }

    private static void createDataBase() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://"+HOST+":"+PORT+"/",
                    		 db_username, db_password);

            Statement statement = connection.createStatement();
            statement.execute("SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
                    "    FROM pg_stat_activity\n" +
                    "    WHERE pg_stat_activity.datname = '"+DB_NAME+"'\n" +
                    "      AND pid <> pg_backend_pid();");
            statement.executeUpdate("DROP DATABASE IF EXISTS " + DB_NAME);
            statement.executeUpdate("CREATE DATABASE " + DB_NAME);
            connection.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private static void createTables() {
        Connection connection = null;
        try {

            String roleType = "CREATE TABLE roles (\n" +
                    "    role TEXT PRIMARY KEY\n" +
                    ");";

            String roleValues = "INSERT INTO roles (role) VALUES \n" +
                    "    ('ADMIN'),\n" +
                    "    ('EMPLOYEE'),\n" +
                    "    ('CUSTOMER');";

            String createUserQuery = "CREATE TABLE users (\n" +
                    "  id SERIAL PRIMARY KEY,\n" +
                    "  email TEXT NOT NULL UNIQUE,\n" +
                    "  password TEXT NOT NULL,\n" +
                    "  status TEXT NOT NULL,\n" +
                    "  role TEXT REFERENCES roles (role) ON UPDATE CASCADE NOT NULL\n" +
                    ");";

            String employeeUser = "INSERT INTO users (email, password, status, role) VALUES ('"+EMPLOYEE_EMAIL+"','"+EMPLOYEE_PASS+"','APPROVED','EMPLOYEE')";

            String accountTable = "CREATE TABLE account (\n" +
                    "  account_no SERIAL PRIMARY KEY,\n" +
                    "  customer_id int NOT NULL,\n" +
                    "  balance FLOAT NOT NULL,\n" +
                    "  CONSTRAINT fk_user_id FOREIGN KEY (customer_id) REFERENCES users (id)\n" +
                    ");";

            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://"+HOST+":"+PORT+"/" + DB_NAME,
                            db_username, db_password);
            Statement statement = connection.createStatement();
            statement.executeUpdate(roleType);
            statement.execute(roleValues);
            statement.executeUpdate(createUserQuery);
            statement.execute(employeeUser);
            statement.executeUpdate(accountTable);
            connection.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static void setUpDB() {
        //createDataBase();
        //createTables();
    }
}
