package Contents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Connect.ConnectJDBC;

public class GetDate {
		
	public static int GetDate(String Username) {
		int ngay = 0;
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("Select Ngaythue from sinhvien where sinhVien_username = ?");
			prst.setString(1, "test_01");
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				String ngaythue = rs.getDate(1).toString();	
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
				LocalDateTime now = LocalDateTime.now();  
				dtf.format(now);
				prst = conn.prepareStatement("SELECT DATEDIFF(?,?);");
				prst.setString(1, dtf.format(now));
				prst.setString(2, ngaythue );
				ResultSet rs1 = prst.executeQuery();
				while (rs1.next()) {
					ngay = rs.getInt(1);
				}

			}

			
			ConnectJDBC.closeConnection(conn, prst, rs);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		return ngay;
	}
}
