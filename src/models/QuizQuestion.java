package models;

public class QuizQuestion {

    private String question;
    private String[] choices;
    private String category; // zodiac, mbti, element, mood

    public QuizQuestion(String question, String[] choices, String category) {
        this.question = question;
        this.choices = choices;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCategory() {
        return category;
    }
}
