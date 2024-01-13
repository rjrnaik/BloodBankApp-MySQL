package bloodBankApp;

import java.sql.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.proteanit.sql.DbUtils;//uses rs2xml.jar file
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;

public class MainPage {
	private JFrame frmBloodBankApp;

	public JFrame getFrmBloodBankApp() {
		return frmBloodBankApp;
	}

	private JTextField txtLName;
	private JTextField txtFName;
	private JTextField txtAge;
	private JTable table;
	private JTextField txDonorID;
	private String donorId;

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}

	public JTextField getTxDonorID() {
		return txDonorID;
	}

	private JComboBox<String> cmbBloodType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frmBloodBankApp.setVisible(true);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
		Connect();
		table_load();// should be commented at the beginning and later opened
	}

// the main methods
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
	 * Populate table with data from Donor table
	 */
	public void table_load() {
		try {
			pst = con.prepareStatement(
					"SELECT donorId as 'Donor ID', firstName as 'First Name', lastName as 'Last Name', age as 'Donor Age', bloodtype.type as 'Blood Group' FROM donor, bloodtype \r\n"
							+ "WHERE bloodtype.bloodTypeId = donor.bloodTypeId;");
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
		frmBloodBankApp = new JFrame();
		frmBloodBankApp.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\rjrna\\Documents\\Spring-Summer 2023\\DBAS32100 Database Management\\Project\\bloodFavicon.png"));
		frmBloodBankApp.setTitle("Blood Bank App");
		frmBloodBankApp.setBounds(100, 100, 897, 501);
		frmBloodBankApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBloodBankApp.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("Blood Bank Application");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(246, 11, 351, 59);
		frmBloodBankApp.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(38, 108, 305, 187);
		frmBloodBankApp.getContentPane().add(panel);
		panel.setLayout(null);
		JLabel lblBName = new JLabel("First name");
		lblBName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBName.setBounds(10, 22, 93, 26);
		panel.add(lblBName);
		JLabel lblPrice = new JLabel("Age");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(10, 96, 93, 26);
		panel.add(lblPrice);
		JLabel lblEdition = new JLabel("Last name");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEdition.setBounds(10, 59, 93, 26);
		panel.add(lblEdition);
		txtLName = new JTextField();
		txtLName.setBounds(113, 61, 162, 26);
		panel.add(txtLName);
		txtLName.setColumns(10);
		txtFName = new JTextField();
		txtFName.setColumns(10);
		txtFName.setBounds(113, 24, 162, 26);
		panel.add(txtFName);
		txtAge = new JTextField();
		txtAge.setColumns(10);
		txtAge.setBounds(113, 101, 162, 26);
		panel.add(txtAge);

		JLabel lblBloodtype = new JLabel("BloodType");
		lblBloodtype.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBloodtype.setBounds(10, 137, 93, 26);
		panel.add(lblBloodtype);

		cmbBloodType = new JComboBox<String>();
		cmbBloodType.setModel(
				new DefaultComboBoxModel<String>(new String[] { "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-" }));
		cmbBloodType.setToolTipText("");
		cmbBloodType.setBounds(113, 141, 82, 22);
		cmbBloodType.addItem("A+");
		cmbBloodType.addItem("A-");
		cmbBloodType.addItem("B+");
		cmbBloodType.addItem("B-");
		cmbBloodType.addItem("O+");
		cmbBloodType.addItem("O-");
		cmbBloodType.addItem("AB+");
		cmbBloodType.addItem("AB-");
		panel.add(cmbBloodType);

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String fname, lname, age;
				int bloodTypeId = 0;
				fname = txtFName.getText();
				lname = txtLName.getText();
				age = txtAge.getText();
				try {
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("A+")) {
						bloodTypeId = 1;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("A-")) {
						bloodTypeId = 2;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("B+")) {
						bloodTypeId = 3;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("B-")) {
						bloodTypeId = 4;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("O+")) {
						bloodTypeId = 5;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("O-")) {
						bloodTypeId = 6;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("AB+")) {
						bloodTypeId = 7;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("AB-")) {
						bloodTypeId = 8;
					}
					pst = con.prepareStatement(																	//Insert donor details in Donor table									
							"insert into donor(firstName, lastName , age , bloodTypeId)values(?,?,?,?)");		//Use of prepareStatement helps prevent the issue of SQL Injection
					pst.setString(1, fname);
					pst.setString(2, lname);
					pst.setString(3, age);
					pst.setInt(4, bloodTypeId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Inserting Record " + txDonorID.getText() + "!");

					table_load();
					txtFName.setText("");
					txtLName.setText("");
					txtAge.setText("");
					txtFName.requestFocus();
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
				}
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInsert.setBounds(44, 317, 91, 37);
		frmBloodBankApp.getContentPane().add(btnInsert);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(369, 108, 480, 187);
		frmBloodBankApp.getContentPane().add(scrollPane_1);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search Donor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(38, 381, 305, 65);
		frmBloodBankApp.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txDonorID = new JTextField();
		txDonorID.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					String id = txDonorID.getText();																			
					pst = con.prepareStatement("select firstName,lastName,age from donor where donorId = ?");	//Use of prepareStatement helps prevent the issue of SQL Injection
					pst.setString(1, id);																		//Select donor details from Donor table based on searched donorid
					ResultSet rs = pst.executeQuery();
					if (rs.next() == true) {
						String name = rs.getString(1);

						String edition = rs.getString(2);
						String price = rs.getString(3);
						txtFName.setText(name);
						txtLName.setText(edition);
						txtAge.setText(price);
					} else {
						txtFName.setText("");
						txtLName.setText("");
						txtAge.setText("");
						txDonorID.requestFocus();
					}
				} catch (SQLException ex) {
					System.err.println(ex.getMessage());
				}
			}
		});
		txDonorID.setColumns(10);
		txDonorID.setBounds(82, 28, 167, 26);
		panel_1.add(txDonorID);
		JLabel lblBookId = new JLabel("Donor ID");
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBookId.setBounds(10, 26, 93, 26);
		panel_1.add(lblBookId);
