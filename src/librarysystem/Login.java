package librarysystem;

import java.awt.EventQueue;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Login {
	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String role = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void dbConnect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem", "root", "");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String SorT() {
		return role;
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		dbConnect();
		try {
			query = "select * from libraryuser";
			rs = stmt.executeQuery(query);
			rs.first();
			// conn.close();
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("LOGIN");
		frame.setBounds(100, 100, 451, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLoigin = new JLabel("LOGIN");
		lblLoigin.setBounds(182, 10, 61, 40);
		lblLoigin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(lblLoigin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(98, 64, 54, 13);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(162, 62, 156, 19);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(98, 97, 54, 13);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(162, 95, 156, 19);
		frame.getContentPane().add(txtPassword);
		
		ButtonGroup rdoGroupRender = new ButtonGroup();
		
		JCheckBox chkShowPassword = new JCheckBox("Show Password");
		chkShowPassword.setBounds(206, 120, 123, 21);
		chkShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkShowPassword.isSelected()) {
					txtPassword.setEchoChar((char) 0);
				} else {
					txtPassword.setEchoChar('*');
				}
			}
		});
		frame.getContentPane().add(chkShowPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(117, 175, 96, 23);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					 query = "select * from libraryuser where password='" + txtPassword.getText() + "'" + "and username='" +
							 txtUsername.getText() + "'";
					 rs = stmt.executeQuery(query);
					 if(rs.next()) {
						 role += rs.getString("role");
						 LibraryNavigation lb = new LibraryNavigation();
						 lb.setVisible(true);
						 frame.dispose();
					 } else {
						 JOptionPane.showMessageDialog(null, "username and/or password does not match...");
					 }
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnLogin);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(223, 175, 85, 23);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText("");
				txtPassword.setText("");
				chkShowPassword.setSelected(false);
				txtPassword.setEchoChar('*');
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.getContentPane().add(btnClear);
	}
}
