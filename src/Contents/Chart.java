package Contents;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import Connect.ConnectJDBC;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chart extends JFrame {

	private JPanel contentPane;
	private ChartPanel CP ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chart frame = new Chart();
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
	public Chart() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(22, 25, 364, 313);
		contentPane.add(panel);
		
		JButton Update = new JButton("Update");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultPieDataset pieDataset = new DefaultPieDataset();
				int tongSVdango = 0, tongSucchua = 0 , Conlai;
				try {
					Connection conn = ConnectJDBC.getConnection();
					String sql="select mems from room"; 
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(sql);
					while(rs.next()) {
						tongSVdango += rs.getInt(1);
					}
					
					sql = "select capacity from room ";
					rs = st .executeQuery(sql);
					while(rs.next()) {
						tongSucchua+=rs.getInt(1);
					}
					
					Conlai = tongSucchua- tongSVdango;
					pieDataset.setValue("Số sinh viên đang ở", tongSVdango);
					pieDataset.setValue("tổng số chỗ còn lại ", Conlai);
					
					JFreeChart chart = ChartFactory.createPieChart("Quản lí ", pieDataset, true,true,true);
			
					CP = new ChartPanel(chart);
					 
					CP.setBounds(10, 25, 364, 313);
					contentPane.add(CP);
					
		
					st.close();
					ConnectJDBC.closeConnection(conn, null, rs);
					chart.fireChartChanged();
					} catch (Exception e2) {
					e2.printStackTrace();
			
				}
			}
		});
		Update.setBounds(122, 348, 136, 41);
		contentPane.add(Update);
		
		
	
		
			
		
		
		
		
		
	}
}
