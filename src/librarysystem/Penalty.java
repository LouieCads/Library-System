package librarysystem;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Penalty implements ActionListener {

    private JFrame frame;
    private JTextField txtAmountPaid;
    private JLabel lblDaysLate;
    private JLabel lblAmountDue;
    private JLabel lblChange;
    private JButton btnPay, btnClose, btnClear;
    private double penaltyPerDay = 20.0; // Penalty rate per day for late returns
    private double balance;
    private int userId = 1; // Example user ID
    private String userName = "John Doe"; // Example user name
    private LocalDate dateDue;
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static String query;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Penalty window = new Penalty();
                window.frame.setVisible(true);
                window.calculatePenalty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Penalty() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Penalty Form");
        frame.setBounds(200, 200, 550, 400); // Adjusted window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(40, 40, 40)); // Set background color to the same color as LibraryNavigation

        lblDaysLate = new JLabel("Days Late: 0");
        lblDaysLate.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        lblDaysLate.setForeground(java.awt.Color.WHITE); // Set text color to white
        lblDaysLate.setBounds(30, 30, 300, 30); // Adjusted component size
        frame.getContentPane().add(lblDaysLate);

        lblAmountDue = new JLabel("Amount Due: Php 0.00");
        lblAmountDue.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        lblAmountDue.setForeground(java.awt.Color.WHITE); // Set text color to white
        lblAmountDue.setBounds(30, 70, 300, 30); // Adjusted component size
        frame.getContentPane().add(lblAmountDue);

        JLabel lblAmountPaid = new JLabel("Payment:");
        lblAmountPaid.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        lblAmountPaid.setForeground(java.awt.Color.WHITE); // Set text color to white
        lblAmountPaid.setBounds(30, 110, 150, 30); // Adjusted component size
        frame.getContentPane().add(lblAmountPaid);

        txtAmountPaid = new JTextField();
        txtAmountPaid.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        txtAmountPaid.setColumns(10);
        txtAmountPaid.setBounds(190, 110, 200, 30); // Adjusted component size
        frame.getContentPane().add(txtAmountPaid);

        lblChange = new JLabel("Change: Php 0.00");
        lblChange.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        lblChange.setForeground(java.awt.Color.WHITE); // Set text color to white
        lblChange.setBounds(30, 150, 300, 30); // Adjusted component size
        frame.getContentPane().add(lblChange);

        btnPay = new JButton("Pay");
        btnPay.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        btnPay.setBounds(30, 190, 100, 40); // Adjusted component size
        frame.getContentPane().add(btnPay);

        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        btnClear.setBounds(140, 190, 100, 40); // Adjusted component size
        frame.getContentPane().add(btnClear);

        btnClose = new JButton("Back");
        btnClose.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Adjusted font size
        btnClose.setBounds(250, 190, 100, 40); // Adjusted component size
        frame.getContentPane().add(btnClose);

        btnPay.addActionListener(this);
        btnClear.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPay) {
            try {
                double amountPaid = Double.parseDouble(txtAmountPaid.getText());
                double change = amountPaid - balance;
                if (change < 0) {
                    JOptionPane.showMessageDialog(null, "Insufficient amount paid. Balance due: Php" + String.format("%.2f", -change));
                } else {
                    JOptionPane.showMessageDialog(null, "Payment successful. Change: Php" + String.format("%.2f", change));
                    lblChange.setText("Change: Php" + String.format("%.2f", change));
                    balance = 0;
                    updatePaymentStatus(); // Update the payment status in the database
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount paid.");
            }
        } else if (e.getSource() == btnClear) {
            txtAmountPaid.setText(null);
            lblDaysLate.setText("Days Late: 0");
            lblAmountDue.setText("Amount Due: Php 0.00");
            lblChange.setText("Change: Php 0.00");
            txtAmountPaid.requestFocus();
        } else if (e.getSource() == btnClose) {
            frame.dispose();
            LibraryNavigation libraryNavigation = new LibraryNavigation();
            libraryNavigation.setVisible(true);
        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    // Fetch penalty details from the database
    public void calculatePenalty() {
        dbConnect();
        try {
            // Example query to fetch the due date for a specific user
            query = "SELECT date_due FROM borrowed WHERE name = '" + userName + "' AND paymentstatus = 'unpaid'";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                dateDue = LocalDate.parse(rs.getString("date_due"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Simulate that the book is 3 days late
                LocalDate currentDate = dateDue.plusDays(3); // Simulating that today's date is 3 days after the due date
                long daysLate = ChronoUnit.DAYS.between(dateDue, currentDate);
                daysLate = Math.max(0, daysLate); // Ensure days late is not negative
                balance = daysLate * penaltyPerDay;

                lblDaysLate.setText("Days Late: " + daysLate);
                lblAmountDue.setText("Amount Due: Php " + String.format("%.2f", balance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update the payment status in the database
    private void updatePaymentStatus() {
        dbConnect();
        try {
            String updateQuery = "UPDATE borrowed SET paymentstatus = 'paid' WHERE name = ? AND paymentstatus = 'unpaid'";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, userName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Payment status updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No records updated. Please check the user name and payment status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Establish a database connection
    public void dbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarysystem", "root", "");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}