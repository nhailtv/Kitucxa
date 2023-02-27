package DAO;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Connect.ConnectJDBC;
import model.SinhVien;
public class SinhVienDAO implements DAOinterface<SinhVien>{
	private static SinhVienDAO instance;
    SinhVien sv = new SinhVien();

    public SinhVienDAO() {
    }

    public static SinhVienDAO getInstance() {
        if (instance == null) {
            instance = new SinhVienDAO();
        }
        return instance;
    }

    public static void setInstance(SinhVienDAO instance) {
        SinhVienDAO.instance = instance;
    }

	@Override
	public int Add(SinhVien t)  {
		try {
			Connection conn = ConnectJDBC.getConnection();
			
			PreparedStatement prst = conn.prepareStatement("insert into sinhvien (sinhVien_ID,sinhVien_Name,sinhVien_Mail,sinhVien_Sdt,sinhVien_Username,sinhVien_Password)"
					+"values (?,?,?,?,?,?);");
			prst.setString(1, t.getSinhVien_ID());
			prst.setString(2, t.getSinhVien_Name());
			prst.setString(3, t.getSinhVien_Mail());
			prst.setString(4, t.getSinhVien_Sdt());
			prst.setString(5, t.getSinhVien_Username());
			prst.setString(6, t.getSinhVien_Password());
			
			int rs = prst.executeUpdate();
			if(rs>0) {
				JOptionPane.showMessageDialog(new JFrame(), "Register Succeed" , null , JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(new JFrame(), "Something was wrong , Please try again!!!", null, JOptionPane.ERROR_MESSAGE);
			}
			
			
			ConnectJDBC.closeConnection(conn, prst, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int Update(SinhVien t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Delete(SinhVien t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<SinhVien> SelectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SinhVien SelectByID(SinhVien t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SinhVien> SelectByCondition(String Condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int Getdate (String sinhVien_Username) {
		int ngay = 0 ;
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
