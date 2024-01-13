package bloodBankApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

import java.awt.Toolkit;

public class Donation {

	private JFrame frmDonationPage;

	public JFrame getFrmDonationPage() {
		return frmDonationPage;
	}

	private JComboBox<String> cmbBloodBankName;
	private JComboBox<String> cmbDonorNames;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Donation window = new Donation();
					window.frmDonationPage.setVisible(true);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Donation() {
		initialize();
		Connect();
		comboDonorName_load();
		comboBloodBank_load();
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
	 * Populate combo box with name of donor from Donor table
	 */
	public void comboDonorName_load() {
		try {
			pst = con.prepareStatement("SELECT firstName, lastName FROM donor");			//Use of prepareStatement helps prevent the issue of SQL Injection
			rs = pst.executeQuery();
			while (rs.next()) {
				String fName = rs.getString("firstName");
				String lName = rs.getString("lastName");
				cmbDonorNames.addItem(fName + " " + lName);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Populate combo box with bloodbank names from Bloodbank table
	 */
	public void comboBloodBank_load() {
		try {
			pst = con.prepareStatement("SELECT name FROM bloodbank");
			rs = pst.executeQuery();
			while (rs.next()) {
				String bloodbankName = rs.getString("name");
				cmbBloodBankName.addItem(bloodbankName);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDonationPage = new JFrame();
		frmDonationPage.setTitle("Blood Bank App");
		frmDonationPage.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\rjrna\\Documents\\Spring-Summer 2023\\DBAS32100 Database Management\\Project\\bloodFavicon.png"));
		frmDonationPage.setBounds(100, 100, 534, 541);
		frmDonationPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDonationPage.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Blood Bank Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(83, 11, 368, 59);
		frmDonationPage.getContentPane().add(lblNewLabel);

		JLabel lblDonationForm = new JLabel("Donation Form");
		lblDonationForm.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDonationForm.setBounds(105, 81, 236, 48);
		frmDonationPage.getContentPane().add(lblDonationForm);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Donation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(58, 130, 432, 259);
		frmDonationPage.getContentPane().add(panel);

		JLabel lblBloodBankName = new JLabel("Blood Bank");
		lblBloodBankName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBloodBankName.setBounds(44, 97, 93, 26);
		panel.add(lblBloodBankName);

		cmbBloodBankName = new JComboBox<String>();
		cmbBloodBankName.setToolTipText("");
		cmbBloodBankName.setBounds(147, 101, 265, 22);
		panel.add(cmbBloodBankName);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDate.setBounds(90, 134, 38, 26);
		panel.add(lblDate);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(147, 134, 149, 20);
		panel.add(dateChooser);

		JButton btnDonate = new JButton("Donate");
		btnDonate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int bloodBankId = cmbBloodBankName.getSelectedIndex() + 1;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(dateChooser.getDate());
				int donorId = cmbDonorNames.getSelectedIndex() + 1;
				try {
					pst = con.prepareStatement("insert into donation(date, donorId , bloodBankId)values(?,?,?)");			//Use of prepareStatement helps prevent the issue of SQL Injection
					pst.setString(1, date);																					//Insert donation details in Donation table
					pst.setInt(2, donorId);
					pst.setInt(3, bloodBankId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Inserted Donation Record Successfully");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1.getMessage());
				}

			}
		});
		btnDonate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDonate.setBounds(136, 177, 127, 37);
		panel.add(btnDonate);

		JLabel lblRegisteredUsers = new JLabel("Registered Users");
		lblRegisteredUsers.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRegisteredUsers.setBounds(10, 64, 127, 26);
		panel.add(lblRegisteredUsers);

		cmbDonorNames = new JComboBox<String>();
		cmbDonorNames.setToolTipText("");
		cmbDonorNames.setBounds(147, 68, 265, 22);
		panel.add(cmbDonorNames);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDonationPage.dispose();
				MainPage mainPage = new MainPage();
				mainPage.getFrmBloodBankApp().setVisible(true);
			}
		});
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnHome.setBounds(83, 434, 89, 37);
		frmDonationPage.getContentPane().add(btnHome);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDonationPage.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(330, 434, 89, 37);
		frmDonationPage.getContentPane().add(btnExit);
	}
}
