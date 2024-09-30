package librarysystem;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Teacher_Details extends JFrame {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    static String teacherName = "";
    static String employeeID = "";
    static String department = "";
    static int inputCounter = 0; // Counter to keep track of inputs


    static Connection conn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Teacher_Details window = new Teacher_Details();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Teacher_Details() {
        initialize();
        dbConnect();
    }

    public static void dbConnect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String Name() {
    	return teacherName;
    }
    
    public static String employeeID() {
    	return employeeID;
    }
    
    public static String department_() {
    	return department;
    }
   

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 1200, 817);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel title = new JLabel("Teacher's Detail");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 50));
        title.setBounds(0, 11, 1184, 98);
        getContentPane().add(title);

        JLabel name = new JLabel("Teacher's Name:");
        name.setFont(new Font("Tahoma", Font.PLAIN, 18));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setBounds(69, 120, 150, 40);
        getContentPane().add(name);

        textField = new JTextField();
        textField.setBounds(69, 156, 1034, 47);
        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel studID = new JLabel("Employee ID:");
        studID.setHorizontalAlignment(SwingConstants.LEFT);
        studID.setFont(new Font("Tahoma", Font.PLAIN, 18));
        studID.setBounds(69, 250, 150, 40);
        getContentPane().add(studID);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(69, 288, 1034, 47);
        getContentPane().add(textField_1);

        JLabel yearlvl = new JLabel("Department:");
        yearlvl.setHorizontalAlignment(SwingConstants.LEFT);
        yearlvl.setFont(new Font("Tahoma", Font.PLAIN, 18));
        yearlvl.setBounds(69, 386, 150, 40);
        getContentPane().add(yearlvl);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(69, 424, 1034, 47);
        getContentPane().add(textField_2);

        JButton confirmbtn = new JButton("Confirm");
        confirmbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String name = textField.getText().trim();
                String id = textField_1.getText().trim();
                String dept = textField_2.getText().trim();

                if (name.isEmpty() || id.isEmpty() || dept.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (inputCounter < 3) { // Check if input limit is reached
                        inputCounter++; // Increment counter for each input

                        try {
                            teacherName += textField.getText();
                            employeeID += textField_1.getText();
                            department += textField_2.getText();

                            // Inserting data into the database
                            String query = "INSERT INTO borrowerlist_teacher (teacher_name, employee_id, teacher_department) VALUES (?, ?, ?)";
                            PreparedStatement pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, teacherName);
                            pstmt.setString(2, employeeID);
                            pstmt.setString(3, department);
                            pstmt.executeUpdate();
                            pstmt.close();

                            // Clearing text fields after insertion
                            
                            textField.setText("");
                            textField_1.setText("");
                            textField_2.setText("");
                            
                            Teacher_Borrowing tb = new Teacher_Borrowing();
                        	tb.showFrame();
                        	dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You can only input up to 3 entries.", "Input Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        confirmbtn.setFont(new Font("Arial Black", Font.PLAIN, 18));
        confirmbtn.setBounds(491, 687, 188, 40);
        getContentPane().add(confirmbtn);
    }
}