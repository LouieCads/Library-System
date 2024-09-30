package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class BookList extends JFrame {
    private static final long serialVersionUID = 1L;
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static String query;
    
    JTable tableBookList;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BookList window = new BookList();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public BookList() {
    	setResizable(false);
        initialize();
        dbConnect();
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
        setTitle("BOOK LIST");
        setBounds(100, 100, 800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Book List");
        lblTitle.setFont(new Font("Arial Black", Font.BOLD, 24));
        titlePanel.add(lblTitle);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Create table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        getContentPane().add(tablePanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBack.addActionListener(e -> {
            LibraryNavigation libraryNavigation = new LibraryNavigation();
            libraryNavigation.setVisible(true);
            dispose();
        });
        buttonPanel.add(btnBack);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        showBookList(tablePanel);
    }

    public void showBookList(JPanel tablePanel) {
        String title, ISBN, category, author, copyright, publisher, status;

        try {
            dbConnect();
            query = "SELECT * FROM booklist";
            rs = stmt.executeQuery(query);
            
            // Headers for the table
            String[] columns = {"Title", "ISBN", "Category", "Author", "Copyright", "Publisher", "Status"};
            String[][] row = new String[15][7]; // Adjust size as needed

            int i = 0;
            while(rs.next() && i < row.length) {
                title = rs.getString("title");
                ISBN = rs.getString("ISBN");
                category = rs.getString("category");
                author = rs.getString("author");
                copyright = rs.getString("copyright");
                publisher = rs.getString("publisher");
                status = rs.getString("status");

                row[i][0] = title;
                row[i][1] = ISBN;
                row[i][2] = category;
                row[i][3] = author;
                row[i][4] = copyright;
                row[i][5] = publisher;
                row[i][6] = status;
                i++;
            }
            tableBookList = new JTable(row, columns);
            JScrollPane scrollPane = new JScrollPane(tableBookList);
            tablePanel.add(scrollPane, BorderLayout.CENTER);

        } catch(SQLException e1) {
            e1.printStackTrace();
        }
    }
}
