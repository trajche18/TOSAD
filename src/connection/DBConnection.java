package connection;

import java.sql.Connection;


public interface DBConnection {
	Connection getTargetDatabaseConnection(String hostname,
			String port, String servicename, String username, String password);
}
