package utils;

import java.io.*;
import models.User;

public class FileManager {

    // Save user charms to a text file
    public static void saveUserCharms(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(user.getUsername() + "_charms.txt"))) {
            user.getCharms().forEach(charm -> {
                try {
                    writer.write(charm.getName() + " | " + charm.getDescription() + " | " + charm.getImagePath());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load user charms from a text file
    public static void loadUserCharms(User user) {
        File file = new File(user.getUsername() + "_charms.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    user.addCharm(new models.Charm(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
