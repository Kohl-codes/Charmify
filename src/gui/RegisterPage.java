package gui;

import javax.swing.*;
import data.UserData;
import models.User;

public class RegisterPage extends JFrame {

    public RegisterPage() {
        setTitle("Charmify - Register");
        setSize(350, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 50, 100, 25);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(120, 50, 150, 25);
        add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 90, 100, 25);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(120, 90, 150, 25);
        add(txtPass);

        JButton registerBtn = new JButton("Submit");
        registerBtn.setBounds(120, 140, 150, 25);
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            String username = txtUser.getText();
            String password = String.valueOf(txtPass.getPassword());

            User newUser = new User(username, password, false);

            UserData.addUser(newUser);

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginPage();
            dispose();
        });

        setVisible(true);
    }
}
