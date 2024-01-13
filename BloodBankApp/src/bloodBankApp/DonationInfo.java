package bloodBankApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DonationInfo {

	private JFrame frmDonationPage;

	public JFrame getFrmDonationPage() {
		return frmDonationPage;
	}

	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DonationInfo window = new DonationInfo();
					window.frmDonationPage.setVisible(true);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DonationInfo() {
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
	 * Populate table with data from Donation table
	 */
	public void table_load() {
		try {																
			pst = con.prepareStatement(										//Use of prepareStatement helps prevent the issue of SQL Injection
					"SELECT donation.donationId as 'Donation ID', firstName as 'Donor Name', lastName as 'Donor Last', age as 'Donor Age', bloodtype.type as 'Blood Group', donation.date as 'Date of Donation' FROM donor, bloodtype, donation\r\n"
							+ "WHERE bloodtype.bloodTypeId = donor.bloodTypeId\r\n"
							+ "AND donation.donorId = donor.donorId\r\n" + "ORDER BY donation.donationId;");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void initialize() {
		frmDonationPage = new JFrame();
		frmDonationPage.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\rjrna\\Documents\\Spring-Summer 2023\\DBAS32100 Database Management\\Project\\bloodFavicon.png"));
		frmDonationPage.setTitle("Blood Bank App");
		frmDonationPage.setBounds(100, 100, 720, 500);
		frmDonationPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDonationPage.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Blood Bank Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(167, 11, 351, 59);
		frmDonationPage.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 150, 500, 200);
		frmDonationPage.getContentPane().add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDonationPage.dispose();
				MainPage mainPage = new MainPage();
				mainPage.getFrmBloodBankApp().setVisible(true);
			}
		});
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnHome.setBounds(167, 392, 89, 37);
		frmDonationPage.getContentPane().add(btnHome);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDonationPage.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(414, 392, 89, 37);
		frmDonationPage.getContentPane().add(btnExit);

		JLabel lblDonationRecords = new JLabel("Donation Records");
		lblDonationRecords.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDonationRecords.setBounds(241, 91, 225, 48);
		frmDonationPage.getContentPane().add(lblDonationRecords);
	}
}
