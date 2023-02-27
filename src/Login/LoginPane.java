package Login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Color;

public class LoginPane extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPane frame = new LoginPane();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public LoginPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 862, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFocusPainted(false);
		btnLogin.setBackground(new Color(0, 255, 255));
		Image Login = new ImageIcon(this.getClass().getResource("/login.png")).getImage();
		btnLogin.setIcon(new ImageIcon(Login));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login log = new Login();
				log.setVisible(true);
			}
		});
		btnLogin.setBounds(150, 367, 250, 58);
		btnLogin.setToolTipText("");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 31));
		
		
		JButton btnRegister = new JButton("Register");
		Image Register = new ImageIcon(this.getClass().getResource("/verify.png")).getImage();
		btnRegister.setIcon(new ImageIcon(Register));
		btnRegister.setBackground(new Color(255, 255, 0));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Register Rg = new Register();
				Rg.setVisible(true);
				}
		});
		btnRegister.setBounds(429, 367, 221, 58);
		btnRegister.setToolTipText("");
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 31));
		
		contentPane.setLayout(null);
		contentPane.add(btnLogin);
		contentPane.add(btnRegister);
		
		JLabel VKU = new JLabel("");
		VKU.setBackground(new Color(255, 255, 255));
		BufferedImage img = null;
		try {
		    img = ImageIO.read(getClass().getResource("/VKU.png"));
		    Image dimg = img.getScaledInstance(335, 270,Image.SCALE_SMOOTH);
		    VKU.setIcon(new ImageIcon(dimg));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		VKU.setBounds(243, 10, 335, 269);
		contentPane.add(VKU);
		
		JLabel lblNewLabel = new JLabel("Quản lí kí túc xá");
		lblNewLabel.setForeground(new Color(0, 128, 128));
		lblNewLabel.setBackground(new Color(128, 255, 255));
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 41));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(210, 277, 405, 80);
		contentPane.add(lblNewLabel);
	}
}
