package org.kpi.campus.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlExample {
	public static void main(String args[]) throws SQLException {
		SqlIntegrator sql = new MySqlIntegrator("localhost:3306/", "bank", 
				"user", "1111");
		sql.openConnection();
		ResultSet rs = sql.executeStatement("SELECT * FROM person");
		while(rs.next())
        {
        	System.out.println(rs.getRow() + ". " + rs.getString("fname") + "\t" +
                               rs.getString("lname") + "\t" + rs.getString("birth_date"));
        }
		sql.closeConnection();
	}
}
