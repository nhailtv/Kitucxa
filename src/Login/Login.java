package Login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.ConnectJDBC;
import Contents.AdminHomePane;
import Contents.StudentsHomePage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsername.setBounds(73, 60, 101, 40);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPassword.setBounds(73, 118, 101, 40);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(178, 60, 164, 40);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(178, 122, 164, 40);
		contentPane.add(txtPassword);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginPane LP = new LoginPane();
				LP.setVisible(true);
			}
		});
		btnBack.setBounds(47, 213, 69, 21);
		contentPane.add(btnBack);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//khúc chỗ này để viết event cho nút login 
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());
				StringBuilder sb = new StringBuilder();
				if (username.equals("")) {
					sb.append("Username can't be Null\n");
				}
				if (password.equals("")) {
					sb.append("Password can't be Null\n");
				}
				if(sb.length()>0) {
					JOptionPane.showMessageDialog(btnLogin,sb.toString(),"Invalidation", JOptionPane.ERROR_MESSAGE);
				}
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select * from admin where username=? and password=?");
					prst.setString(1, username);
					prst.setString(2, password);
					ResultSet rs = prst.executeQuery();
					if(rs.next()) {
							dispose();
							AdminHomePane adhp = new AdminHomePane();
							adhp.setVisible(true);
					}else {
						try {
							
							prst = conn.prepareStatement("select * from sinhvien where sinhVien_Username=? and sinhVien_Password=?");
							prst.setString(1, username);
							prst.setString(2, password);
							rs = prst.executeQuery();
							if(rs.next()) {
								dispose();
								StudentsHomePage sthp = new StudentsHomePage(username);
								sthp.setVisible(true);
								
								
							}else {
								JOptionPane.showMessageDialog(btnLogin,"Invalid Username or Password","Notification" , JOptionPane.INFORMATION_MESSAGE);
								//
							}

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}

					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				
				
				
				//
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setBounds(187, 203, 101, 40);
		contentPane.add(btnLogin);
	}

}
