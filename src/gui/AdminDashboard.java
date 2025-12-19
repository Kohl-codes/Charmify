package gui;

import data.CharmTemplates;
import data.QuizData;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import models.Charm;
import models.QuizQuestion;
import models.User;

public class AdminDashboard extends JFrame {

    private DefaultListModel<User> userListModel;
    private JTable charmTable;
    private DefaultTableModel charmTableModel;
    private JTable questionTable;
    private DefaultTableModel questionTableModel;
    private JPanel mainPanel;
    private Color primaryColor = new Color(33, 150, 243);
    private Color accentColor = new Color(76, 175, 80);
    private Color deleteColor = new Color(231, 76, 60);

    public AdminDashboard(ArrayList<User> users) {
        setTitle("Admin Dashboard");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 249, 250));
        setLayout(new BorderLayout());

        // ===== Header =====
        add(createHeader(), BorderLayout.NORTH);

        // ===== Sidebar =====
        add(createSidebar(), BorderLayout.WEST);

        // ===== Main Panel =====
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Initialize panels
        mainPanel.add(createUsersPanel(users), "Users");
        mainPanel.add(createCharmsPanel(), "Charms");
        mainPanel.add(createQuizPanel(), "Quiz");
        mainPanel.add(createStatsPanel(), "Stats");

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setPreferredSize(new Dimension(1200, 80));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, primaryColor.darker()));

        // Title
        JLabel title = new JLabel("⚙️ Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(0, 30, 0, 0));

        // Admin info
        JLabel adminLabel = new JLabel("Administrator Panel", SwingConstants.RIGHT);
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminLabel.setForeground(new Color(200, 230, 255));
        adminLabel.setBorder(new EmptyBorder(0, 0, 0, 30));

        header.add(title, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Sidebar title
        JLabel sidebarTitle = new JLabel("Navigation");
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        sidebar.add(sidebarTitle);

        // Sidebar buttons
        String[] buttons = {"👥 Users", "✨ Charms", "❓ Questions", "📊 Statistics", "🚪 Logout"};
        String[] panelNames = {"Users", "Charms", "Quiz", "Stats", null};

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = createSidebarButton(buttons[i]);
            final String panelName = panelNames[i];
            
            if (panelName != null) {
                btn.addActionListener(e -> switchPanel(panelName));
            } else {
                btn.addActionListener(e -> {
                    new LoginPage();
                    dispose();
                });
            }
            
            sidebar.add(btn);
            if (i < buttons.length - 1) {
                sidebar.add(Box.createVerticalStrut(10));
            }
        }

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(80, 80, 80));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 60, 60));
            }
        });
        
        return btn;
    }

    private JPanel createUsersPanel(ArrayList<User> users) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("👥 User Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(primaryColor);
        
        JButton refreshBtn = createActionButton("🔄 Refresh", primaryColor);
        refreshBtn.addActionListener(e -> refreshUsers());
        
        header.add(title, BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);

        // User list
        userListModel = new DefaultListModel<>();
        users.forEach(userListModel::addElement);
        JList<User> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Custom renderer for users
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof User) {
                    User user = (User) value;
                    setText(user.getUsername() + (user.isAdmin() ? " 👑" : " 👤"));
                    if (user.isAdmin()) {
                        setForeground(new Color(255, 152, 0));
                    }
                }
                return this;
            }
        });

        // Control buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controlPanel.setBackground(Color.WHITE);
        
        JButton viewCharmsBtn = createActionButton("👁️ View Charms", accentColor);
        JButton toggleAdminBtn = createActionButton("👑 Toggle Admin", new Color(255, 152, 0));
        JButton deleteUserBtn = createActionButton("🗑️ Delete", deleteColor);
        
        viewCharmsBtn.addActionListener(e -> viewUserCharms(userList.getSelectedValue()));
        toggleAdminBtn.addActionListener(e -> toggleUserAdmin(userList.getSelectedValue()));
        deleteUserBtn.addActionListener(e -> deleteUser(userList.getSelectedValue()));
        
        controlPanel.add(viewCharmsBtn);
        controlPanel.add(toggleAdminBtn);
        controlPanel.add(deleteUserBtn);

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCharmsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Header with buttons
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("✨ Charm Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(primaryColor);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton addBtn = createActionButton("➕ Add New", accentColor);
        JButton refreshBtn = createActionButton("🔄 Refresh", primaryColor);
        
        addBtn.addActionListener(e -> addNewCharm());
        refreshBtn.addActionListener(e -> refreshCharms());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        
        header.add(title, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        // Charm table
        String[] columns = {"ID", "Name", "Description", "Image Path", "Actions"};
        charmTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only actions column is editable
            }
        };
        
        charmTable = new JTable(charmTableModel);
        charmTable.setRowHeight(40);
        charmTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        charmTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        charmTable.getTableHeader().setBackground(primaryColor);
        charmTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add action buttons to table
        charmTable.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        charmTable.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor());
        
        // Load charms
        refreshCharms();

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(charmTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        // Header with buttons
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("❓ Question Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(primaryColor);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton addBtn = createActionButton("➕ Add Question", accentColor);
        JButton refreshBtn = createActionButton("🔄 Refresh", primaryColor);
        
        addBtn.addActionListener(e -> addNewQuestion());
        refreshBtn.addActionListener(e -> refreshQuestions());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        
        header.add(title, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        // Question table
        String[] columns = {"ID", "Question", "Options Count", "Actions"};
        questionTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only actions column is editable
            }
        };
        
        questionTable = new JTable(questionTableModel);
        questionTable.setRowHeight(40);
        questionTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        questionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        questionTable.getTableHeader().setBackground(primaryColor);
        questionTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add action buttons to table
        questionTable.getColumnModel().getColumn(3).setCellRenderer(new TableButtonRenderer());
        questionTable.getColumnModel().getColumn(3).setCellEditor(new TableButtonEditor());
        
        // Load questions
        refreshQuestions();

        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(questionTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("📊 Statistics Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(primaryColor);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Stat cards
        statsPanel.add(createStatCard("Total Users", String.valueOf(userListModel.size()), "👥", primaryColor));
        statsPanel.add(createStatCard("Total Charms", String.valueOf(CharmTemplates.getAllCharms().size()), "✨", accentColor));
        statsPanel.add(createStatCard("Total Questions", String.valueOf(QuizData.getAllQuestions().size()), "❓", new Color(156, 39, 176)));
        statsPanel.add(createStatCard("Admin Users", 
            String.valueOf(userListModel.size() - userListModel.size() + 1), "👑", new Color(255, 152, 0)));

        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        
        return card;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }

    // ===== CRUD Operations =====

    private void refreshUsers() {
        // In a real app, this would fetch from database
        // For now, just refresh the list
        JOptionPane.showMessageDialog(this, "Users refreshed!", "Refresh", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewUserCharms(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Please select a user first!", "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<Charm> charms = user.getCharms();
        if (charms.isEmpty()) {
            JOptionPane.showMessageDialog(this, user.getUsername() + " has no charms yet.", 
                "User Charms", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder(user.getUsername() + "'s Charms:\n\n");
            for (Charm charm : charms) {
                message.append("✨ ").append(charm.getName()).append("\n");
                message.append("   ").append(charm.getDescription()).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), 
                "User Charms - " + user.getUsername(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void toggleUserAdmin(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Please select a user first!", "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Toggle admin status for " + user.getUsername() + "?", 
            "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            user.setAdmin(!user.isAdmin());
            JOptionPane.showMessageDialog(this, 
                user.getUsername() + " is now " + (user.isAdmin() ? "an admin" : "a regular user"),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshUsers();
        }
    }

    private void deleteUser(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Please select a user first!", "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete user: " + user.getUsername() + "?\nThis action cannot be undone!", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // In a real app, this would delete from database
            userListModel.removeElement(user);
            JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshCharms() {
        charmTableModel.setRowCount(0);
        List<Charm> charms = CharmTemplates.getAllCharms();
        
        for (int i = 0; i < charms.size(); i++) {
            Charm charm = charms.get(i);
            charmTableModel.addRow(new Object[]{
                i + 1,
                charm.getName(),
                charm.getDescription(),
                charm.getImagePath(),
                "Edit | Delete"
            });
        }
    }

    private void addNewCharm() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField imageField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Description:", descField,
            "Image Path:", imageField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Charm", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String image = imageField.getText().trim();
            
            if (name.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and description are required!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // In a real app, this would add to database
            Charm newCharm = new Charm(name, desc, image);
            charmTableModel.addRow(new Object[]{
                charmTableModel.getRowCount() + 1,
                name,
                desc,
                image,
                "Edit | Delete"
            });
            
            JOptionPane.showMessageDialog(this, "Charm added successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editCharm(int row) {
        if (row < 0 || row >= charmTableModel.getRowCount()) return;
        
        String name = (String) charmTableModel.getValueAt(row, 1);
        String desc = (String) charmTableModel.getValueAt(row, 2);
        String image = (String) charmTableModel.getValueAt(row, 3);
        
        JTextField nameField = new JTextField(name);
        JTextField descField = new JTextField(desc);
        JTextField imageField = new JTextField(image);

        Object[] message = {
            "Name:", nameField,
            "Description:", descField,
            "Image Path:", imageField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Charm", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String newName = nameField.getText().trim();
            String newDesc = descField.getText().trim();
            String newImage = imageField.getText().trim();
            
            if (newName.isEmpty() || newDesc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and description are required!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            charmTableModel.setValueAt(newName, row, 1);
            charmTableModel.setValueAt(newDesc, row, 2);
            charmTableModel.setValueAt(newImage, row, 3);
            
            JOptionPane.showMessageDialog(this, "Charm updated successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteCharm(int row) {
        if (row < 0 || row >= charmTableModel.getRowCount()) return;
        
        String charmName = (String) charmTableModel.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete charm: " + charmName + "?\nThis action cannot be undone!", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            charmTableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Charm deleted successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshQuestions() {
        questionTableModel.setRowCount(0);
        List<QuizQuestion> questions = QuizData.getAllQuestions();
        
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            questionTableModel.addRow(new Object[]{
                i + 1,
                q.getQuestion(),
                q.getChoices().length,
                "Edit | Delete"
            });
        }
    }

    private void addNewQuestion() {
        JTextField questionField = new JTextField();
        JTextField option1Field = new JTextField();
        JTextField option2Field = new JTextField();
        JTextField option3Field = new JTextField();
        JTextField option4Field = new JTextField();
        JTextField option5Field = new JTextField();

        Object[] message = {
            "Question:", questionField,
            "Option A:", option1Field,
            "Option B:", option2Field,
            "Option C:", option3Field,
            "Option D:", option4Field,
            "Option E:", option5Field
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Question", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String question = questionField.getText().trim();
            String[] options = {
                option1Field.getText().trim(),
                option2Field.getText().trim(),
                option3Field.getText().trim(),
                option4Field.getText().trim(),
                option5Field.getText().trim()
            };
            
            if (question.isEmpty() || options[0].isEmpty()) {
                JOptionPane.showMessageDialog(this, "Question and at least one option are required!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Count non-empty options
            int optionCount = 0;
            for (String opt : options) {
                if (!opt.isEmpty()) optionCount++;
            }
            
            questionTableModel.addRow(new Object[]{
                questionTableModel.getRowCount() + 1,
                question,
                optionCount,
                "Edit | Delete"
            });
            
            JOptionPane.showMessageDialog(this, "Question added successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editQuestion(int row) {
        if (row < 0 || row >= questionTableModel.getRowCount()) return;
        
        String question = (String) questionTableModel.getValueAt(row, 1);
        
        // In a real app, this would fetch the actual options
        JTextField questionField = new JTextField(question);
        JTextField option1Field = new JTextField("Option A");
        JTextField option2Field = new JTextField("Option B");
        JTextField option3Field = new JTextField("Option C");
        JTextField option4Field = new JTextField("Option D");
        JTextField option5Field = new JTextField("Option E");

        Object[] message = {
            "Question:", questionField,
            "Option A:", option1Field,
            "Option B:", option2Field,
            "Option C:", option3Field,
            "Option D:", option4Field,
            "Option E:", option5Field
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Question", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String newQuestion = questionField.getText().trim();
            String[] options = {
                option1Field.getText().trim(),
                option2Field.getText().trim(),
                option3Field.getText().trim(),
                option4Field.getText().trim(),
                option5Field.getText().trim()
            };
            
            if (newQuestion.isEmpty() || options[0].isEmpty()) {
                JOptionPane.showMessageDialog(this, "Question and at least one option are required!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int optionCount = 0;
            for (String opt : options) {
                if (!opt.isEmpty()) optionCount++;
            }
            
            questionTableModel.setValueAt(newQuestion, row, 1);
            questionTableModel.setValueAt(optionCount, row, 2);
            
            JOptionPane.showMessageDialog(this, "Question updated successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteQuestion(int row) {
        if (row < 0 || row >= questionTableModel.getRowCount()) return;
        
        String question = (String) questionTableModel.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete question: \"" + question + "\"?\nThis action cannot be undone!", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            questionTableModel.removeRow(row);
            JOptionPane.showMessageDialog(this, "Question deleted successfully!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void switchPanel(String name) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, name);
    }

    // ===== Table Button Renderer & Editor =====

    class TableButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public TableButtonRenderer() {
            setOpaque(true);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
            setBackground(primaryColor);
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }

    class TableButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;

        public TableButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Handle button click
                String source = button.getText();
                if (source.contains("Edit")) {
                    if (charmTable.isEditing()) {
                        editCharm(currentRow);
                    } else if (questionTable.isEditing()) {
                        editQuestion(currentRow);
                    }
                } else if (source.contains("Delete")) {
                    if (charmTable.isEditing()) {
                        deleteCharm(currentRow);
                    } else if (questionTable.isEditing()) {
                        deleteQuestion(currentRow);
                    }
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("admin", "admin123"));
        users.add(new User("Alice", "123"));
        users.add(new User("Bob", "123"));
        
        SwingUtilities.invokeLater(() -> new AdminDashboard(users));
    }
}