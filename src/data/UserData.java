package data;

import models.User;
import java.util.ArrayList;

public class UserData {

    private static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", true));
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
