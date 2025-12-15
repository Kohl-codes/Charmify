package gui;

import javax.swing.*;
import java.awt.*;
import data.UserData;
import models.User;

public class LoginPage extends JFrame {

    public LoginPage() {

        setTitle("Charmify | Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Main panel (card style)
        JPanel panel = new JPanel();
        panel.setBounds(40, 30, 300, 260);
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(panel);

        // Title
        JLabel title = new JLabel("Charmify");
        title.setBounds(0, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title);

        // Username
        JLabel lblUser = new JLabel("Username");
        lblUser.setBounds(30, 70, 100, 20);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(30, 90, 240, 30);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtUser);

        // Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(30, 125, 100, 20);
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(30, 145, 240, 30);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtPass);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(30, 190, 240, 35);
        loginBtn.setFocusPainted(false);
        loginBtn.setBackground(new Color(92, 107, 192));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(loginBtn);

        // Register link-style button
        JButton registerBtn = new JButton("Create Account");
        registerBtn.setBounds(30, 230, 240, 25);
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setForeground(new Color(92, 107, 192));
        registerBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(registerBtn);

        // Actions
        loginBtn.addActionListener(e -> {
            String username = txtUser.getText();
            String password = String.valueOf(txtPass.getPassword());

            User user = UserData.authenticate(username, password);

            if (user != null) {
                if (user.isAdmin()) {
                    new AdminDashboard();
                } else {
                    new QuizPage(user);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        registerBtn.addActionListener(e -> {
            new RegisterPage();
            dispose();
        });

        setVisible(true);
    }
}
