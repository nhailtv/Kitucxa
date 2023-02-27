package Contents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Connect.ConnectJDBC;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class tienphong extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tienphong frame = new tienphong();
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
	public tienphong() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 817, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 38, 541, 238);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn sinh vi\u00EAn", "Ph\u00F2ng", "s\u1ED1 ng\u00E0y k\u1EC3 t\u1EEB l\u1EA7n cu\u1ED1i tr\u1EA3 ti\u1EC1n ph\u00F2ng"
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(111);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(181);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("làm mới");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select sinhVien_ID ,sinhVien_Name , room_ID  from sinhvien where room_ID IS NOT NULL ");
					ResultSet rs = prst.executeQuery();
					String sv_ID , sv_name , room_ID;
					while(rs.next()) {
						sv_ID = rs.getString(1);
						sv_name = rs.getString(2);
						room_ID = rs.getString(3);
						PreparedStatement prst1 = conn.prepareStatement("Select Ngaythue from sinhvien where sinhVien_ID = ?");
						prst1.setString(1, rs.getString(1));
						ResultSet rs1 = prst1.executeQuery();
								while(rs1.next()) {
									String ngaythue = rs1.getDate(1).toString();	
									DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
									LocalDateTime now = LocalDateTime.now();  
									dtf.format(now);
									prst1 = conn.prepareStatement("SELECT DATEDIFF(?,?);");
									prst1.setString(1, dtf.format(now));
									prst1.setString(2, ngaythue );
									ResultSet rs2 = prst1.executeQuery();
									while(rs2.next()) {
										String date = rs2.getString(1);
										String[] row = {sv_ID , sv_name , room_ID , date};
										model.addRow(row);
					
									}
								}	
					}
					
				
					

					
			
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton.setBounds(207, 303, 117, 37);
		contentPane.add(btnNewButton);
		
		JButton btnThanhToan = new JButton("Thanh toán");
		btnThanhToan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String Selected = table.getValueAt(row, 0).toString();
				
				Thanhtoan tt = new Thanhtoan(Selected);
				tt.setVisible(true);
				
				
				
				
			}
		});
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnThanhToan.setBounds(345, 303, 146, 37);
		contentPane.add(btnThanhToan);
	}
}
