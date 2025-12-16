package models;

import data.CharmTemplates;
import java.util.*;

public class ResultLogic {

    /**
     * Generate a single charm (for backward compatibility)
     */
    public static Charm generateCharm(UserAnswer answers) {
        List<Charm> charms = generateCharms(answers);
        return charms.isEmpty() ? null : charms.get(0);
    }

    /**
     * Generate up to 5 unique charms based on user's answers,
     * randomly shuffled each time.
     */
    public static ArrayList<Charm> generateCharms(UserAnswer answers) {
        ArrayList<Charm> result = new ArrayList<>();
        List<Charm> allCharms = new ArrayList<>(CharmTemplates.getAllCharms());

        // Shuffle the charm list so it will be random every time
        Collections.shuffle(allCharms);

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

        // Sort answers by frequency
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        // Map answers to charms dynamically from shuffled list
        Set<Charm> selectedCharms = new LinkedHashSet<>(); // ensures uniqueness
        int index = 0;
        for (Map.Entry<String, Integer> entry : sorted) {
            if (index >= allCharms.size()) break;
            selectedCharms.add(allCharms.get(index));
            index++;
            if (selectedCharms.size() >= 5) break;
        }

        // Fill remaining slots if less than 5
        while (selectedCharms.size() < 5 && index < allCharms.size()) {
            selectedCharms.add(allCharms.get(index));
            index++;
        }

        result.addAll(selectedCharms);
        return result;
    }
}
