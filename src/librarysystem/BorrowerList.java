package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BorrowerList extends JFrame {
    private static final long serialVersionUID = 1L;
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static String query;
    
    private JTable tableStudent;
    private JTable tableTeacher;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BorrowerList window = new BorrowerList();
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
    public BorrowerList() {
        setResizable(false);
        initialize();
        dbConnect();
        showStudent();
        showTeacher();
    }
    
    public static void dbConnect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem", "root", "");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 949, 545);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblStudent = new JLabel("Student");
        lblStudent.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStudent.setBounds(20, 67, 100, 25);
        getContentPane().add(lblStudent);
        
        JLabel lblTeacher = new JLabel("Teacher");
        lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTeacher.setBounds(20, 300, 100, 25);
        getContentPane().add(lblTeacher);
        
        JLabel lblBorrowerList = new JLabel("Borrower List");
        lblBorrowerList.setFont(new Font("Arial Black", Font.BOLD, 24));
        lblBorrowerList.setBounds(370, 10, 220, 40);
        getContentPane().add(lblBorrowerList);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 LibraryNavigation libraryNavigation = new LibraryNavigation();
                 libraryNavigation.setVisible(true);
                 dispose();
        	}
        });
        btnBack.setBounds(419, 450, 100, 30);
        getContentPane().add(btnBack);
    }
    
    public void showStudent() {
        String student_id, student_name, student_yearlevel, student_section;
        try {
            query = "SELECT * FROM borrowerlist_student";
            rs = stmt.executeQuery(query);
            
            String[] columns = {"Student ID", "Name", "Year Level", "Section"};
            String[][] row = new String[10][4];
            
            int i = 0;
            while(rs.next() && i < row.length) {
                student_id = rs.getString("student_id");
                student_name = rs.getString("student_name");
                student_yearlevel = rs.getString("student_yearlevel");
                student_section = rs.getString("student_section");

                row[i][0] = student_id;
                row[i][1] = student_name;
                row[i][2] = student_yearlevel;
                row[i][3] = student_section;
                i++;
            }
            tableStudent = new JTable(row, columns);
            JScrollPane scrollPane = new JScrollPane(tableStudent);
            scrollPane.setBounds(20, 100, 900, 150); // Set the bounds for the scroll pane
            getContentPane().add(scrollPane);
        } catch(SQLException e1) {
            e1.printStackTrace();
        }
    }
    
     public void showTeacher() {
         String teacher_id, teacher_name, teacher_department;
         try {
             query = "SELECT * FROM borrowerlist_teacher";
             rs = stmt.executeQuery(query);
             
             String[] columns = {"Employee ID", "Name", "Department"};
             String[][] row = new String[3][3];
             
             int i = 0;
             while(rs.next() && i < row.length) {
            	 teacher_id = rs.getString("employee_id");
            	 teacher_name = rs.getString("teacher_name");
            	 teacher_department = rs.getString("teacher_department");
                 row[i][0] = teacher_id;
                 row[i][1] = teacher_name;
                 row[i][2] = teacher_department;
                 i++;
             }
             tableTeacher = new JTable(row, columns);
             JScrollPane scrollPane = new JScrollPane(tableTeacher);
             scrollPane.setBounds(20, 330, 900, 70); // Set the bounds for the scroll pane
             getContentPane().add(scrollPane);
         } catch(SQLException e1) {
             e1.printStackTrace();
         }
     }
}