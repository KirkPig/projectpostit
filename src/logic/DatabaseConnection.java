package logic;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public static Connection getConnection() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://103.253.72.156:3306/yonotool_app?useUnicode=true&characterEncoding=utf-8", "yonotool",
				"2aF::on0T8E6oY");

	}

}
