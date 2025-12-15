package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import data.QuizData;
import models.*;

public class QuizPage extends JFrame {

    private User currentUser;
    private int index = 0;
    private ArrayList<QuizQuestion> questions;
    private UserAnswer answers = new UserAnswer();

    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;

    public QuizPage(User user) {
        this.currentUser = user;
        questions = QuizData.getAllQuestions();

        setTitle("Charmify Personality Quiz");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        questionLabel = new JLabel();
        questionLabel.setBounds(30, 20, 420, 30);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(questionLabel);

        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(50, 60 + (i * 30), 400, 25);
            group.add(options[i]);
            add(options[i]);
        }

        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(190, 220, 100, 30);
        add(nextBtn);

        loadQuestion();

        nextBtn.addActionListener(e -> {
            saveAnswer();

            // check if last question
            if (index >= questions.size() - 1) {
                String charm = ResultLogic.generateCharm(answers);
                currentUser.addCharm(charm);  // store charm

                // Open CharmDisplayPage with bracelet animation
                new CharmDisplayPage(currentUser.getCharms());
                dispose();
            } else {
                index++;
                loadQuestion();
            }
        });

        setVisible(true);
    }

    private void loadQuestion() {
        QuizQuestion q = questions.get(index);
        questionLabel.setText(q.getQuestion());

        String[] choices = q.getChoices();
        for (int i = 0; i < options.length; i++) {
            options[i].setVisible(i < choices.length);
            if (i < choices.length) {
                options[i].setText(choices[i]);
            }
        }
        group.clearSelection();
    }

    private void saveAnswer() {
        QuizQuestion q = questions.get(index);
        for (JRadioButton btn : options) {
            if (btn.isSelected()) {
                String value = btn.getText();
                if (q.getCategory().equals("zodiac")) {
                    answers.setZodiac(value);
                } else if (q.getCategory().equals("element")) {
                    answers.setElement(value);
                } else if (q.getCategory().equals("mood")) {
                    answers.setMood(value);
                } else if (q.getCategory().equals("mbti_IE")) {
                    answers.setMbti(value.startsWith("Being alone") ? "I" : "E");
                }
            }
        }
    }
}
