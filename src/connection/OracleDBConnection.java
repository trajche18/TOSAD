package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Column;
import database.OracleScheme;
import database.Scheme;
import database.Table;

import rule.AttributeCompareRule;
import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.BusinessRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;
import trigger.TriggerTiming;
import trigger.TriggerTypes;

public class OracleDBConnection implements DBConnection {
	Statement st;
	ResultSet rs;

	public OracleDBConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("Oracle JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}

	}

	public Connection getTargetDatabaseConnection(String hostname, String port,
			String servicename, String username, String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"
					+ hostname + ":" + port + ":" + servicename, username,
					password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
