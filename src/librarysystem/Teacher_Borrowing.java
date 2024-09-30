package librarysystem;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.*;


import javax.swing.*;
import java.sql.*;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Teacher_Borrowing {
	private JFrame frmBookBorrowing;
	static Connection conn;
	static Statement stmt;
	static Statement stmt2;
	static Statement stmt3;
	static Statement stmt4;
	private JTextField bISBNInput;
	static ResultSet rs;
	static int rs2;
	static ResultSet rs3;
	static int rs4;
	static String query;
	static String query2;
	static String query3;
	static String query4;
	static String success;
	private JTable table;
	String buttonSearch = "Search Book";
	static String role = Login.SorT();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teacher_Borrowing window = new Teacher_Borrowing(); 
					window.frmBookBorrowing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Teacher_Borrowing() {
		initialize();
		dbConnect();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    frmBookBorrowing = new JFrame();
	    frmBookBorrowing.setTitle("Teacher Borrow Books");
	    frmBookBorrowing.setBounds(100, 100, 848, 445);
	    frmBookBorrowing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmBookBorrowing.getContentPane().setLayout(null);
	    
	    JLabel lblNewLabel = new JLabel("Teacher Borrow Books");
	    lblNewLabel.setBounds(250, 32, 351, 43);
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    frmBookBorrowing.getContentPane().add(lblNewLabel);
	    
	    JLabel bISBN = new JLabel("Book ISBN:");
	    bISBN.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    bISBN.setBounds(385, 100, 89, 21);
	    frmBookBorrowing.getContentPane().add(bISBN);
	    
	    bISBNInput = new JTextField();
	    bISBNInput.setHorizontalAlignment(SwingConstants.CENTER);
	    bISBN.setLabelFor(bISBNInput);
	    bISBNInput.setColumns(10);
	    bISBNInput.setBounds(342, 130, 163, 20);
	    frmBookBorrowing.getContentPane().add(bISBNInput);
	    
	    JButton btnBack = new JButton("Reset");
	    btnBack.setBounds(307, 340, 118, 34);
	    frmBookBorrowing.getContentPane().add(btnBack); 
	    
	    JButton btnBorrow = new JButton("Search Book");
	    btnBorrow.setFont(new Font("Tahoma", Font.BOLD, 11));
	    btnBorrow.setBounds(438, 340, 118, 34);
	    frmBookBorrowing.getContentPane().add(btnBorrow);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 200, 812, 109);
	    frmBookBorrowing.getContentPane().add(scrollPane);
	    
	    table = new JTable();
	    
	    scrollPane.setViewportView(table);
	    DefaultTableModel model = new DefaultTableModel(
	            new Object[][] {
	            },
	            new String[] {
	                "Title", "Category", "Author", "Copyright", "Publisher"
	            }
	        );
	    table.setModel(model);
	    
	    table.getColumnModel().getColumn(0).setPreferredWidth(107);
	    table.getColumnModel().getColumn(1).setPreferredWidth(104);
	    table.getColumnModel().getColumn(2).setPreferredWidth(120);
	    table.getColumnModel().getColumn(3).setPreferredWidth(120);
	    table.getColumnModel().getColumn(4).setPreferredWidth(116);
	    
	    btnBorrow.addActionListener(new ActionListener() {
	        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String name = Teacher_Details.Name();
	            String employeeID = Teacher_Details.employeeID();
	            String department = Teacher_Details.department_();
	            
	            Object[] object = {"Return", "confirm"}; 
//	          success = "Name: \n" + name + "\n\nIBSN: \n" + bISBNInput.getText();
	            
	             if(btnBorrow.getText().equals("Search Book")) {
	                    try {
	                        query = "SELECT COUNT(*) FROM borrowed where name='" + name + "' && role='" + role + "'";
	                        rs = stmt.executeQuery(query);
	                        if (rs.next()) {
	                            int count = rs.getInt(1);
	                            if (count >= 5) {
	                                JOptionPane.showMessageDialog(frmBookBorrowing, "You Exceed Your Book Borrowing Limit (5 Books Only to Borrow)");
	                            }
	                        }

	                    } catch (SQLException el) {
	                        el.printStackTrace();
	                    }   

	                    try {
	                        query = "SELECT * FROM booklist WHERE ISBN='" + bISBNInput.getText() + "'";
	                        rs = stmt.executeQuery(query);
	                        if (rs.next()) {
	                            btnBorrow.setText("Borrow Book");

	                           String title = rs.getString("title");
	                           String category = rs.getString("category");
	                           String author = rs.getString("author");
	                           String copyright = rs.getString("copyright");
	                           String publisher = rs.getString("publisher");

	                            model.setRowCount(0);
	                            model.addRow(new Object[]{title, category, author, copyright, publisher});
	                            table.setRowHeight(60);
	                        } else {
	                            JOptionPane.showMessageDialog(frmBookBorrowing, "No Matching IBSN, No Book");
	                        }
	                    } catch (SQLException el) {
	                        el.printStackTrace();
	                    }
	                } else if(btnBorrow.getText().equals("Borrow Book")) {
	                    try {
	                    query = "SELECT * FROM booklist WHERE ISBN='" + bISBNInput.getText() + "'";
	                        rs = stmt.executeQuery(query);

	                    if (rs.next()) {
	                        
	                            try {
	                                query = "SELECT COUNT(*) FROM borrowed where name='" + name + "' && role='" + role + "'";
	                                rs = stmt.executeQuery(query);
	                                if (rs.next()) {
	                                    int count = rs.getInt(1);
	                                    if (count >= 5) {
	                                        JOptionPane.showMessageDialog(frmBookBorrowing, "You Exceed Your Book Borrowing Limit (5 Books Only to Borrow)");
	                                    }
	                                    else {
	                                        try {
	                                            LocalDate today = LocalDate.now();
	                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	                                            String todayAsString = today.format(formatter);
	                                            query3 = "SELECT * FROM booklist WHERE ISBN='" + bISBNInput.getText() + "'";
	                                            rs3 = stmt.executeQuery(query3);
	                                            
	                                            if (rs3.next()) {
	                                                String category = rs3.getString("category");
	                                            
	                                                if (category.equals("Fictional") || category.equals("Non-Fictional")) {

	                                                    query4 = "SELECT COUNT(*) FROM booklist WHERE status='borrowed' && ISBN='" + bISBNInput.getText() + "'";
	                                                    rs3 = stmt.executeQuery(query4);

	                                                    if (rs3.next()) {
	                                                        int count_status = rs3.getInt(1);
	                                                        if (count_status == 1) {
	                                                            JOptionPane.showMessageDialog(frmBookBorrowing, "Already Borrowed");
	                                                        }
	                                                        else {
	                                                            try {
																	query2 = String.format("INSERT INTO borrowed (name, ISBN, date_borrowed, role) VALUES ('%s', '%s', '%s', '%s')", name, bISBNInput.getText(), todayAsString, role);
	                                                                query3 = String.format("UPDATE booklist SET status='%s' WHERE ISBN='%s'", "Borrowed", bISBNInput.getText());
	                                                                rs2 = stmt.executeUpdate(query2);
	                                                                rs4 = stmt.executeUpdate(query3);
	                                                                JOptionPane.showMessageDialog(frmBookBorrowing, "You borrowed this in: \n" + todayAsString);
	                                                                if (rs2 > 0 && rs4 > 0) {
	                                                                    JOptionPane.showMessageDialog(frmBookBorrowing, "Success!");
	                                                                }
	                                                                else {
	                                                                    JOptionPane.showMessageDialog(frmBookBorrowing, "Unsucessful");
	                                                                }
	                                                            } catch (SQLException el) {
	                                                                el.printStackTrace();
	                                                            }
	                                                        }
	                                                    }
	                                                    
	                                                    
	                
	                                                } else {
	                                                    JOptionPane.showMessageDialog(frmBookBorrowing, "Only Fictional or Non-Fictional is allowed to be borrowed");
	                                                }
	                                            }
	                                            
	                                        } catch (SQLException e1) {
	                                            // TODO Auto-generated catch block
	                                            e1.printStackTrace();
	                                        }
	                                    }
	                                }
	        
	                            } catch (SQLException el) {
	                                el.printStackTrace();
	                            }   
	                            
	                        }
	                        
	                     else {
	                        JOptionPane.showMessageDialog(frmBookBorrowing, "NONE EXISTING ISBN! NO BOOK FOUND!", "Invalid!", JOptionPane.ERROR_MESSAGE);
	                    }
	                } catch (Exception el) {
	                    el.printStackTrace();
	                }

	                    
	                }
	        }
	    });
	    
	    btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {

	            bISBNInput.setText(""); 
	       
	            model.setRowCount(0);
	            table.clearSelection();
	       
	            btnBorrow.setText(buttonSearch);
	        }
	    });
	}
		
		

	
	public void showFrame() {
        frmBookBorrowing.setVisible(true); // call it sa main java file and set it to false
    }
	
	public static void dbConnect() {
		 try {
	         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem", "root", "");
	         stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	      }
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}
