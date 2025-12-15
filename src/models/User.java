package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private boolean admin = false;
    private List<Charm> charms;
    private UserAnswer lastQuizAnswer;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.charms = new ArrayList<>();
    }

    public void addCharm(Charm charm) {
        charms.add(charm);
    }

    public List<Charm> getCharms() {
        return charms;
    }

    public void setLastQuizAnswer(UserAnswer answer) {
        this.lastQuizAnswer = answer;
    }

    public UserAnswer getLastQuizAnswer() {
        return lastQuizAnswer;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pwd) {
        return this.password.equals(pwd);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
