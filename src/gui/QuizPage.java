package gui;

import data.QuizData;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import models.Charm;
import models.QuizQuestion;
import models.ResultLogic;
import models.User;
import models.UserAnswer;

public class QuizPage extends JFrame {
    private User currentUser;
    private Map<String, ButtonGroup> questionGroups;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton finishBtn;
    private JButton prevBtn;  // Made this an instance variable
    private JButton nextBtn;  // Made this an instance variable
    private JProgressBar progressBar;
    private JLabel questionNumberLabel;
    private int currentQuestionIndex = 0;
    private final int TOTAL_QUESTIONS = 10;
    private Color primaryColor = new Color(63, 81, 181);
    private Color secondaryColor = new Color(92, 107, 192);
    private Color accentColor = new Color(76, 175, 80);
    
    public QuizPage(User user) {
        this.currentUser = user;
        this.questionGroups = new HashMap<>();
        
        setupUI();
        loadQuestions();
        setVisible(true);
    }
    
    private void setupUI() {
        setTitle("✨ Charmify Personality Quiz");
        setSize(900, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout());
        
        // Header Panel
        add(createHeader(), BorderLayout.NORTH);
        
        // Main Quiz Area with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Footer Panel
        add(createFooter(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(900, 120));
        header.setBackground(primaryColor);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, primaryColor.darker()));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        titlePanel.setOpaque(false);
        
