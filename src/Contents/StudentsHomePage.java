package Contents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.ConnectJDBC;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentsHomePage extends JFrame {

	private JPanel contentPane;
	private JTextField txtTenSinhVien;
	private JTextField txtRoomID;
	private JTextField txtSongay;
	private JTextField txtThongbao;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentsHomePage frame = new StudentsHomePage("test_01");
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
	public StudentsHomePage(String username) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSongay = new JTextField();
		txtSongay.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSongay.setEditable(false);
		txtSongay.setBounds(332, 134, 99, 37);
		contentPane.add(txtSongay);
		txtSongay.setColumns(10);
		
		txtTenSinhVien = new JTextField();
		txtTenSinhVien.setEditable(false);
		txtTenSinhVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		txtThongbao = new JTextField();
		txtThongbao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtThongbao.setEditable(false);
		txtThongbao.setBounds(441, 134, 248, 40);
		contentPane.add(txtThongbao);
		txtThongbao.setColumns(10);
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select sinhVien_Name from sinhvien where sinhVien_Username = ? ");
			prst.setString(1, username);
			ResultSet rs = prst.executeQuery();
			if(rs.next()) {
				txtTenSinhVien.setText(rs.getString(1));
			}
			prst = conn.prepareStatement("select Ngaythue from sinhvien where sinhvien_Username = ? and Ngaythue IS NOT NULL");
			prst.setString(1, username);
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
						if (rs1.getInt(1) > 31) {
							txtThongbao.setText("Bạn đã đến hạn trả tiền phòng");
						}
				}
					ConnectJDBC.closeConnection(null, prst1, rs1);
					
				}
			ConnectJDBC.closeConnection(conn, prst, rs);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		txtTenSinhVien.setBounds(331, 38, 274, 40);
		contentPane.add(txtTenSinhVien);
		txtTenSinhVien.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tên sinh viên");
		lblNewLabel.setBounds(10, 42, 135, 40);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblNewLabel);
		
		JLabel lblPhng = new JLabel("Phòng");
		lblPhng.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPhng.setBounds(10, 86, 135, 40);
		contentPane.add(lblPhng);
		
		txtRoomID = new JTextField();
		txtRoomID.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtRoomID.setEditable(false);
		txtRoomID.setColumns(10);
		txtRoomID.setBounds(331, 88, 100, 40);
		contentPane.add(txtRoomID);
		
		JButton btnNewButton = new JButton("Đổi mật khẩu");
		btnNewButton.setBounds(30, 191, 206, 54);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ChangePassword chp = new ChangePassword(username);
				chp.setVisible(true);
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		
		
		JButton btnngKPhng = new JButton("Đăng kí phòng");
		btnngKPhng.setBounds(257, 191, 201, 54);
		contentPane.add(btnngKPhng);
		btnngKPhng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("Select room_ID from sinhvien where sinhVien_Username = ?");
					prst.setString(1,username);
					
					ResultSet rs = prst.executeQuery();
					
					if(rs.next()){
						
						if (rs.getObject(1) != null) {
							JOptionPane.showMessageDialog(btnngKPhng,"Bạn đã có phòng , không thể đăng kí mới !! ","Notification", JOptionPane.ERROR_MESSAGE);	
						}else {
							RoomRegister room = new RoomRegister(username);
							room.setVisible(true);
							
						}
						
					}
						
					
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
			}
		});
		btnngKPhng.setFont(new Font("Tahoma", Font.BOLD, 18));
		
	
		
		JLabel lblNewLabel_1 = new JLabel("Số ngày kể từ lần cuối thanh toán");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 136, 321, 30);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 306, 648, 205);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn sinh vi\u00EAn", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1_1 = new JLabel("Thành viên trong phòng");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(24, 268, 321, 30);
		contentPane.add(lblNewLabel_1_1);
		
		try {
			String room_ID ;
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select room_ID from sinhvien where sinhVien_Username = ?");
			prst.setString(1, username);
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				room_ID = rs.getString(1);
				prst = conn.prepareStatement("Select sinhVien_ID , sinhVien_Name , sinhVien_Sdt from sinhvien where room_ID= ? ");
				prst.setString(1, room_ID);
				rs = prst.executeQuery();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while(rs.next()) {
						String[] row = {rs.getString(1),rs.getString(2),rs.getString(3)};
						model.addRow(row);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("Select room_ID from sinhvien where sinhVien_Username = ?");
			prst.setString(1,username);
			ResultSet rs = prst.executeQuery();
			while(rs.next()){
				txtRoomID.setText(rs.getString(1));
			}
			ConnectJDBC.closeConnection(conn, prst, rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
