package models;

import java.util.HashMap;

public class ResultLogic {

    private static HashMap<String, String> charmMap = new HashMap<>();

    static {
        // Special predefined charms
        charmMap.put("Aries-ENFP-Fire-Energetic", "phoenix.png");
        charmMap.put("Pisces-INFJ-Water-Calm", "aqua_spirit.png");
        charmMap.put("Leo-ENTJ-Fire-Bold", "sun_lion.png");
        charmMap.put("Virgo-ISTJ-Earth-Calm", "forest_guardian.png");
    }

    public static String generateCharm(UserAnswer ans) {

        String key = ans.getZodiac() + "-" +
                     ans.getMbti() + "-" +
                     ans.getElement() + "-" +
                     ans.getMood();

        // If exact match exists
        if (charmMap.containsKey(key)) {
            return charmMap.get(key);
        }

        // Fallback → auto-generate unique charm
        return autoGenerateCharm(ans);
    }

    private static String autoGenerateCharm(UserAnswer ans) {

        // Example: ar-en-f-b.png
        String fileName =
                ans.getZodiac().substring(0, 2).toLowerCase() +
                ans.getMbti().substring(0, 2).toLowerCase() +
                ans.getElement().charAt(0) +
                ans.getMood().charAt(0) +
                ".png";

        return fileName;
    }
}