        JLabel title = new JLabel("Discover Your Charm");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Answer honestly to reveal your unique personality charms!");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(200, 230, 255));
        
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);
        
        // Progress Panel
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);
        progressPanel.setBorder(new EmptyBorder(0, 40, 20, 40));
        
        progressBar = new JProgressBar(0, TOTAL_QUESTIONS);
        progressBar.setValue(1);
        progressBar.setForeground(accentColor);
        progressBar.setBackground(new Color(230, 230, 230));
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        questionNumberLabel = new JLabel("Question 1 of " + TOTAL_QUESTIONS, SwingConstants.CENTER);
        questionNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        questionNumberLabel.setForeground(Color.WHITE);
        questionNumberLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        progressPanel.add(questionNumberLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        
        header.add(titlePanel, BorderLayout.CENTER);
        header.add(progressPanel, BorderLayout.SOUTH);
        
        return header;
    }
    
    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(900, 100));
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            new EmptyBorder(15, 30, 15, 30)
        ));
        
        // Navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        navPanel.setOpaque(false);
        
        // Store buttons as instance variables so we can access them later
        prevBtn = createNavButton("← Previous", secondaryColor);
        nextBtn = createNavButton("Next →", secondaryColor);
        
        prevBtn.setEnabled(false);
        prevBtn.addActionListener(e -> showPreviousQuestion());
        nextBtn.addActionListener(e -> showNextQuestion());
        
        navPanel.add(prevBtn);
        navPanel.add(nextBtn);
        
        // Finish button
        finishBtn = createPrimaryButton("✨ Finish Quiz & See Your Charms");
        finishBtn.setEnabled(false);
        finishBtn.addActionListener(e -> finishQuiz());
        
        JPanel finishPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        finishPanel.setOpaque(false);
        finishPanel.add(finishBtn);
        
        footer.add(navPanel, BorderLayout.WEST);
        footer.add(finishPanel, BorderLayout.EAST);
        
        return footer;
    }
    
    private void loadQuestions() {
        ArrayList<QuizQuestion> questions = QuizData.getAllQuestions();
        
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);
            String qKey = "q" + (i + 1);
            
            // Create question panel
            JPanel questionPanel = createQuestionPanel(i + 1, q.getQuestion(), q.getChoices(), qKey);
            
            // Add to card panel with unique name
            cardPanel.add(questionPanel, "question" + (i + 1));
        }
        
        // Show first question
        cardLayout.show(cardPanel, "question1");
        updateProgress();
    }
    
    private JPanel createQuestionPanel(int number, String question, String[] choices, String qKey) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Question header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel numberLabel = new JLabel("Question " + number);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        numberLabel.setForeground(primaryColor);
        
        JLabel requiredLabel = new JLabel("Required *");
        requiredLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        requiredLabel.setForeground(Color.RED);
        requiredLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        headerPanel.add(numberLabel, BorderLayout.WEST);
        headerPanel.add(requiredLabel, BorderLayout.EAST);
        
        // Question text
        JTextArea questionArea = new JTextArea(question);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionArea.setBackground(Color.WHITE);
        questionArea.setBorder(new EmptyBorder(20, 0, 30, 0));
        questionArea.setFocusable(false);
        
        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            "Select one option:",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 12),
            Color.DARK_GRAY
        ));
        
        ButtonGroup group = new ButtonGroup();
        questionGroups.put(qKey, group);
        
        char optionLetter = 'A';
        for (String choice : choices) {
            JPanel optionPanel = new JPanel(new BorderLayout(10, 0));
            optionPanel.setBackground(new Color(250, 250, 250));
            optionPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
            optionPanel.setMaximumSize(new Dimension(700, 60));
            optionPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Option letter badge
            JLabel letterLabel = new JLabel(String.valueOf(optionLetter));
            letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            letterLabel.setForeground(Color.WHITE);
            letterLabel.setBackground(secondaryColor);
            letterLabel.setOpaque(true);
            letterLabel.setPreferredSize(new Dimension(30, 30));
            letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
            letterLabel.setBorder(BorderFactory.createLineBorder(secondaryColor.darker(), 1));
            
            // Option text
            JTextArea textArea = new JTextArea(choice);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textArea.setBackground(new Color(250, 250, 250));
            textArea.setBorder(new EmptyBorder(5, 10, 5, 10));
            textArea.setFocusable(false);
            
            // Radio button
            JRadioButton radioBtn = new JRadioButton();
            radioBtn.setBackground(new Color(250, 250, 250));
            radioBtn.setActionCommand(String.valueOf(optionLetter));
            
            // Add hover effect
            optionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    optionPanel.setBackground(new Color(240, 245, 255));
                    textArea.setBackground(new Color(240, 245, 255));
                }
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    optionPanel.setBackground(new Color(250, 250, 250));
                    textArea.setBackground(new Color(250, 250, 250));
                }
                
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    radioBtn.setSelected(true);
                    checkIfAllAnswered();
                }
            });
            
            group.add(radioBtn);
            
            optionPanel.add(letterLabel, BorderLayout.WEST);
            optionPanel.add(textArea, BorderLayout.CENTER);
            optionPanel.add(radioBtn, BorderLayout.EAST);
            
            optionsPanel.add(optionPanel);
            optionsPanel.add(Box.createVerticalStrut(8));
            
            optionLetter++;
        }
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(questionArea, BorderLayout.CENTER);
        panel.add(optionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            cardLayout.show(cardPanel, "question" + (currentQuestionIndex + 1));
            updateProgress();
            updateNavigationButtons();
        }
    }
    
    private void showNextQuestion() {
        // Check if current question is answered
        String currentQKey = "q" + (currentQuestionIndex + 1);
        if (getSelectedOption(currentQKey) == null) {
            JOptionPane.showMessageDialog(this,
                "Please answer this question before continuing!",
                "Answer Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (currentQuestionIndex < TOTAL_QUESTIONS - 1) {
            currentQuestionIndex++;
            cardLayout.show(cardPanel, "question" + (currentQuestionIndex + 1));
            updateProgress();
            updateNavigationButtons();
        }
    }
    
    private void updateProgress() {
        int currentQuestion = currentQuestionIndex + 1;
        progressBar.setValue(currentQuestion);
        progressBar.setString("Question " + currentQuestion + " of " + TOTAL_QUESTIONS);
        questionNumberLabel.setText("Question " + currentQuestion + " of " + TOTAL_QUESTIONS);
    }
    
    private void updateNavigationButtons() {
        // Update previous button
        prevBtn.setEnabled(currentQuestionIndex > 0);
        
        // Update next button
        if (currentQuestionIndex == TOTAL_QUESTIONS - 1) {
            nextBtn.setText("Last Question");
            nextBtn.setEnabled(false);
            finishBtn.setVisible(true);
        } else {
            nextBtn.setText("Next →");
            nextBtn.setEnabled(true);
            finishBtn.setVisible(false);
        }
        
        // Update finish button state
        checkIfAllAnswered();
    }
    
    private void checkIfAllAnswered() {
        boolean allAnswered = true;
        
        for (int i = 1; i <= TOTAL_QUESTIONS; i++) {
            String qKey = "q" + i;
            if (getSelectedOption(qKey) == null) {
                allAnswered = false;
                break;
            }
        }
        
        finishBtn.setEnabled(allAnswered);
        
        // Update finish button text and color
        if (allAnswered) {
            finishBtn.setText("✨ Complete Quiz & Reveal Charms!");
            finishBtn.setBackground(accentColor);
        } else {
            finishBtn.setText("Finish Quiz");
            finishBtn.setBackground(secondaryColor);
        }
    }
    
    private void finishQuiz() {
        // Validate all questions are answered
        for (int i = 1; i <= TOTAL_QUESTIONS; i++) {
            String qKey = "q" + i;
            if (getSelectedOption(qKey) == null) {
                JOptionPane.showMessageDialog(this,
                    "Please answer question " + i + " before submitting!",
                    "Incomplete Quiz",
                    JOptionPane.WARNING_MESSAGE);
                
                // Navigate to unanswered question
                if (currentQuestionIndex != i - 1) {
                    currentQuestionIndex = i - 1;
                    cardLayout.show(cardPanel, "question" + (currentQuestionIndex + 1));
                    updateProgress();
                    updateNavigationButtons();
                }
                
                return;
            }
        }
        
        // Collect answers
        UserAnswer answers = new UserAnswer();
        
        for (int i = 1; i <= TOTAL_QUESTIONS; i++) {
            String qKey = "q" + i;
            String selected = getSelectedOption(qKey);
            
            switch (i) {
                case 1: answers.setAnswer1(selected); break;
                case 2: answers.setAnswer2(selected); break;
                case 3: answers.setAnswer3(selected); break;
                case 4: answers.setAnswer4(selected); break;
                case 5: answers.setAnswer5(selected); break;
                case 6: answers.setAnswer6(selected); break;
                case 7: answers.setAnswer7(selected); break;
                case 8: answers.setAnswer8(selected); break;
                case 9: answers.setAnswer9(selected); break;
                case 10: answers.setAnswer10(selected); break;
            }
        }
        
        // Process quiz results
        currentUser.setLastQuizAnswer(answers);
        ArrayList<Charm> newCharms = ResultLogic.generateCharms(answers);
        
        // Check for duplicates
        int duplicatesFound = 0;
        int newCharmsAdded = 0;
        StringBuilder newCharmNames = new StringBuilder();
        
        for (Charm newCharm : newCharms) {
            boolean alreadyHas = false;
            
            for (Charm existingCharm : currentUser.getCharms()) {
                if (existingCharm.getName().equals(newCharm.getName())) {
                    alreadyHas = true;
                    duplicatesFound++;
                    break;
                }
            }
            
            if (!alreadyHas) {
                currentUser.addCharm(newCharm);
                newCharmsAdded++;
                newCharmNames.append("\n• ").append(newCharm.getName());
            }
        }
        
        // Show result message
        showResultMessage(newCharmsAdded, duplicatesFound, newCharmNames.toString());
        
        // Navigate to charm display
        new CharmDisplayPage(currentUser);
        dispose();
    }
    
    private void showResultMessage(int newCharmsAdded, int duplicatesFound, String newCharmNames) {
        String title = "🎉 Quiz Complete!";
        String message;
        int messageType = JOptionPane.INFORMATION_MESSAGE;
        
        if (newCharmsAdded == 0 && duplicatesFound > 0) {
            title = "All Charms Duplicate!";
            message = "You already have all " + duplicatesFound + " charm(s) from this quiz!\n\n" +
                     "💡 Tip: Try answering differently to discover new charms!";
            messageType = JOptionPane.WARNING_MESSAGE;
        } else if (newCharmsAdded > 0 && duplicatesFound > 0) {
            message = "🎉 You earned " + newCharmsAdded + " new charm(s)!\n" +
                     "🔄 " + duplicatesFound + " were already in your collection.\n\n" +
                     "✨ New charms earned:" + newCharmNames;
        } else if (newCharmsAdded > 0) {
            message = "🎊 Amazing! You discovered " + newCharmsAdded + " brand new charm(s)!\n\n" +
                     "✨ Your new charms:" + newCharmNames;
        } else {
            message = "No charms were generated. Please try again.";
            messageType = JOptionPane.ERROR_MESSAGE;
        }
        
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    private String getSelectedOption(String qKey) {
        ButtonGroup group = questionGroups.get(qKey);
        if (group != null) {
            Enumeration<AbstractButton> buttons = group.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return button.getActionCommand();
                }
            }
        }
        return null;
    }
    
    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(secondaryColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(secondaryColor.darker(), 2),
            new EmptyBorder(12, 25, 12, 25)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(secondaryColor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(secondaryColor);
            }
        });
        
        return btn;
    }
    
    private JButton createNavButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
}