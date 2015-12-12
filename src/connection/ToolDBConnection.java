package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ToolDBConnection {
	public ToolDBConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Oracle JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}

	}

	public Connection getToolDatabaseConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@ondora01.hu.nl:8521:cursus01",
					"THO7_2012_2B_TEAM3", "THO7_2012_2B_TEAM3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
