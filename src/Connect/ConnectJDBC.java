package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectJDBC {
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url ="jdbc:mysql://localhost:3306/KTX";
			String username = "root";
			String password = "13579-97531";
			conn = DriverManager.getConnection(url,username,password);
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return conn;
		
	}
	public static void closeConnection(Connection conn, PreparedStatement prst, ResultSet rss) {
		//close connection
		try {
			if(conn != null ) {
				conn.close();
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
		//close PrepareStament
		try {
			if(prst != null) {
				prst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//close ResoultSet;
		try {
			if(rss != null) {
				rss.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
