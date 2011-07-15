package org.kpi.campus.convert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlIntegrator implements SqlIntegrator{
	private String hostName;
	private String dbName;
	private String userName;
	private String password;
	private Connection con;
	
	public MySqlIntegrator(String hostName, String dbName,
			String userName, String password) {
		this.hostName = hostName;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("MySql jdbc driver not found. You should install it first.");
		}
	}
	@Override
	public ResultSet executeStatement(String stmt) throws SQLException {
		Statement statement = con.createStatement();
		return statement.executeQuery(stmt);
	}

	@Override
	public int executeUpdate(String stmt) throws SQLException {
		Statement statement = con.createStatement();
		return statement.executeUpdate(stmt);
	}

	@Override
	public boolean openConnection() {
		try {
			con = DriverManager.getConnection(
			        "jdbc:mysql://"+hostName+dbName,
			        userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (con == null) return false;
		return true;
	}

	@Override
	public boolean closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
