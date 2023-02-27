package Contents;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.ConnectJDBC;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class RoomRegister extends JFrame {

	private JPanel contentPane;
	private JTable tblSinhvien;
	private JLabel lblNewLabel;
	private JTextField txtSoSinhVien;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomRegister frame = new RoomRegister("test");
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
	public RoomRegister(String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(275, 88, 451, 170);
		contentPane.add(scrollPane);

		tblSinhvien = new JTable();
		tblSinhvien.setEnabled(false);
		tblSinhvien.setRowSelectionAllowed(false);
		tblSinhvien.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Danh s\u00E1ch sinh vi\u00EAn ", "M\u00E3 sinh vi\u00EAn"
			}
		));
		tblSinhvien.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblSinhvien.getColumnModel().getColumn(0).setMinWidth(30);
		tblSinhvien.getColumnModel().getColumn(1).setPreferredWidth(30);
		tblSinhvien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(tblSinhvien);
		txtSoSinhVien = new JTextField();
		txtSoSinhVien.setEditable(false);
		txtSoSinhVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSoSinhVien.setBounds(275, 38, 451, 28);
		contentPane.add(txtSoSinhVien);
		txtSoSinhVien.setColumns(10);
		

		JComboBox cbbRoom_ID = new JComboBox();
		
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select room_ID from room");
					ResultSet rs = prst.executeQuery();
					while (rs.next()) {
						cbbRoom_ID.addItem(rs.getString(1));
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cbbRoom_ID.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String Selected = cbbRoom_ID.getSelectedItem().toString();
						try {
							Connection conn = ConnectJDBC.getConnection();
							PreparedStatement prst = conn.prepareStatement("select Mems,capacity from room where room_ID=?");
							prst.setString(1, Selected);
							ResultSet rs = prst.executeQuery();
							while(rs.next()) {
								int mem = rs.getInt(1);
								int capac = rs.getInt(2);
								if(mem==capac) {
									txtSoSinhVien.setText("Phòng đã đầy!! vui lòng chọn phòng khác!!");
								}else {
								txtSoSinhVien.setText("Phòng đã có "+mem+"/"+capac+" sinh viên");}
							};
							prst = conn.prepareStatement("select sinhVien_Name,sinhVien_ID from sinhvien where room_ID = ?");
							prst.setString(1, Selected);
							rs = prst.executeQuery();
							DefaultTableModel model = (DefaultTableModel) tblSinhvien.getModel();
							String svname,svID;
							model.setRowCount(0);
							while(rs.next()) {
								
								svname = rs.getString(1);
								svID= rs.getString(2);
								String[] row = {svname,svID};
								model.addRow(row);
							}
							ConnectJDBC.closeConnection(conn, prst, rs);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
		
		cbbRoom_ID.setBounds(38, 63, 139, 28);
		
	
		contentPane.setLayout(null);
		contentPane.add(cbbRoom_ID);
		
		lblNewLabel = new JLabel("Chọn phòng");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(38, 25, 139, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnDangKi = new JButton("Đăng kí");
		btnDangKi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Selected = cbbRoom_ID.getSelectedItem().toString();
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select Mems,capacity from room where room_ID=?");
					prst.setString(1, Selected);
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
						int mem = rs.getInt(1);
						int capac = rs.getInt(2);
						if(mem==capac) {
							JOptionPane.showMessageDialog(btnDangKi, "Phòng đã đầy ,  vui lòng chọn phòng khác!!  ","Thông báo! ",JOptionPane.INFORMATION_MESSAGE);
						}else {
							
							try {
								prst = conn.prepareStatement("select sinhVien_Username from dangkiphong where sinhVien_Username=?");
								prst.setString(1, username);
								rs = prst.executeQuery();
								if(rs.next()) {
									JOptionPane.showMessageDialog(btnDangKi, "Không thể đăng kí nhiều hơn 1 phòng !!","Thông báo! ",JOptionPane.INFORMATION_MESSAGE);
								}else {
									prst = conn.prepareStatement("Insert into dangkiphong (sinhVien_Username, room_ID) values (?,?)");
									prst.setString(1,username);
									prst.setString(2,Selected);
									boolean execute = prst.execute();
									JOptionPane.showMessageDialog(btnDangKi, "Đăng kí thành công!!","Thông báo! ",JOptionPane.INFORMATION_MESSAGE);
								}

							} catch (Exception e2) {
								e2.printStackTrace();
							}
							
							
						}
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				}catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		btnDangKi.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDangKi.setBounds(38, 171, 139, 49);
		contentPane.add(btnDangKi);
		
		
	}
}
