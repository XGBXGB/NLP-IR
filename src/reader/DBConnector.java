package reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	private String driverName;
	private String url;
	private String database;
	private String username;
	private String passwordDB;

	private Connection con;
	private static DBConnector dbCon = null;

	public DBConnector() {
		driverName = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://127.0.0.1:3306/";
		database = "informationretrieval";
		username = "root";
		passwordDB = "password";
	}

	public static DBConnector getInstance() {
		if (dbCon == null) {
			dbCon = new DBConnector();
		}

		return dbCon;
	}

	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(getUrl() + getDatabase(), getUsername(), getPasswordDB());

			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordDB() {
		return passwordDB;
	}

	public void setPasswordDB(String passwordDB) {
		this.passwordDB = passwordDB;
	}

	public void close() {

		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
