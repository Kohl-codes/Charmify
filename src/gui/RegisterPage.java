package gui;

import data.UserData;
import java.awt.*;
import javax.swing.*;
import models.User;

public class RegisterPage extends JFrame {

    public RegisterPage() {
        setTitle("Charmify | Register");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(40, 30, 300, 260);
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(panel);

        JLabel title = new JLabel("Create Account");
        title.setBounds(0, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(title);

        JLabel lblUser = new JLabel("Username");
        lblUser.setBounds(30, 70, 100, 20);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(30, 90, 240, 30);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtUser);

        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(30, 125, 100, 20);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(30, 145, 240, 30);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtPass);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(30, 190, 240, 35);
        registerBtn.setFocusPainted(false);
        registerBtn.setBackground(new Color(92, 107, 192));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(registerBtn);

        JButton loginBtn = new JButton("Back to Login");
        loginBtn.setBounds(30, 230, 240, 25);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(new Color(92, 107, 192));
        loginBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(loginBtn);

        // Register Action
        registerBtn.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = String.valueOf(txtPass.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if username already exists
            boolean exists = UserData.getUsers().stream().anyMatch(u -> u.getUsername().equals(username));
            if (exists) {
                JOptionPane.showMessageDialog(this, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(username, password);
            UserData.addUser(newUser);

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginPage();
            dispose();
        });

        // Go back to Login
        loginBtn.addActionListener(e -> {
            new LoginPage();
            dispose();
        });

        setVisible(true);
    }
}
