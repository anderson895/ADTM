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

public class ATMRegister extends JFrame {
    JTextField tfAccountNo = new JTextField();
    JTextField tfAccountName = new JTextField();
    JPasswordField pfPin = new JPasswordField();
    JButton btnRegister = new JButton("Register");
    JButton btnBack = new JButton("Back to Login"); // New button

    public ATMRegister() {
        setTitle("ATM Registration");
        setLayout(null);

        JLabel lbl1 = new JLabel("Account No:");
        JLabel lbl2 = new JLabel("Account Name:");
        JLabel lbl3 = new JLabel("PIN:");

        lbl1.setBounds(20, 20, 100, 25);
        tfAccountNo.setBounds(130, 20, 150, 25);

        lbl2.setBounds(20, 60, 100, 25);
        tfAccountName.setBounds(130, 60, 150, 25);

        lbl3.setBounds(20, 100, 100, 25);
        pfPin.setBounds(130, 100, 150, 25);

        btnRegister.setBounds(40, 140, 120, 30);
        btnBack.setBounds(170, 140, 120, 30); // Position beside Register

        add(lbl1); add(tfAccountNo);
        add(lbl2); add(tfAccountName);
        add(lbl3); add(pfPin);
        add(btnRegister); add(btnBack);

        setSize(320, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setVisible(true);

        btnRegister.addActionListener(e -> registerAccount());
        btnBack.addActionListener(e -> goBackToLogin());
    }

    private void registerAccount() {
        String accNo = tfAccountNo.getText();
        String accName = tfAccountName.getText();
        String pin = String.valueOf(pfPin.getPassword());

        if (accNo.isEmpty() || accName.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        try (Connection con = DBConnection.connect()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO accounts(account_no, account_name, pin) VALUES (?, ?, ?)");
            ps.setString(1, accNo);
            ps.setString(2, accName);
            ps.setString(3, pin);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account Registered!");
            dispose();
            new ATMLogin(); // Return to login after registration
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void goBackToLogin() {
        dispose();      // Close this registration form
        new ATMLogin(); // Open login form
    }
}
