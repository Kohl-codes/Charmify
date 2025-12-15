package data;

import java.util.ArrayList;
import models.QuizQuestion;

public class QuizData {

    public static ArrayList<QuizQuestion> getAllQuestions() {

        ArrayList<QuizQuestion> questions = new ArrayList<>();

        // ZODIAC (simplified choice-based)
        questions.add(new QuizQuestion(
                "Which describes you best?",
                new String[]{"Bold Leader", "Calm Thinker", "Creative Soul", "Adventurous"},
                "zodiac"
        ));

        // MBTI (simplified)
        questions.add(new QuizQuestion(
                "You gain energy from:",
                new String[]{"Being alone", "Being with people"},
                "mbti_IE"
        ));

        questions.add(new QuizQuestion(
                "You trust more:",
                new String[]{"Facts", "Ideas"},
                "mbti_SN"
        ));

        questions.add(new QuizQuestion(
                "You decide using:",
                new String[]{"Logic", "Feelings"},
                "mbti_TF"
        ));

        questions.add(new QuizQuestion(
                "You prefer:",
                new String[]{"Plans", "Flexibility"},
                "mbti_JP"
        ));

        // ELEMENT
        questions.add(new QuizQuestion(
                "Which element attracts you most?",
                new String[]{"Fire", "Water", "Earth", "Air"},
                "element"
        ));

        // MOOD
        questions.add(new QuizQuestion(
                "Your usual vibe:",
                new String[]{"Calm", "Energetic", "Soft", "Bold"},
                "mood"
        ));

        return questions;
    }
}
