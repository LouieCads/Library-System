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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.*;

public class Student_Details extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtStudentName;
    private JTextField txtStudentID;
    private JTextField txtYearLevel;
    private JTextField txtSection;
    static Connection conn;
    static String studentName = "";
    static String studentID = "";
    static String yearLevel = "";
    static String section_ = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	EventQueue.invokeLater(() -> {
            try {
                Student_Details window = new Student_Details();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Student_Details() {
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
    	return studentName;
    }
    
    public static String Student_ID() {
    	return studentID;
    }
    
    public static String year_level() {
    	return yearLevel;
    }
    public static String _section() {
    	return section_;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 1200, 817);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel title = new JLabel("Student's Details");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 50));
        title.setBounds(0, 11, 1184, 98);
        getContentPane().add(title);

        JLabel name = new JLabel("Student's Name\r\n");
        name.setFont(new Font("Tahoma", Font.PLAIN, 18));
        name.setHorizontalAlignment(SwingConstants.LEFT);
        name.setBounds(69, 120, 150, 40);
        getContentPane().add(name);

        txtStudentName = new JTextField();
        txtStudentName.setBounds(69, 156, 1034, 47);
        getContentPane().add(txtStudentName);
        txtStudentName.setColumns(10);

        JLabel studID = new JLabel("Student ID:");
        studID.setHorizontalAlignment(SwingConstants.LEFT);
        studID.setFont(new Font("Tahoma", Font.PLAIN, 18));
        studID.setBounds(69, 250, 150, 40);
        getContentPane().add(studID);

        txtStudentID = new JTextField();
        txtStudentID.setColumns(10);
        txtStudentID.setBounds(69, 288, 1034, 47);
        getContentPane().add(txtStudentID);

        JLabel yearlvl = new JLabel("Year Level:");
        yearlvl.setHorizontalAlignment(SwingConstants.LEFT);
        yearlvl.setFont(new Font("Tahoma", Font.PLAIN, 18));
        yearlvl.setBounds(69, 388, 150, 40);
        getContentPane().add(yearlvl);

        txtYearLevel = new JTextField();
        txtYearLevel.setColumns(10);
        txtYearLevel.setBounds(69, 424, 1034, 47);
        getContentPane().add(txtYearLevel);

        JLabel section = new JLabel("Section:");
        section.setHorizontalAlignment(SwingConstants.LEFT);
        section.setFont(new Font("Tahoma", Font.PLAIN, 18));
        section.setBounds(69, 520, 150, 40);
        getContentPane().add(section);

        txtSection = new JTextField();
        txtSection.setColumns(10);
        txtSection.setBounds(69, 557, 1034, 47);
        getContentPane().add(txtSection);

        JButton confirmbtn = new JButton("Confirm");
        confirmbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String name = txtStudentName.getText().trim();
                String id = txtStudentID.getText().trim();
                String level = txtYearLevel.getText().trim();
                String section = txtSection.getText().trim();

                if (name.isEmpty() || id.isEmpty() || level.isEmpty() || section.isEmpty()) {
                    // Prompt user for invalid input
                    JOptionPane.showMessageDialog(null, "Invalid input. Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                	try {
                        studentName += txtStudentName.getText();
                        studentID += txtStudentID.getText();
                        yearLevel += txtYearLevel.getText();
                        section_ += txtSection.getText();

                        // Inserting data into the database
                        String query = "INSERT INTO borrowerlist_student (student_name, student_id, student_yearlevel, student_section) VALUES (?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(query);
                        pstmt.setString(1, studentName);
                        pstmt.setString(2, studentID);
                        pstmt.setString(3, yearLevel);
                        pstmt.setString(4, section_);
                        pstmt.executeUpdate();
                        pstmt.close();

                        // Clearing text fields after insertion
                        txtStudentName.setText("");
                        txtStudentID.setText("");
                        txtYearLevel.setText("");
                        txtSection.setText("");
                        
                        Student_Borrowing sb = new Student_Borrowing();
                    	sb.showFrame();
                    	dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        confirmbtn.setFont(new Font("Arial Black", Font.PLAIN, 18));
        confirmbtn.setBounds(491, 687, 188, 40);
        getContentPane().add(confirmbtn);
    }
}