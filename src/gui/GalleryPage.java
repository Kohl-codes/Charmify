package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Charm;
import models.User;

public class GalleryPage extends JFrame {

    private User currentUser;

    public GalleryPage(User user) {
        this.currentUser = user;

        setTitle("Charm Gallery");
        setSize(500, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Your Charm Collection", SwingConstants.CENTER);
        label.setBounds(50, 10, 400, 30);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(label);

        // Display charms dynamically
        List<Charm> charms = currentUser.getCharms();
        int x = 50;
        int y = 50;
        int width = 80;
        int height = 80;
        int gap = 20;

        for (Charm charm : charms) {
            ImageIcon icon = new ImageIcon(charm.getImagePath());
            // Scale image to fit label
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);

            JLabel charmLabel = new JLabel(icon);
            charmLabel.setBounds(x, y, width, height);
            add(charmLabel);

            // Add name below charm
            JLabel nameLabel = new JLabel(charm.getName(), SwingConstants.CENTER);
            nameLabel.setBounds(x - 10, y + height, width + 20, 20);
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            add(nameLabel);

            x += width + gap;
            if (x + width > getWidth()) { // wrap to next row
                x = 50;
                y += height + 40; // add extra space for name label
            }
        }

        setVisible(true);
    }
}
