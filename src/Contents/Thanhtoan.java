package Contents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.ConnectJDBC;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class Thanhtoan extends JFrame {

	private JPanel contentPane;
	private JTextField txtTen;
	private JTextField txtMasinhvien;
	private JTextField txtNgaycu;
	private JTextField txtXacnhan;
	private JTextField txtSongay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thanhtoan frame = new Thanhtoan("123");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Thanhtoan(String ID) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 803, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 17, 58, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblMSinhVin = new JLabel("Mã sinh viên");
		lblMSinhVin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMSinhVin.setBounds(24, 65, 158, 31);
		contentPane.add(lblMSinhVin);
		
		JLabel lblNgyngK = new JLabel("Ngày cũ");
		lblNgyngK.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNgyngK.setBounds(24, 111, 126, 31);
		contentPane.add(lblNgyngK);
		
		
		
		
		
		
		JLabel lblNgyMi = new JLabel("Ngày sau thanh toán");
		lblNgyMi.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNgyMi.setBounds(25, 242, 201, 31);
		contentPane.add(lblNgyMi);
		
		txtTen = new JTextField();
		txtTen.setEditable(false);
		txtTen.setBounds(338, 13, 181, 39);
		contentPane.add(txtTen);
		txtTen.setColumns(10);
		
		txtMasinhvien = new JTextField();
		txtMasinhvien.setEditable(false);
		txtMasinhvien.setColumns(10);
		txtMasinhvien.setBounds(338, 62, 181, 39);
		contentPane.add(txtMasinhvien);
		
		txtNgaycu = new JTextField();
		txtNgaycu.setEditable(false);
		txtNgaycu.setColumns(10);
		txtNgaycu.setBounds(336, 111, 183, 39);
		contentPane.add(txtNgaycu);
		JLabel lblSNgyT = new JLabel("Số ngày từ lần cuối thanh toán");
		lblSNgyT.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSNgyT.setBounds(24, 161, 306, 31);
		contentPane.add(lblSNgyT);
		
		txtSongay = new JTextField();
		txtSongay.setEditable(false);
		txtSongay.setBounds(337, 160, 182, 39);
		contentPane.add(txtSongay);
		txtSongay.setColumns(10);
		
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select sinhVien_name , sinhVien_ID , Ngaythue from sinhvien where sinhVien_ID = ? ");
			prst.setString(1,ID);
			ResultSet rs= prst.executeQuery();
			while(rs.next()) {
				txtTen.setText(rs.getString(1));
				txtMasinhvien.setText(ID);
				txtNgaycu.setText(rs.getDate(3).toString());
			}
			prst = conn.prepareStatement("select Ngaythue from sinhvien where sinhvien_ID = ?");
			prst.setString(1, ID);
			rs= prst.executeQuery();
			while (rs.next()) {
				String ngaythue = rs.getDate(1).toString();	
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
				LocalDateTime now = LocalDateTime.now();
				dtf.format(now);
				PreparedStatement prst1 = conn.prepareStatement("SELECT DATEDIFF(?,?);");
				prst1.setString(1, dtf.format(now));
				prst1.setString(2, ngaythue );
				ResultSet rs1 = prst1.executeQuery();
					while (rs1.next()) {
						txtSongay.setText(rs1.getString(1));
				}		rs1.close();
						prst1.close();
					
				}
		
			ConnectJDBC.closeConnection(conn, prst, rs);
			
			
			
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		txtXacnhan = new JTextField();
		txtXacnhan.setColumns(10);
		txtXacnhan.setBounds(338, 236, 181, 39);
		contentPane.add(txtXacnhan);
		
		
		
		JButton btnNewButton = new JButton("Xác nhận thanh toán");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newdate = txtXacnhan.getText().toString();
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("UPDATE sinhvien SET Ngaythue = ? WHERE (sinhVien_ID = ?);");
					prst.setString(1, newdate);
					prst.setString(2, ID);
					int rs = prst.executeUpdate();
					if(rs != 0 ) {
						JOptionPane.showMessageDialog(btnNewButton, "thanh toán thành công !!" , "Thông báo!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(567, 238, 157, 39);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Vui lòng nhập theo định dạng yyyy-MM-dd");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(277, 305, 312, 35);
		contentPane.add(lblNewLabel_1);
		
		JButton btnLammoi = new JButton("Làm mới");
		btnLammoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select sinhVien_name , sinhVien_ID , Ngaythue from sinhvien where sinhVien_ID = ? ");
					prst.setString(1,ID);
					ResultSet rs= prst.executeQuery();
					while(rs.next()) {
						txtTen.setText(rs.getString(1));
						txtMasinhvien.setText(ID);
						txtNgaycu.setText(rs.getDate(3).toString());
					}
				prst = conn.prepareStatement("select Ngaythue from sinhvien where sinhvien_ID = ?");
				prst.setString(1, ID);
				rs= prst.executeQuery();
				while (rs.next()) {
					String ngaythue = rs.getDate(1).toString();	
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
					LocalDateTime now = LocalDateTime.now();
					dtf.format(now);
					PreparedStatement prst1 = conn.prepareStatement("SELECT DATEDIFF(?,?);");
					prst1.setString(1, dtf.format(now));
					prst1.setString(2, ngaythue );
					ResultSet rs1 = prst1.executeQuery();
						while (rs1.next()) {
							txtSongay.setText(rs1.getString(1));
					}
						ConnectJDBC.closeConnection(null, prst1, rs1);
						
					}
				ConnectJDBC.closeConnection(conn, prst, rs);
					
					
					
					
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLammoi.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLammoi.setBounds(555, 12, 126, 39);
		contentPane.add(btnLammoi);
		
		
	}
}
