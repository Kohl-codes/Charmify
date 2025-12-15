package models;

import data.CharmTemplates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultLogic {

    /**
     * Generate a single charm based on UserAnswer (for backward compatibility).
     */
    public static Charm generateCharm(UserAnswer answers) {
        List<Charm> charms = generateCharms(answers);
        return charms.isEmpty() ? null : charms.get(0);
    }

    /**
     * Generate up to 5 charms based on the user's answers.
     */
    public static ArrayList<Charm> generateCharms(UserAnswer answers) {
        ArrayList<Charm> result = new ArrayList<>();

        // Count occurrences of A-E answers
        Map<String, Integer> counts = new HashMap<>();
        String[] allAnswers = {
                answers.getAnswer1(), answers.getAnswer2(), answers.getAnswer3(),
                answers.getAnswer4(), answers.getAnswer5(), answers.getAnswer6(),
                answers.getAnswer7(), answers.getAnswer8(), answers.getAnswer9(),
                answers.getAnswer10()
        };

        for (String ans : allAnswers) {
            if (!ans.isEmpty()) {
                counts.put(ans, counts.getOrDefault(ans, 0) + 1);
            }
        }

        // Sort answers by frequency (most selected first)
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        // Fetch predefined charms
        List<Charm> allCharms = CharmTemplates.getAllCharms();
        int charmIndex = 0;

        // Assign charms based on the top answers
        for (Map.Entry<String, Integer> entry : sorted) {
            if (charmIndex >= 5) break; // max 5 charms
            String answerLetter = entry.getKey();
            // Map A-E to first 5 charms
            switch (answerLetter) {
                case "A": result.add(allCharms.get(0)); break;
                case "B": result.add(allCharms.get(1)); break;
                case "C": result.add(allCharms.get(2)); break;
                case "D": result.add(allCharms.get(3)); break;
                case "E": result.add(allCharms.get(4)); break;
                default: result.add(allCharms.get(allCharms.size() - 1)); break;
            }
            charmIndex++;
        }

        // Fill remaining charms if less than 5
        while (result.size() < 5 && result.size() < allCharms.size()) {
            result.add(allCharms.get(result.size()));
        }

        return result;
    }
}
