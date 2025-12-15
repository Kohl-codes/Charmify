package models;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private boolean admin;

    // Store generated charms
    private ArrayList<String> charms;

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.charms = new ArrayList<>();
    }

    // ===== GETTERS =====
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    // ===== CHARM MANAGEMENT =====
    public void addCharm(String charm) {
        charms.add(charm);
    }

    public ArrayList<String> getCharms() {
        return charms;
    }
}
