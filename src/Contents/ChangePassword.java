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
import java.awt.event.ActionEvent;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JTextField txtMatkhaucu;
	private JTextField txtMatkhaumoi;
	private JTextField txtNhaplaimatkhau;
	private JTextField txtUsernameNE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword("test");
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
	public ChangePassword(String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Đổi mật khẩu ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(151, 10, 131, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNhpMtKhu = new JLabel("Nhập mật khẩu cũ ");
		lblNhpMtKhu.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNhpMtKhu.setBounds(22, 146, 185, 32);
		contentPane.add(lblNhpMtKhu);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nhập mật khẩu mới");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(22, 188, 185, 32);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Nhập lại mật khẩu");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(22, 230, 185, 32);
		contentPane.add(lblNewLabel_1_1_1);
		
		txtMatkhaucu = new JTextField();
		txtMatkhaucu.setBounds(218, 141, 185, 37);
		contentPane.add(txtMatkhaucu);
		txtMatkhaucu.setColumns(10);
		
		txtMatkhaumoi = new JTextField();
		txtMatkhaumoi.setColumns(10);
		txtMatkhaumoi.setBounds(217, 183, 185, 37);
		contentPane.add(txtMatkhaumoi);
		
		txtNhaplaimatkhau = new JTextField();
		txtNhaplaimatkhau.setColumns(10);
		txtNhaplaimatkhau.setBounds(217, 225, 185, 37);
		contentPane.add(txtNhaplaimatkhau);
		
		JButton btnNewButton = new JButton("Xác Nhận");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String oldpass = txtMatkhaucu.getText().toString();
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("Select sinhVien_Password from sinhvien where sinhVien_Username = ?");
					prst.setString(1, username);
					ResultSet rs = prst.executeQuery();
					while (rs.next()) {
						if(rs.getString(1).equals(oldpass)) {
							String newpass= txtMatkhaumoi.getText().toString();
							String newpass1 = txtNhaplaimatkhau.getText().toString();
							if(newpass.equals(newpass1)) {
								prst = conn.prepareStatement("UPDATE sinhvien SET sinhVien_Password = ? WHERE (sinhVien_Username = ?)");
								prst.setString(1, newpass);
								prst.setString(2, username);
								int rs1 = prst.executeUpdate();
								while(rs1!=0) {
									JOptionPane.showMessageDialog(btnNewButton, "Đổi mật khẩu thành công");
									rs1--;
								}
							}else {
								JOptionPane.showMessageDialog(btnNewButton, "Mật khẩu nhập lại không trùng khớp !!");
							}
						}else {
							JOptionPane.showMessageDialog(btnNewButton, "Mật khẩu không đúng");
						}
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(151, 317, 131, 42);
		contentPane.add(btnNewButton);
		
		JButton btnQuayLi = new JButton("Quay lại");
		btnQuayLi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				StudentsHomePage sthp = new StudentsHomePage(username);
				sthp.setVisible(true);
			}
		});
		btnQuayLi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnQuayLi.setBounds(10, 352, 102, 22);
		contentPane.add(btnQuayLi);
		
		txtUsernameNE = new JTextField();
		txtUsernameNE.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtUsernameNE.setText(username);
		txtUsernameNE.setEditable(false);
		txtUsernameNE.setBounds(178, 52, 170, 32);
		contentPane.add(txtUsernameNE);
		txtUsernameNE.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(22, 49, 164, 35);
		contentPane.add(lblNewLabel_1);
	}

}
