package bloodBankApp;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;

public class BloodbankInfo {

	private JFrame frmBloodbankInfo;

	public JFrame getFrmBloodbankInfo() {
		return frmBloodbankInfo;
	}

	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BloodbankInfo window = new BloodbankInfo();
					window.frmBloodbankInfo.setVisible(true);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BloodbankInfo() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	/**
	 * Establish the Connection with MySql database
	 */
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/groupprojectdb", "naikra", "Rn@mydb2023");
		} catch (ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Populate table with data from Bloodbank table
	 */
	public void table_load() {
		try {
			pst = con.prepareStatement(							//Use of prepareStatement helps prevent the issue of SQL Injection
					"SELECT name as 'Bloodbank Name', location.city as 'City', location.province as 'Province', location.street as 'Street Address', location.zip as 'ZIP', capacity as 'Donor Capacity' from bloodbank, location \r\n"
							+ "WHERE location.locationId = bloodbank.locationId;");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBloodbankInfo = new JFrame();
		frmBloodbankInfo.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\rjrna\\Documents\\Spring-Summer 2023\\DBAS32100 Database Management\\Project\\bloodFavicon.png"));
		frmBloodbankInfo.setTitle("Blood Bank App");
		frmBloodbankInfo.setBounds(100, 100, 720, 500);
		frmBloodbankInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBloodbankInfo.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Blood Bank Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(167, 11, 351, 59);
		frmBloodbankInfo.getContentPane().add(lblNewLabel);

		JLabel lblSupportedBloodbanks = new JLabel("Supported Bloodbanks");
		lblSupportedBloodbanks.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSupportedBloodbanks.setBounds(241, 91, 248, 48);
		frmBloodbankInfo.getContentPane().add(lblSupportedBloodbanks);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 150, 500, 200);
		frmBloodbankInfo.getContentPane().add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodbankInfo.dispose();
				MainPage mainPage = new MainPage();
				mainPage.getFrmBloodBankApp().setVisible(true);
			}
		});
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnHome.setBounds(167, 392, 89, 37);
		frmBloodbankInfo.getContentPane().add(btnHome);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodbankInfo.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(414, 392, 89, 37);
		frmBloodbankInfo.getContentPane().add(btnExit);
	}

}
