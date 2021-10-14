package logic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public static Connection getConnection() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("<database host>", "<database username>",
				"<database password>");

	}

}
