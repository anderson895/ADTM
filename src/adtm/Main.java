// ATMLogin.java
package adtm;

import javax.swing.*;

public class Main extends JFrame {
    
    public Main() {
        setTitle("ATM Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the window

        // Add your login components here
        JLabel label = new JLabel("Welcome to ATM!");
        add(label);
    }
}
