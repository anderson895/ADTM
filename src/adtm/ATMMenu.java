/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adtm;

/**
 *
 * @author Desktop
 */
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ATMMenu extends JFrame {
    int userId;
    String userName;

    JLabel lblWelcome = new JLabel();
    JButton btnCheck = new JButton("Check Balance");
    JButton btnDeposit = new JButton("Deposit");
    JButton btnWithdraw = new JButton("Withdraw");
    JButton btnExit = new JButton("Logout");

    public ATMMenu(int id, String name) {
        userId = id;
        userName = name;

        setTitle("ATM Menu");
        setLayout(null);
        lblWelcome.setText("Welcome, " + userName);

        lblWelcome.setBounds(50, 20, 200, 30);
        btnCheck.setBounds(80, 60, 140, 30);
        btnDeposit.setBounds(80, 100, 140, 30);
        btnWithdraw.setBounds(80, 140, 140, 30);
        btnExit.setBounds(80, 180, 140, 30);

        add(lblWelcome);
        add(btnCheck);
        add(btnDeposit);
        add(btnWithdraw);
        add(btnExit);

        setSize(300, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnCheck.addActionListener(e -> checkBalance());
        btnDeposit.addActionListener(e -> deposit());
        btnWithdraw.addActionListener(e -> withdraw());

        // Updated Exit Button to Logout
        btnExit.addActionListener(e -> {
            dispose(); // Close ATMMenu window
            new ATMLogin(); // Go back to login screen
        });
    }

    private void checkBalance() {
        try (Connection con = DBConnection.connect()) {
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Balance: ₱" + rs.getDouble("balance"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deposit() {
        String amtStr = JOptionPane.showInputDialog("Enter deposit amount:");
        try {
            double amount = Double.parseDouble(amtStr);
            if (amount <= 0) throw new Exception("Invalid amount");

            try (Connection con = DBConnection.connect()) {
                PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
                ps.setDouble(1, amount);
                ps.setInt(2, userId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Deposited ₱" + amount);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void withdraw() {
        String amtStr = JOptionPane.showInputDialog("Enter withdraw amount:");
        try {
            double amount = Double.parseDouble(amtStr);
            if (amount <= 0) throw new Exception("Invalid amount");

            try (Connection con = DBConnection.connect()) {
                PreparedStatement check = con.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
                check.setInt(1, userId);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getDouble("balance") >= amount) {
                    PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
                    ps.setDouble(1, amount);
                    ps.setInt(2, userId);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Withdrew ₱" + amount);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient funds.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
