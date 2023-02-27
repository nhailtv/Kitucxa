package Login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.ConnectJDBC;
import DAO.SinhVienDAO;
import model.SinhVien;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField txtsinhVien_ID;
	private JTextField txtsinhVien_Name;
	private JTextField txtsinhVien_Mail;
	private JTextField txtsinhVien_Sdt;
	private JTextField txtUsername;
	private JPasswordField pwfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblsinhVien_ID = new JLabel("Mã Sinh Viên");
		lblsinhVien_ID.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblsinhVien_ID.setBounds(119, 42, 141, 33);
		contentPane.add(lblsinhVien_ID);
		
		JLabel lblHVTn = new JLabel("Họ và Tên");
		lblHVTn.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHVTn.setBounds(119, 100, 141, 33);
		contentPane.add(lblHVTn);
		
		JLabel lvlEmail = new JLabel("Email");
		lvlEmail.setFont(new Font("Tahoma", Font.BOLD, 18));
		lvlEmail.setBounds(119, 159, 141, 33);
		contentPane.add(lvlEmail);
		
		txtsinhVien_ID = new JTextField();
		txtsinhVien_ID.setBounds(259, 37, 188, 38);
		contentPane.add(txtsinhVien_ID);
		txtsinhVien_ID.setColumns(10);
		
		txtsinhVien_Name = new JTextField();
		txtsinhVien_Name.setColumns(10);
		txtsinhVien_Name.setBounds(259, 95, 188, 38);
		contentPane.add(txtsinhVien_Name);
		
		txtsinhVien_Mail = new JTextField();
		txtsinhVien_Mail.setColumns(10);
		txtsinhVien_Mail.setBounds(259, 154, 188, 38);
		contentPane.add(txtsinhVien_Mail);
		
		JLabel lblSDT = new JLabel("Số điện thoại");
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSDT.setBounds(119, 218, 141, 33);
		contentPane.add(lblSDT);
		
		txtsinhVien_Sdt = new JTextField();
		txtsinhVien_Sdt.setColumns(10);
		txtsinhVien_Sdt.setBounds(259, 213, 188, 38);
		contentPane.add(txtsinhVien_Sdt);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsername.setBounds(119, 275, 141, 33);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(259, 270, 188, 38);
		contentPane.add(txtUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPassword.setBounds(119, 333, 141, 33);
		contentPane.add(lblPassword);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginPane LP = new LoginPane();
				LP.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBounds(47, 405, 85, 21);
		contentPane.add(btnBack);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtsinhVien_ID.getText();;
				String name = txtsinhVien_Name.getText();
				String email= txtsinhVien_Mail.getText();
				String sdt = txtsinhVien_Sdt.getText();
				String Rusername = txtUsername.getText();
				String Rpassword = String.valueOf(pwfPassword.getPassword());
				
				
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select * from sinhvien where sinhVien_Username = ?");
					prst.setString(1, Rusername);
					ResultSet rs = prst.executeQuery();
					if (rs.next()) {
						JOptionPane.showMessageDialog(btnRegister,"Tên đăng nhập đã tồn tại!","Chú ý!", JOptionPane.INFORMATION_MESSAGE);
					}else {
						prst = conn.prepareStatement("select * from sinhvien where sinhVien_Mail = ?");
						prst.setString(1, email);
						rs = prst.executeQuery();
						if (rs.next()) {
						JOptionPane.showMessageDialog(btnRegister,"Email đã tồn tại","Chú ý!", JOptionPane.INFORMATION_MESSAGE);
							}else {
								prst = conn.prepareStatement("select * from sinhvien where sinhVien_Sdt = ?");
								prst.setString(1, sdt);
								rs = prst.executeQuery();
								if (rs.next()) {
									JOptionPane.showMessageDialog(btnRegister,"Số điện thoại đã tồn tại!!","Chú ý!", JOptionPane.INFORMATION_MESSAGE);
									}else {
										prst = conn.prepareStatement("select * from sinhvien where sinhVien_ID = ?");
										prst.setString(1, id);
										rs = prst.executeQuery();
										if (rs.next()) {
											JOptionPane.showMessageDialog(btnRegister,"Mã sinh viên đã tồn tại!!","Chú ý!", JOptionPane.INFORMATION_MESSAGE);
											}else {
												prst = conn.prepareStatement("select * from admin where username = ? ");
											    prst.setString(1, Rusername);
											    rs = prst.executeQuery();
											    if(rs.next()) {
											    	JOptionPane.showMessageDialog(btnRegister, "Tên tài khoản đã tồn tại !!","Chú ý!", JOptionPane.INFORMATION_MESSAGE);
											    	
											    }else {
											    	SinhVien sv= new SinhVien(id,name,email,sdt,Rusername,Rpassword);
											    	SinhVienDAO.getInstance().Add(sv);}
									}
									}
						
					}
					}
					
					ConnectJDBC.closeConnection(conn, prst, rs);
					
					
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegister.setBounds(279, 393, 129, 38);
		contentPane.add(btnRegister);
		
		pwfPassword = new JPasswordField();
		pwfPassword.setBounds(259, 333, 188, 38);
		contentPane.add(pwfPassword);
	}
}
