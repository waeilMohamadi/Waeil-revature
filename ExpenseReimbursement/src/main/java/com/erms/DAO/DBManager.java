package com.erms.DAO;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.serial.SerialException;

import com.erms.models.Receipt;
import com.erms.models.User;


public class DBManager {

	private final static String db_url = "jdbc:postgresql://localhost:5432/postgres";
	
	//private final static String db_url = "jdbc:postgresql:database-1.cqvgewvkvu8e.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=erms";
	// Change user name and password
	// private static String DBUSER = "postgres"; //"root";
	private static String db_username = "postgres";
	private static String db_password = "postgres";
	// private static String DBPASSWORD = "postgres"; // null;

	private static String ADMIN_EMAIL = "admin@admin.com";
	private static String ADMIN_PASSWORD = "admin123";

	//private boolean resetDB = false;
	//private static String db_urlL = "";

	private static Connection con;

	private static DBManager instance;

	static {
		try {
			// org.postgresql.Driver
			// com.mysql.cj.jdbc.Driver
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getConenction() throws SQLException {

		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(db_url, db_username, db_password);
		
		}
		return con;
	}

	public static void closeConenction() throws SQLException {
		if (con != null) {
			con.close();
		}
	}

	private DBManager() {
		try {

			Properties prop = new Properties();
			String propFileName = "config.properties";
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String dbHost = prop.getProperty("db.host");
			String dbPort = prop.getProperty("db.port");
			String dbName = prop.getProperty("db.name");

			// DBURL = "jdbc:postgresql://" + dbHost +":" + dbPort + "/" + dbName;

			db_username = prop.getProperty("db_username");
			db_password = prop.getProperty("db_password");
			ADMIN_EMAIL = prop.getProperty("admin.email");
			ADMIN_PASSWORD = prop.getProperty("admin.password");
			inputStream.close();

			boolean resetDB = checkDB();
			if (resetDB) {

				dropUserTable();
				createUserTable();
				dropReibursementTable();
				createReibursementTable();

				User user = new User();
				user.setId(0);
				user.setManagerId(0);
				user.setEmail(ADMIN_EMAIL);
				user.setFirstName("Admin");
				user.setLastName("User");
				user.setManagerName(user.getManagerName());
				user.setRole("MANAGER");
				user.setPassword(ADMIN_PASSWORD);
				registerUser(user, true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean checkDB() {
		boolean resetDB = true;
		try {
			Connection con = getConenction();
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet rs = metadata.getTables(null, null, "users", null);
			if (rs != null && rs.next()) {
				resetDB = false;
			}
		} catch (Exception ex) {

		}
		return resetDB;
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private void dropUserTable() throws Exception {
		try {
			Connection con = getConenction();
			String sql = "DROP TABLE IF EXISTS Users;";
			PreparedStatement ps = con.prepareStatement(sql);
			boolean res = ps.execute();
			closeConenction();
		} catch (Exception ex) {

		}
	}

	private void dropReibursementTable() throws Exception {
		try {
			Connection con = getConenction();
			String sql = "DROP TABLE IF EXISTS Reimbursements;";
			PreparedStatement ps = con.prepareStatement(sql);
			boolean res = ps.execute();
			closeConenction();
		} catch (Exception ex) {

		}
	}

	private boolean createReibursementTable() throws Exception {

		Connection con = getConenction();
		String sql = "CREATE TABLE IF NOT EXISTS Reimbursements (" + "	 id SERIAL,\n"
				+ "    employeeId int NOT NULL,\n" + "    managerId int NOT NULL,\n"
				+ "    employeeName varchar(255) NOT NULL,\n" + "    managerName varchar(255) NOT NULL,\n"
				+ "	 amount float,	\n" + "    status varchar(255) DEFAULT 'PENDING',\n"
				+ "    description varchar(255),\n" + "    title varchar(255),\n" + "	 receipt bytea,	\n"
				+ "    PRIMARY KEY (id)" + ");";
		PreparedStatement ps = con.prepareStatement(sql);
		boolean res = ps.execute();
		closeConenction();
		return res;
	}

	public int registerReceipt(Receipt receipt) throws SerialException, SQLException {
		int result = 0;
		Connection con = getConenction();
		String sql = "INSERT INTO Reimbursements (employeeId, managerId, " + "employeeName, managerName, "
				+ "amount, status, description, title, receipt) VALUES (?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;

		ps.setInt(index++, receipt.getEmployeeId());
		ps.setInt(index++, receipt.getManagerId());
		ps.setString(index++, receipt.getEmployeeName());
		ps.setString(index++, receipt.getManagerName());
		ps.setFloat(index++, receipt.getAmount());
		ps.setString(index++, receipt.getStatus());
		ps.setString(index++, receipt.getDescription());
		ps.setString(index++, receipt.getTitle());
		InputStream targetStream = new ByteArrayInputStream(receipt.getReceipt());
		ps.setBinaryStream(index++, targetStream);
		result = ps.executeUpdate();
		closeConenction();
		return result;
	}

	private boolean createUserTable() throws Exception {

		Connection con = getConenction();
		String sql = "CREATE TABLE IF NOT EXISTS Users (" + "	 id SERIAL,\n" + "    managerId int NOT NULL,\n"
				+ "    managerName varchar(255) NOT NULL,\n" + "    role varchar(255) DEFAULT 'EMPLOYEE',\n"
				+ "    fname varchar(255),\n" + "    lname varchar(255),\n"
				+ "    email varchar(255) NOT NULL UNIQUE,\n" + "    password varchar(255) NOT NULL,\n"
				+ "    PRIMARY KEY (id)" + ");";
		PreparedStatement ps = con.prepareStatement(sql);
		boolean res = ps.execute();
		closeConenction();
		return res;
	}

	public User isValidUser(String email, String password) throws Exception {
		boolean result = false;
		Connection con = getConenction();
		String sql = "SELECT * FROM Users where email=? and password=?";
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setString(index++, email);
		ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		User user = null;
		if (rs.next()) {
			result = true;
			user = new User();
			user.setId(rs.getInt("id"));
			user.setFirstName(rs.getString("fname"));
			user.setLastName(rs.getString("lname"));
			user.setManagerId(rs.getInt("managerId"));
			user.setRole(rs.getString("role"));
			user.setManagerName(rs.getString("managerName"));
			user.setEmail(rs.getString("email"));
		}
		closeConenction();
		return user;
	}

	public boolean isManager(User user) throws Exception {
		boolean result = false;
		Connection con = getConenction();
		String sql = "SELECT role FROM Users where email=?";
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setString(index++, user.getEmail());
		ResultSet rs = ps.executeQuery();
		String role = null;
		if (rs.next()) {
			role = rs.getString(1);
		}
		if ("MANAGER".equals(role)) {
			result = true;
		}
		closeConenction();
		return result;
	}

	public int registerUser(User user, boolean idProvided) throws Exception {
		int result = 0;
		Connection con = getConenction();
		String sql = "INSERT INTO Users (managerId, managerName, role, fname, lname, email, password) VALUES (?,?,?,?,?,?,?)";
		if (idProvided) {
			sql = "INSERT INTO Users (id, managerId, managerName, role, fname, lname, email, password) VALUES (?,?,?,?,?,?,?,?)";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		if (idProvided) {
			ps.setInt(index++, user.getId());
		}
		ps.setInt(index++, user.getManagerId());
		ps.setString(index++, user.getManagerName());
		ps.setString(index++, user.getRole());
		ps.setString(index++, user.getFirstName());
		ps.setString(index++, user.getLastName());
		ps.setString(index++, user.getEmail());
		ps.setString(index++, user.getPassword());
		result = ps.executeUpdate();
		closeConenction();
		return result;
	}

	public List<User> getAllEmployees(int id) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = getConenction();
		String sql = "SELECT * FROM Users where managerId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setInt(index++, id);
		// ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		List<User> usersList = new ArrayList<>();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setFirstName(rs.getString("fname"));
			user.setLastName(rs.getString("lname"));
			user.setManagerId(rs.getInt("managerId"));
			user.setRole(rs.getString("role"));
			user.setManagerName(rs.getString("managerName"));
			user.setEmail(rs.getString("email"));
			usersList.add(user);
		}
		closeConenction();
		return usersList;
	}

	public List<Receipt> getRequests(int id, String status, boolean include) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = getConenction();
		String sql = "";
		if (include) {
			sql = "SELECT * FROM Reimbursements where employeeId=? and status=?";
		} else {
			sql = "SELECT * FROM Reimbursements where employeeId=? and NOT status=?";
		}
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setInt(index++, id);
		ps.setString(index++, status);
		// ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		List<Receipt> receipts = new ArrayList<>();
		while (rs.next()) {
			Receipt receipt = new Receipt();
			receipt.setId(rs.getInt("id"));
			receipt.setManager(rs.getString("managerName"));
			receipt.setAmount(rs.getFloat("amount"));
			receipt.setDescription(rs.getString("description"));
			receipt.setEmployeeId(rs.getInt("employeeId"));
			receipt.setEmployeeName(rs.getString("employeeName"));
			receipt.setStatus(rs.getString("status"));
			receipt.setTitle(rs.getString("title"));
			receipt.setReceipt(rs.getBytes("receipt"));
			receipts.add(receipt);
		}
		closeConenction();
		return receipts;
	}

	public List<Receipt> getAllRequests(boolean all) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = getConenction();
		String sql = "";
		if (all) {
			sql = "SELECT * FROM Reimbursements";
		} else {
			sql = "SELECT * FROM Reimbursements where status='APPROVED' OR status='DENIED'";
		}
		PreparedStatement ps = con.prepareStatement(sql);
		// ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		List<Receipt> receipts = new ArrayList<>();
		while (rs.next()) {
			Receipt receipt = new Receipt();
			receipt.setId(rs.getInt("id"));
			receipt.setManager(rs.getString("managerName"));
			receipt.setAmount(rs.getFloat("amount"));
			receipt.setDescription(rs.getString("description"));
			receipt.setEmployeeId(rs.getInt("employeeId"));
			receipt.setEmployeeName(rs.getString("employeeName"));
			receipt.setStatus(rs.getString("status"));
			receipt.setTitle(rs.getString("title"));
			receipt.setReceipt(rs.getBytes("receipt"));
			receipts.add(receipt);
		}
		closeConenction();
		return receipts;
	}

	public List<Receipt> getEmployeeRequestsUnderMe(int id) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = getConenction();
		String sql = "SELECT * FROM Reimbursements where managerId=?";

		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setInt(index++, id);

		// ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		List<Receipt> receipts = new ArrayList<>();
		while (rs.next()) {
			Receipt receipt = new Receipt();
			receipt.setId(rs.getInt("id"));
			receipt.setManager(rs.getString("managerName"));
			receipt.setAmount(rs.getFloat("amount"));
			receipt.setDescription(rs.getString("description"));
			receipt.setEmployeeId(rs.getInt("employeeId"));
			receipt.setEmployeeName(rs.getString("employeeName"));
			receipt.setStatus(rs.getString("status"));
			receipt.setTitle(rs.getString("title"));
			receipt.setReceipt(rs.getBytes("receipt"));
			receipts.add(receipt);
		}
		closeConenction();
		return receipts;
	}

	public List<Receipt> getEmployeeRequests(int id, String status, boolean include) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = getConenction();
		String sql = "";
		if (include) {
			sql = "SELECT * FROM Reimbursements where managerId=? and status=?";
		} else {
			sql = "SELECT * FROM Reimbursements where managerId=? and NOT status=?";
		}
		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setInt(index++, id);
		ps.setString(index++, status);
		// ps.setString(index++, password);
		ResultSet rs = ps.executeQuery();
		List<Receipt> receipts = new ArrayList<>();
		while (rs.next()) {
			Receipt receipt = new Receipt();
			receipt.setId(rs.getInt("id"));
			receipt.setManager(rs.getString("managerName"));
			receipt.setAmount(rs.getFloat("amount"));
			receipt.setDescription(rs.getString("description"));
			receipt.setEmployeeId(rs.getInt("employeeId"));
			receipt.setEmployeeName(rs.getString("employeeName"));
			receipt.setStatus(rs.getString("status"));
			receipt.setTitle(rs.getString("title"));
			receipt.setReceipt(rs.getBytes("receipt"));
			receipts.add(receipt);
		}
		closeConenction();
		return receipts;
	}

	public int updateUser(User user) throws SQLException {
		int result = 0;
		Connection con = getConenction();
		String sql = "UPDATE Users SET fname = ?, lname = ?, password=? WHERE id=?;";

		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setString(index++, user.getFirstName());
		ps.setString(index++, user.getLastName());
		ps.setString(index++, user.getPassword());
		ps.setInt(index++, user.getId());
		result = ps.executeUpdate();
		closeConenction();
		return result;
	}

	public int updateEmployeeRequest(int id, int id2, String action) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		Connection con = getConenction();
		String sql = "UPDATE Reimbursements SET status = ? WHERE id=? AND managerId=?;";

		PreparedStatement ps = con.prepareStatement(sql);
		int index = 1;
		ps.setString(index++, action);
		ps.setInt(index++, id);
		ps.setInt(index++, id2);
		result = ps.executeUpdate();
		closeConenction();
		return result;
	}
	
}