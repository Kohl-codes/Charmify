package models;

/**
 * Represents a single quiz question with multiple choices and a category.
 */
public class QuizQuestion {

    private String question;
    private String[] choices;
    private String category; // e.g., zodiac, mbti, element, mood

    public QuizQuestion(String question, String[] choices, String category) {
        this.question = question;
        this.choices = choices;
        this.category = category;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCategory() {
        return category;
    }

    // Optional: For debugging or displaying the question in console
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(question).append("\n");
        for (int i = 0; i < choices.length; i++) {
            sb.append((char)('A' + i)).append(". ").append(choices[i]).append("\n");
        }
        sb.append("Category: ").append(category);
        return sb.toString();
    }
}
