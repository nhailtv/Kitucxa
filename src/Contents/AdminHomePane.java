package Contents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import Connect.ConnectJDBC;
import DAO.SinhVienDAO;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AdminHomePane extends JFrame {
	

	private JPanel contentPane;
	private ChartPanel CP = new ChartPanel(null);
	private JTable STDtable;
	private JTable tblRoomMembers;
	private JTable tblSinhviendangkiphong;
	private JTextField Search;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminHomePane frame = new AdminHomePane();
					frame.setLocationRelativeTo(null);
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
	public AdminHomePane() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1473, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 143, 423, 233);
		contentPane.add(scrollPane);
		
		STDtable = new JTable();
		STDtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn sinh vi\u00EAn"
			}
		));
		STDtable.getColumnModel().getColumn(0).setPreferredWidth(30);
		STDtable.getColumnModel().getColumn(1).setPreferredWidth(100);
		scrollPane.setViewportView(STDtable);
		
		JButton STDUpdate = new JButton("Làm mới");
		STDUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = ConnectJDBC.getConnection();

					PreparedStatement prst = conn.prepareStatement("select sinhVien_Name,sinhVien_ID from sinhvien");
					
					ResultSet rs = prst.executeQuery();
					DefaultTableModel model = (DefaultTableModel) STDtable.getModel();
					String svname,svID;
					model.setRowCount(0);
					while(rs.next()) {
						svname = rs.getString(1);
						svID= rs.getString(2);
						String[] row = {svID,svname};
						model.addRow(row);
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		STDUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		STDUpdate.setBounds(29, 386, 110, 34);
		contentPane.add(STDUpdate);
		
		JButton STDDetail = new JButton("Chi tiết");
		STDDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = STDtable.getSelectedRow();
				String Value = STDtable.getValueAt(row, 0).toString();
				System.out.println(Value);
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("Select sinhVien_Username from sinhvien where sinhVien_ID = ?");
					prst.setString(1, Value);
					ResultSet rs = prst.executeQuery();
					while(rs.next()) {
					SinhVienDetail svdt = new SinhVienDetail(rs.getString(1));
					svdt.setVisible(true);
					}
					
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
			}
		});
		STDDetail.setFont(new Font("Tahoma", Font.BOLD, 16));
		STDDetail.setBounds(168, 386, 93, 34);
		contentPane.add(STDDetail);
		
		JButton btnRoomDelete = new JButton("Xóa");
		btnRoomDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Selected = tblRoomMembers.getSelectedRow();
				try {
					Connection conn = ConnectJDBC.getConnection();
					String sv_ID = tblRoomMembers.getValueAt(Selected, 1).toString();
					PreparedStatement prst = conn.prepareStatement("UPDATE sinhvien SET room_ID = null WHERE (sinhVien_ID = ?);");
					prst.setString(1,sv_ID);
					int rs = prst.executeUpdate();	
					if(rs !=0) {
						JOptionPane.showMessageDialog(btnRoomDelete, "Xóa thành công thành viên "+sv_ID+" khỏi phòng!");
						rs--;
						prst = conn.prepareStatement("UPDATE `kinhvien` SET `Ngaythue` = NULL WHERE (`sinhVien_ID` = ?)");
						prst.setString(1, sv_ID);
						prst.execute();
							
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnRoomDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRoomDelete.setBounds(753, 386, 93, 34);
		contentPane.add(btnRoomDelete);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(504, 143, 342, 233);
		contentPane.add(scrollPane_1_1);
		
		tblRoomMembers = new JTable();
		tblRoomMembers.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn sinh vi\u00EAn"
			}
		));
		scrollPane_1_1.setViewportView(tblRoomMembers);
		
		JComboBox Roomcbb = new JComboBox();
		
		try {
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select room_ID from room");
			ResultSet rs = prst.executeQuery();
			while(rs.next()) {
				Roomcbb.addItem(rs.getString(1));
			}
			ConnectJDBC.closeConnection(conn, prst, rs);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		
		
		
		
		Roomcbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Selected = Roomcbb.getSelectedItem().toString();
				try {
					Connection conn = ConnectJDBC.getConnection();
					
					PreparedStatement prst = conn.prepareStatement("select sinhVien_Name,sinhVien_ID from sinhvien where room_ID = ?");
					prst.setString(1, Selected);
					ResultSet rs = prst.executeQuery();
					DefaultTableModel model = (DefaultTableModel) tblRoomMembers.getModel();
					String svname,svID;
					model.setRowCount(0);
					while(rs.next()) {
						
						svname = rs.getString(2);
						svID= rs.getString(1);
						String[] row = {svname,svID};
						model.addRow(row);
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		
		Roomcbb.setBounds(504, 99, 136, 34);
		contentPane.add(Roomcbb);
		
		JButton btnRoomUpdate = new JButton("Làm mới");
		btnRoomUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Selected = Roomcbb.getSelectedItem().toString();
				try {
					Connection conn = ConnectJDBC.getConnection();
					
					PreparedStatement prst = conn.prepareStatement("select sinhVien_Name,sinhVien_ID from sinhvien where room_ID = ?");
					prst.setString(1, Selected);
					ResultSet rs = prst.executeQuery();
					DefaultTableModel model = (DefaultTableModel) tblRoomMembers.getModel();
					String svname,svID;
					model.setRowCount(0);
					while(rs.next()) {
						
						svname = rs.getString(2);
						svID= rs.getString(1);
						String[] row = {svname,svID};
						model.addRow(row);
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRoomUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRoomUpdate.setBounds(514, 383, 110, 34);
		contentPane.add(btnRoomUpdate);
		
		JLabel lblNewLabel = new JLabel("Quản lý sinh viên");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 41));
		lblNewLabel.setBounds(33, 37, 408, 44);
		contentPane.add(lblNewLabel);
		
		JLabel lblQunLPhng = new JLabel("Quản lý phòng");
		lblQunLPhng.setFont(new Font("Tahoma", Font.BOLD, 41));
		lblQunLPhng.setBounds(504, 34, 408, 44);
		contentPane.add(lblQunLPhng);
		
		JLabel lblSinhVinng = new JLabel("Sinh viên đăng kí phòng");
		lblSinhVinng.setFont(new Font("Tahoma", Font.BOLD, 41));
		lblSinhVinng.setBounds(910, 34, 549, 44);
		contentPane.add(lblSinhVinng);
		
		JScrollPane scrollPane_1_1_1 = new JScrollPane();
		scrollPane_1_1_1.setBounds(930, 99, 423, 277);
		contentPane.add(scrollPane_1_1_1);
		
		tblSinhviendangkiphong = new JTable();
		tblSinhviendangkiphong.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn sinh vi\u00EAn", "Ph\u00F2ng \u0110\u0103ng K\u00ED "
			}
		));
		scrollPane_1_1_1.setViewportView(tblSinhviendangkiphong);
		try {
			String DKP , username ;
			Connection conn = ConnectJDBC.getConnection();
			PreparedStatement prst = conn.prepareStatement("select * from dangkiphong");
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				username = rs.getString(1);
				DKP = rs.getString(2);
				prst = conn.prepareStatement("select sinhVien_ID , sinhVien_Name from sinhvien where sinhVien_Username = ? ");
				prst.setString(1, username);
				rs = prst.executeQuery();
				DefaultTableModel model = (DefaultTableModel) tblSinhviendangkiphong.getModel();
				String sv_ID , sv_Name;
				while (rs.next()) {
					sv_ID = rs.getString(1);
					sv_Name = rs.getString(2);
					String[] row = {sv_ID , sv_Name , DKP};
					model.addRow(row);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		JButton btnNewButton_1_1_1_1 = new JButton("Xóa");
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tblSinhviendangkiphong.getModel();
				int row = tblSinhviendangkiphong.getSelectedRow();
				String Selected1 = tblSinhviendangkiphong.getValueAt(row, 0).toString();
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("Select sinhVien_Username from sinhvien where sinhVien_ID = ?");
					prst.setString(1, Selected1);
					ResultSet rs = prst.executeQuery();
					while (rs.next()) {
						String uname = rs.getString(1);
					
						prst = conn.prepareStatement("delete from dangkiphong where sinhVien_Username = ?");
						prst.setString(1, uname);
						int rs1= prst.executeUpdate();
						while(rs1!=0) {
							JOptionPane.showMessageDialog(btnNewButton_1_1_1_1, "Xóa thành công");
							model.removeRow(row);
							rs1--;
						}
						
					}
					ConnectJDBC.closeConnection(conn, prst, rs);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1_1_1_1.setBounds(1218, 386, 93, 34);
		contentPane.add(btnNewButton_1_1_1_1);
		
		JButton btnSVDKPOK = new JButton("Duyệt\r\n");
		btnSVDKPOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = ConnectJDBC.getConnection();
					int Srow = tblSinhviendangkiphong.getSelectedRow();
					String sv_ID = tblSinhviendangkiphong.getValueAt(Srow, 0).toString();
					String sv_DKP = tblSinhviendangkiphong.getValueAt(Srow, 2).toString();
					
					
					PreparedStatement prst = conn.prepareStatement("UPDATE sinhvien SET room_ID = ? WHERE (sinhVien_ID = ?)");
					prst.setString(1, sv_DKP);
					prst.setString(2, sv_ID);
	
					
					int rs = prst.executeUpdate();
					while(rs != 0) {
						JOptionPane.showMessageDialog(btnSVDKPOK, "Đã phê duyệt thành công sinh viên "+sv_ID+" vào kí túc xá !!");
						prst = conn.prepareStatement("select sinhVien_username from sinhvien where sinhVien_ID = ?");
						prst.setString(1, sv_ID);
						rs--;
						ResultSet rs1 = prst.executeQuery();
						while (rs1.next()) {
							prst = conn.prepareStatement("Delete from dangkiphong where sinhVien_Username = ?");
							prst.setString(1,rs1.getString(1));
							prst.execute();
							DefaultTableModel model = (DefaultTableModel)tblSinhviendangkiphong.getModel();
							model.removeRow(tblSinhviendangkiphong.getSelectedRow());
						}
						prst = conn.prepareStatement("UPDATE sinhvien SET Ngaythue = ? WHERE (sinhVien_ID = ?);");
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");  
						LocalDateTime now = LocalDateTime.now();  
						
						prst.setString(1, dtf.format(now));
						prst.setString(2, sv_ID);
						prst.executeUpdate();
						
						prst = conn.prepareStatement("Select Mems from room where room_ID = ? ");
						prst.setString(1,sv_DKP);
						rs1 = prst.executeQuery();
						while(rs1.next()) {
							int mems = rs1.getInt(1);
							mems++;
							prst = conn.prepareStatement("UPDATE room SET Mems = ? WHERE (room_ID = ?)");
							prst.setInt(1, mems);
							prst.setString(2, sv_DKP);
							prst.execute();
						}
						
						
						ConnectJDBC.closeConnection(conn, prst, rs1);
						
						
						
						
						
					}
				
					
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSVDKPOK.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSVDKPOK.setBounds(1092, 386, 93, 34);
		contentPane.add(btnSVDKPOK);
		
		JButton btnSVDKPUpdate = new JButton("Làm mới");
		btnSVDKPUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model = (DefaultTableModel) tblSinhviendangkiphong.getModel();
					model.setRowCount(0);
					String DKP , username ;
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("select * from dangkiphong");
					ResultSet rs = prst.executeQuery();
					while (rs.next()) {
						username = rs.getString(1);
						DKP = rs.getString(2);
						prst = conn.prepareStatement("select sinhVien_ID , sinhVien_Name from sinhvien where sinhVien_Username = ? ");
						prst.setString(1, username);
						rs = prst.executeQuery();
						
						String sv_ID , sv_Name;
						while (rs.next()) {
							sv_ID = rs.getString(1);
							sv_Name = rs.getString(2);
							String[] row = {sv_ID , sv_Name , DKP};
							model.addRow(row);
							
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSVDKPUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSVDKPUpdate.setBounds(940, 386, 110, 34);
		contentPane.add(btnSVDKPUpdate);
		
		JButton btnChart = new JButton("Tình trạng kí túc xá");
		btnChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Chart chart = new Chart();
				chart.setVisible(true);
			}
		});
		btnChart.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnChart.setBounds(504, 442, 342, 64);
		contentPane.add(btnChart);
		
		
		
		JComboBox SVcbb = new JComboBox();
		SVcbb.setBounds(29, 99, 110, 34);
		contentPane.add(SVcbb);
		SVcbb.addItem("Mã Sinh Viên");
		SVcbb.addItem("Tên Sinh Viên");
		
		
		Search = new JTextField();
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("đã nhập");
			}
		});
		Search.setBounds(149, 99, 202, 34);
		contentPane.add(Search);
		Search.setColumns(10);
		
		JButton btnTm = new JButton("Tìm");
		btnTm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int Selected = SVcbb.getSelectedIndex();
				String search = Search.getText();
																
				switch (Selected) {
				case 0: {
					try {
						Connection conn = ConnectJDBC.getConnection();
						PreparedStatement prst = conn.prepareStatement("Select sinhVien_Name,sinhVien_ID from sinhvien where sinhVien_ID like ?");
						prst.setString(1, "%"+search+"%");
						DefaultTableModel model = (DefaultTableModel) STDtable.getModel();
						String svname,svID;
						model.setRowCount(0);
						ResultSet rs = prst.executeQuery();
						while (rs.next()) {
								svname = rs.getString(1);
								svID= rs.getString(2);
								String[] row = {svID,svname};
								model.addRow(row);
							}
							ConnectJDBC.closeConnection(conn, prst, rs);
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					break;
					}
				case 1 : {
				try {
					Connection conn = ConnectJDBC.getConnection();
					PreparedStatement prst = conn.prepareStatement("Select sinhVien_Name,sinhVien_ID from sinhvien where sinhVien_Name like ?");
					prst.setString(1, "%"+search+"%");
					DefaultTableModel model = (DefaultTableModel) STDtable.getModel();
					String svname,svID;
					model.setRowCount(0);
					ResultSet rs = prst.executeQuery();
					while (rs.next()) {
							svname = rs.getString(1);
							svID= rs.getString(2);
							String[] row = {svID,svname};
							model.addRow(row);
						}
						ConnectJDBC.closeConnection(conn, prst, rs);
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				break;
				}}
			}
		});
		btnTm.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTm.setBounds(359, 99, 93, 34);
		contentPane.add(btnTm);
		
		JButton btnTienphong = new JButton("Tiền phòng");
		btnTienphong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				tienphong tp = new tienphong();
				tp.setVisible(true);

			}
		});
		btnTienphong.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTienphong.setBounds(29, 430, 146, 34);
		contentPane.add(btnTienphong);
	
			
			
		
		
		
	}
}
