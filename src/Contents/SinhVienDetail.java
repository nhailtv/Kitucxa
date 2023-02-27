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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class SinhVienDetail extends JFrame {

	private JPanel contentPane;
	private JTextField txtSDT;
	private JTextField txtEmail;
	private JTextField txtName;
	private JTextField txtID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SinhVienDetail frame = new SinhVienDetail("test");
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
	public SinhVienDetail(String Username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 637, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblsinhVien_ID = new JLabel("Mã Sinh Viên");
		lblsinhVien_ID.setBounds(136, 45, 141, 33);
		lblsinhVien_ID.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblsinhVien_ID);
		
		JLabel lblHVTn = new JLabel("Họ và Tên");
		lblHVTn.setBounds(136, 103, 141, 33);
		lblHVTn.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblHVTn);
		
		JLabel lvlEmail = new JLabel("Email");
		lvlEmail.setBounds(136, 162, 141, 33);
		lvlEmail.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lvlEmail);
		
		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setBounds(136, 221, 141, 33);
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblSDT);
		
		
		
		
		txtSDT = new JTextField();
		txtSDT.setBounds(276, 216, 188, 38);
		txtSDT.setColumns(10);
		contentPane.add(txtSDT);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(276, 157, 188, 38);
		txtEmail.setColumns(10);
		contentPane.add(txtEmail);
		
		txtName = new JTextField();
		txtName.setBounds(276, 98, 188, 38);
		txtName.setColumns(10);
		contentPane.add(txtName);
		
		txtID = new JTextField();
		txtID.setBounds(276, 40, 188, 38);
		txtID.setColumns(10);
		contentPane.add(txtID);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(276, 279, 188, 38);
		contentPane.add(comboBox);
		
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select sinhVien_ID , sinhVien_Name , sinhVien_Mail , sinhVien_Sdt , room_ID from sinhvien where sinhVien_Username = ?");
			prst.setString(1, Username);
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				txtID.setText(rs.getString(1));
				txtName.setText(rs.getString(2));
				txtEmail.setText(rs.getString(3));
				txtSDT.setText(rs.getString(4));
				comboBox.addItem(rs.getString(5));
				String Phong = rs.getString(5);
			
				prst = conn.prepareStatement("Select room_ID from room");
				ResultSet rs1 = prst.executeQuery();
				while(rs1.next()) {
					if(rs1.getString(1).equals(rs.getString(5))){
						
					}else {
						comboBox.addItem(rs1.getString(1));
					}
					
				}
				
				
			}
			ConnectJDBC.closeConnection(conn, prst, rs);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		JButton btnNewButton = new JButton("Làm mới ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select sinhVien_ID , sinhVien_Name , sinhVien_Mail , sinhVien_Sdt from sinhvien where sinhVien_Username = ?");
					prst.setString(1, Username);
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
						txtID.setText(rs.getString(1));
						txtName.setText(rs.getString(2));
						txtEmail.setText(rs.getString(3));
						txtSDT.setText(rs.getString(4));
						
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(162, 341, 125, 38);
		contentPane.add(btnNewButton);
		
		JButton btnSa = new JButton("Sửa");
		btnSa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("UPDATE sinhvien SET sinhVien_ID = ?, sinhVien_Name = ?, "
							+ "sinhVien_Mail = ?, sinhVien_Sdt = ? , room_ID = ? WHERE (sinhVien_Username = ?);");
					prst.setString(1, txtID.getText());
					prst.setString(2, txtName.getText());
					prst.setString(3, txtEmail.getText());
					prst.setString(4, txtSDT.getText());
					//prst.setString(5,txtPhong.getText());
					prst.setString(5, comboBox.getSelectedItem().toString());
					prst.setString(6, Username);
					int rs = prst.executeUpdate();
					if(rs !=0 ) {
						JOptionPane.showMessageDialog(btnSa, "Đã thay đổi thành công");
					}
					
					ConnectJDBC.closeConnection(conn, prst, null);
					AdminHomePane.roomcountupdate();
					} catch (Exception e2) {
					// TODO: handle exception	
					e2.printStackTrace();
				}
			}
		});
		btnSa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSa.setBounds(319, 341, 125, 38);
		contentPane.add(btnSa);
		
		JLabel lblPhng = new JLabel("Phòng");
		lblPhng.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPhng.setBounds(136, 279, 141, 33);
		contentPane.add(lblPhng);
		
		
		
	
	}
}
