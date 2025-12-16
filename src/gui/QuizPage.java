package gui;

import data.QuizData;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import models.Charm;
import models.QuizQuestion;
import models.ResultLogic;
import models.User;
import models.UserAnswer;

public class QuizPage extends JFrame {

    private final User currentUser;
    private final Map<String, ButtonGroup> questionGroups;

    public QuizPage(User user) {
        this.currentUser = user;
        questionGroups = new HashMap<>();

        setTitle("Charmify Quiz");
        setSize(750, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Charmify Personality Quiz");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Quiz Panel
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.setBackground(new Color(245, 245, 250));
        JScrollPane scrollPane = new JScrollPane(quizPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load questions
        ArrayList<QuizQuestion> questions = QuizData.getAllQuestions();
        int qNumber = 1;
        for (QuizQuestion q : questions) {
            String qKey = "q" + qNumber;

            // Add letters A, B, C… to options
            String[] optionsWithLetters = new String[q.getChoices().length];
            for (int i = 0; i < q.getChoices().length; i++) {
                char letter = (char) ('A' + i);
                optionsWithLetters[i] = letter + ". " + q.getChoices()[i];
            }

            addQuestion(quizPanel, qNumber + ". " + q.getQuestion(), optionsWithLetters, qKey);
            qNumber++;
        }

        // Finish Button
        JButton finishBtn = new JButton("Finish Quiz");
        finishBtn.setBackground(new Color(33, 150, 243));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        finishBtn.setFocusPainted(false);
        finishBtn.setPreferredSize(new Dimension(200, 50));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245, 245, 250));
        btnPanel.add(finishBtn);
        add(btnPanel, BorderLayout.SOUTH);

        finishBtn.addActionListener(e -> finishQuiz());

        setVisible(true);
    }

    private void addQuestion(JPanel panel, String questionText, String[] options, String qKey) {
        JPanel questionBox = new JPanel();
        questionBox.setLayout(new BoxLayout(questionBox, BoxLayout.Y_AXIS));
        questionBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        questionBox.setBackground(Color.WHITE);

        JLabel questionLabel = new JLabel(questionText);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        questionBox.add(questionLabel);
        questionBox.add(Box.createVerticalStrut(8));

        ButtonGroup group = new ButtonGroup();
        questionGroups.put(qKey, group);

        for (String option : options) {
            JRadioButton btn = new JRadioButton(option);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(Color.WHITE);
            group.add(btn);
            questionBox.add(btn);
        }

        questionBox.add(Box.createVerticalStrut(10));
        panel.add(questionBox);
        panel.add(Box.createVerticalStrut(12));
    }

   private void finishQuiz() {
        // Validate all questions are answered
        for (int i = 1; i <= questionGroups.size(); i++) {
            if (getSelectedOption("q" + i).isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please answer all questions before finishing the quiz.",
                        "Incomplete Quiz",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Collect answers
        UserAnswer answers = new UserAnswer();
        for (int i = 1; i <= questionGroups.size(); i++) {
            String selected = getSelectedOption("q" + i);
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

        currentUser.setLastQuizAnswer(answers);

        // CLEAR old charms first
        currentUser.getCharms().clear();

        // GENERATE new randomized set of charms
        ArrayList<Charm> newCharms = ResultLogic.generateCharms(answers);

        // Shuffle to make it random
        java.util.Collections.shuffle(newCharms);

        // Only take first 5 charms
        for (int i = 0; i < Math.min(5, newCharms.size()); i++) {
            currentUser.addCharm(newCharms.get(i));
        }

        // Show bracelet with new charms
        new CharmDisplayPage(currentUser);
        dispose();
    }



    private String getSelectedOption(String qKey) {
        ButtonGroup group = questionGroups.get(qKey);
        if (group != null) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return button.getText().substring(0, 1);
                }
            }
        }
        return "";
    }
}