//Update method
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dId, fname, lname, age;
				int bloodTypeId = 0;
				dId = txDonorID.getText();
				fname = txtFName.getText();
				lname = txtLName.getText();
				age = txtAge.getText();
				try {
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("A+")) {
						bloodTypeId = 1;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("A-")) {
						bloodTypeId = 2;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("B+")) {
						bloodTypeId = 3;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("B-")) {
						bloodTypeId = 4;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("O+")) {
						bloodTypeId = 5;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("O-")) {
						bloodTypeId = 6;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("AB+")) {
						bloodTypeId = 7;
					}
					if (cmbBloodType.getSelectedItem().toString().equalsIgnoreCase("AB-")) {
						bloodTypeId = 8;
					}

					pst = con.prepareStatement(
							"update donor set firstName=?,lastName=?,age=?, bloodTypeId=? where donorId =?");	//Use of prepareStatement helps prevent the issue of SQL Injection
					pst.setString(1, fname);																	//Update donor details in Donor table
					pst.setString(2, lname);
					pst.setString(3, age);
					pst.setInt(4, bloodTypeId);
					pst.setString(5, dId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Updating Record " + txDonorID.getText() + "!");
					table_load();
					txtFName.setText("");
					txtLName.setText("");
					txtAge.setText("");
					txDonorID.setText("");

					txDonorID.requestFocus();
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(145, 317, 89, 37);
		frmBloodBankApp.getContentPane().add(btnUpdate);
//Delete Method
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dId;
				dId = txDonorID.getText();
				try {
					pst = con.prepareStatement("delete from donor where donorId =?");						//Use of prepareStatement helps prevent the issue of SQL Injection
					pst.setString(1, dId);																	//Delete donor details from Donor table based on donorId
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Deleting Record " + txDonorID.getText() + "!");
					table_load();
					txtFName.setText("");
					txtLName.setText("");
					txtAge.setText("");
					txDonorID.setText("");
					txtFName.requestFocus();
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(246, 317, 89, 37);
		frmBloodBankApp.getContentPane().add(btnDelete);
		JLabel lblBookTableRecords = new JLabel("Donor Records");
		lblBookTableRecords.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBookTableRecords.setBounds(400, 58, 225, 48);
		frmBloodBankApp.getContentPane().add(lblBookTableRecords);

		JButton btnDonationPage = new JButton("Show Donations");
		btnDonationPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodBankApp.dispose();
				DonationInfo donationInfo = new DonationInfo();
				donationInfo.getFrmDonationPage().setVisible(true);
			}
		});
		btnDonationPage.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDonationPage.setBounds(396, 317, 170, 37);
		frmBloodBankApp.getContentPane().add(btnDonationPage);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodBankApp.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(758, 397, 89, 37);
		frmBloodBankApp.getContentPane().add(btnExit);

		JButton btnShowBloodbanks = new JButton("Show Bloodbanks");
		btnShowBloodbanks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodBankApp.dispose();
				BloodbankInfo bloodbankInfo = new BloodbankInfo();
				bloodbankInfo.getFrmBloodbankInfo().setVisible(true);
			}
		});
		btnShowBloodbanks.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnShowBloodbanks.setBounds(649, 317, 170, 37);
		frmBloodBankApp.getContentPane().add(btnShowBloodbanks);

		JButton btnDonateNow = new JButton("Donate Now");
		btnDonateNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBloodBankApp.setVisible(false);
				Donation donation = new Donation();
				donation.getFrmDonationPage().setVisible(true);
			}
		});
		btnDonateNow.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDonateNow.setBounds(396, 397, 170, 37);
		frmBloodBankApp.getContentPane().add(btnDonateNow);
	}
}