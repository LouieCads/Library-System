package librarysystem;

import java.awt.*;
import java.awt.EventQueue;
import javax.swing.*;
import java.sql.*;


public class LibraryNavigation extends JFrame {
	static String role;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LibraryNavigation window = new LibraryNavigation();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public LibraryNavigation() {
    	setResizable(false);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Library Navigation");
        setBounds(100, 100, 600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));
        getContentPane().setLayout(new GridBagLayout());

        // Title Label
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblTitle = new JLabel("Library");
        lblTitle.setFont(new Font("Arial Black", Font.BOLD, 40));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        getContentPane().add(lblTitle, gbc);

        // Book List Button
        JButton btnBookList = new JButton("Book List");
        styleButton(btnBookList);
        btnBookList.addActionListener(e -> {
            BookList bl = new BookList();
            bl.setVisible(true);
            dispose();
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(btnBookList, gbc);

        // Borrower List Button
        JButton btnBorrowerList = new JButton("Borrower List");
        styleButton(btnBorrowerList);
        btnBorrowerList.addActionListener(e -> {
            BorrowerList bl = new BorrowerList();
            bl.setVisible(true);
            dispose();
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(btnBorrowerList, gbc);

        // Book Borrowing Button
        JButton btnBookBorrowing = new JButton("Book Borrowing");
        styleButton(btnBookBorrowing);
        btnBookBorrowing.addActionListener(e -> {
        	
        	String userRole = Login.SorT();

        	if ("Student".equals(userRole)) {
            	Student_Details sd = new Student_Details();
            	sd.setVisible(true);
            	dispose();
            } else if ("Teacher".equals(userRole)) {
            	Teacher_Details td = new Teacher_Details();
            	td.setVisible(true);
            	dispose();
            } else {
            	JOptionPane.showMessageDialog(null, "Not Student and Not Teacher");
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(btnBookBorrowing, gbc);

        // Book Returning Button
        JButton btnBookReturning = new JButton("Book Returning");
        styleButton(btnBookReturning);
        btnBookReturning.addActionListener(e -> {
            // Code Here
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(btnBookReturning, gbc);

        // Penalty Button
        JButton btnPenalty = new JButton("Penalty");
        styleButton(btnPenalty);
        btnPenalty.addActionListener(e -> {
            Penalty p = new Penalty();
            p.setVisible(true);
            dispose();
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(btnPenalty, gbc);

        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}