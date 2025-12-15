package gui;

import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLayout(null);

        JLabel label = new JLabel("Welcome, Administrator");
        label.setBounds(100, 50, 200, 25);
        add(label);

        setVisible(true);
    }
}
