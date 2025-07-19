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

public class ATMLogin extends JFrame {
    JTextField tfAccountNo = new JTextField();
    JPasswordField pfPin = new JPasswordField();
    JButton btnLogin = new JButton("Login");
    JButton btnRegister = new JButton("Register");  // New Register button

    public ATMLogin() {
        setTitle("ATM Login");
        setLayout(null);

        JLabel lbl1 = new JLabel("Account No:");
        JLabel lbl2 = new JLabel("PIN:");

        lbl1.setBounds(20, 30, 100, 25);
        tfAccountNo.setBounds(130, 30, 150, 25);

        lbl2.setBounds(20, 70, 100, 25);
        pfPin.setBounds(130, 70, 150, 25);

        btnLogin.setBounds(50, 120, 100, 30);
        btnRegister.setBounds(160, 120, 100, 30);

        add(lbl1); add(tfAccountNo);
        add(lbl2); add(pfPin);
        add(btnLogin); add(btnRegister);

        setSize(330, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> openRegisterForm());
    }

    private void login() {
        String accNo = tfAccountNo.getText();
        String pin = String.valueOf(pfPin.getPassword());

        try (Connection con = DBConnection.connect()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM accounts WHERE account_no = ? AND pin = ?");
            ps.setString(1, accNo);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new ATMMenu(rs.getInt("id"), rs.getString("account_name"));
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void openRegisterForm() {
        new ATMRegister();  // Open registration
        dispose();          // Close login window
    }
}
