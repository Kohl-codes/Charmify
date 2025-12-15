package data;

import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserData {

    private static List<User> users = new ArrayList<>();

    static {
        // Sample users
        User admin = new User("admin", "admin");
        admin.setAdmin(true);
        users.add(admin);

        User user1 = new User("nichole", "123");
        users.add(user1);
    }

    public static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }
}
