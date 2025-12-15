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

    private User currentUser;
    private Map<String, ButtonGroup> questionGroups;

    public QuizPage(User user) {
        this.currentUser = user;
        questionGroups = new HashMap<>();

        setTitle("Charmify Quiz");
        setSize(750, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Charmify Personality Quiz");
        title.setBounds(0, 10, 750, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title);

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(quizPanel);
        scrollPane.setBounds(20, 60, 700, 600);
        add(scrollPane);

        // Load questions from QuizData
        ArrayList<QuizQuestion> questions = QuizData.getAllQuestions();
        int qNumber = 1;

        for (QuizQuestion q : questions) {
            String qKey = "q" + qNumber;

            // Add letters A, B, C, etc. to options
            String[] optionsWithLetters = new String[q.getChoices().length];
            for (int i = 0; i < q.getChoices().length; i++) {
                char letter = (char) ('A' + i);
                optionsWithLetters[i] = letter + ". " + q.getChoices()[i];
            }

            addQuestion(quizPanel, qNumber + ". " + q.getQuestion(), optionsWithLetters, qKey);
            qNumber++;
        }

        JButton finishBtn = new JButton("Finish Quiz");
        finishBtn.setBounds(250, 670, 200, 40);
        finishBtn.setBackground(new Color(92, 107, 192));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        finishBtn.setFocusPainted(false);
        add(finishBtn);

        finishBtn.addActionListener(e -> finishQuiz());

        setVisible(true);
    }

    private void addQuestion(JPanel panel, String questionText, String[] options, String qKey) {
        JLabel questionLabel = new JLabel(questionText);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(questionLabel);

        ButtonGroup group = new ButtonGroup();
        questionGroups.put(qKey, group);

        for (String option : options) {
            JRadioButton btn = new JRadioButton(option);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            group.add(btn);
            panel.add(btn);
        }
        panel.add(Box.createVerticalStrut(10));
    }

    private void finishQuiz() {
        UserAnswer answers = new UserAnswer();

        // Dynamically set answers based on question order
        for (int i = 1; i <= questionGroups.size(); i++) {
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

        currentUser.setLastQuizAnswer(answers);

        // Generate multiple charms based on answers
        // You can modify ResultLogic to return List<Charm>
        ArrayList<Charm> newCharms = ResultLogic.generateCharms(answers); // refactored method
        for (Charm c : newCharms) {
            currentUser.addCharm(c);
        }

        // Display all charms in bracelet layout
        new CharmDisplayPage(currentUser);

        dispose();
    }

    private String getSelectedOption(String qKey) {
        ButtonGroup group = questionGroups.get(qKey);
        if (group != null) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    return button.getText().substring(0, 1); // return letter A-E
                }
            }
        }
        return ""; // none selected
    }
}
