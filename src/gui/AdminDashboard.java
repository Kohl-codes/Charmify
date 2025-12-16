package gui;

import data.CharmTemplates;
import data.QuizData;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.Charm;
import models.QuizQuestion;
import models.User;

public class AdminDashboard extends JFrame {

    private DefaultListModel<User> userListModel;
    private DefaultListModel<Charm> charmListModel;
    private JPanel mainPanel;

    public AdminDashboard(ArrayList<User> users) {
        setTitle("Admin Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(1000, 70));
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // ===== Sidebar =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4, 1, 0, 20));
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton usersBtn = new JButton("Users");
        JButton charmsBtn = new JButton("Charms");
        JButton quizBtn = new JButton("Quiz");
        JButton logoutBtn = new JButton("Logout");

        Font sidebarFont = new Font("Segoe UI", Font.PLAIN, 16);
        for (JButton btn : new JButton[]{usersBtn, charmsBtn, quizBtn, logoutBtn}) {
            btn.setFont(sidebarFont);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(200, 200, 200));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);

        // ===== Main Panel =====
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // ===== Users Panel =====
        JPanel usersPanel = new JPanel(new BorderLayout(10, 10));
        JLabel userLabel = new JLabel("Users");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        usersPanel.add(userLabel, BorderLayout.NORTH);

        userListModel = new DefaultListModel<>();
        users.forEach(userListModel::addElement);
        JList<User> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usersPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        mainPanel.add(usersPanel, "Users");

        // ===== Charms Panel =====
        JPanel charmsPanel = new JPanel(new BorderLayout(10, 10));
        JLabel charmLabel = new JLabel("Charm Templates");
        charmLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        charmsPanel.add(charmLabel, BorderLayout.NORTH);

        charmListModel = new DefaultListModel<>();
        CharmTemplates.getAllCharms().forEach(charmListModel::addElement);
        JList<Charm> charmList = new JList<>(charmListModel);
        charmList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        charmsPanel.add(new JScrollPane(charmList), BorderLayout.CENTER);

        mainPanel.add(charmsPanel, "Charms");

        // ===== Quiz Panel =====
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel quizLabel = new JLabel("Quiz Questions");
        quizLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        quizLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quizPanel.add(quizLabel);
        quizPanel.add(Box.createVerticalStrut(15));

        for (QuizQuestion q : QuizData.getAllQuestions()) {
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    new EmptyBorder(10, 10, 10, 10)
            ));
            questionPanel.setBackground(Color.WHITE);

            JLabel questionText = new JLabel(q.getQuestion());
            questionText.setFont(new Font("Segoe UI", Font.BOLD, 16));
            questionPanel.add(questionText);
            questionPanel.add(Box.createVerticalStrut(5));

            String[] choices = q.getChoices();
            for (int i = 0; i < choices.length; i++) {
                JLabel choiceLabel = new JLabel((char) ('A' + i) + ". " + choices[i]);
                choiceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                questionPanel.add(choiceLabel);
            }

            quizPanel.add(questionPanel);
            quizPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane quizScroll = new JScrollPane(quizPanel);
        mainPanel.add(quizScroll, "Quiz");

        // ===== Sidebar Actions =====
        usersBtn.addActionListener(e -> switchPanel("Users"));
        charmsBtn.addActionListener(e -> switchPanel("Charms"));
        quizBtn.addActionListener(e -> switchPanel("Quiz"));

        // Logout action: opens LoginPage
        logoutBtn.addActionListener(e -> {
            new LoginPage(); // open login page
            dispose();        // close admin dashboard
        });

        setVisible(true);
    }

    private void switchPanel(String name) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, name);
    }

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Alice", "123"));
        users.add(new User("Bob", "123"));
        SwingUtilities.invokeLater(() -> new AdminDashboard(users));
    }
}
