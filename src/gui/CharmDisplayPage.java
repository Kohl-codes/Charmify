package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Charm;
import models.User;

public class CharmDisplayPage extends JFrame {

    private User currentUser;
    private List<Charm> charms;
    private JLabel[] charmLabels;
    private JLabel[] nameLabels;
    private JLabel[] descLabels;
    private boolean showNames = true;

    private double angle = 0;
    private int centerX = 300, centerY = 200, braceletRadius = 120, braceletThickness = 20;
    private int beadCount = 20;

    public CharmDisplayPage(User user) {
        this.currentUser = user;
        this.charms = user.getCharms();
        charmLabels = new JLabel[charms.size()];
        nameLabels = new JLabel[charms.size()];
        descLabels = new JLabel[charms.size()];

        setTitle("Your 3D Charm Bracelet");
        setSize(650, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Toggle names button
        JButton toggleBtn = new JButton("Toggle Names");
        toggleBtn.setBounds(50, 520, 150, 35);
        toggleBtn.setBackground(new Color(63, 81, 181));
        toggleBtn.setForeground(Color.WHITE);
        toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        toggleBtn.setFocusPainted(false);
        toggleBtn.addActionListener(e -> {
            showNames = !showNames;
            for (int i = 0; i < nameLabels.length; i++) {
                nameLabels[i].setVisible(showNames);
                descLabels[i].setVisible(showNames);
            }
        });
        add(toggleBtn);

        // Collect another charm
        JButton collectBtn = new JButton("Collect Another Charm");
        collectBtn.setBounds(230, 520, 180, 35);
        collectBtn.setBackground(new Color(92, 107, 192));
        collectBtn.setForeground(Color.WHITE);
        collectBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        collectBtn.setFocusPainted(false);
        collectBtn.addActionListener(e -> {
            new QuizPage(currentUser);
            dispose();
        });
        add(collectBtn);

        // View gallery button
        JButton galleryBtn = new JButton("View Gallery");
        galleryBtn.setBounds(440, 520, 150, 35);
        galleryBtn.setBackground(new Color(76, 175, 80));
        galleryBtn.setForeground(Color.WHITE);
        galleryBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        galleryBtn.setFocusPainted(false);
        galleryBtn.addActionListener(e -> new GalleryPage(currentUser));
        add(galleryBtn);

        // Load charms
        for (int i = 0; i < charms.size(); i++) {
            Charm charm = charms.get(i);

            ImageIcon icon = new ImageIcon(charm.getImagePath());
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);

            charmLabels[i] = new JLabel(icon);
            charmLabels[i].setSize(80, 80);
            add(charmLabels[i]);

            nameLabels[i] = new JLabel(charm.getName(), SwingConstants.CENTER);
            nameLabels[i].setSize(100, 20);
            add(nameLabels[i]);

            descLabels[i] = new JLabel("<html><center>" + charm.getDescription() + "</center></html>", SwingConstants.CENTER);
            descLabels[i].setSize(150, 40);
            add(descLabels[i]);
        }

        startAnimation();
        setVisible(true);
    }

    private void startAnimation() {
        Timer timer = new Timer(30, e -> {
            for (int i = 0; i < charmLabels.length; i++) {
                double angleOffset = (2 * Math.PI / charmLabels.length) * i;
                int x = (int) (centerX + braceletRadius * Math.cos(angle + angleOffset));
                int y = (int) (centerY + braceletRadius * Math.sin(angle + angleOffset));

                charmLabels[i].setLocation(x - 40, y + 20);
                nameLabels[i].setLocation(x - 50, y + 105);
                descLabels[i].setLocation(x - 60, y + 130);
            }
            angle += 0.01;
            repaint();
        });
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 3D bracelet using gradient
        for (int t = 0; t < braceletThickness; t++) {
            float shade = 0.6f - (t / (float)braceletThickness) * 0.4f; // lighter at top
            g2.setColor(new Color(shade, shade * 0.9f, 0.2f));
            g2.drawOval(centerX - braceletRadius + t, centerY - braceletRadius + t, 
                        2 * (braceletRadius - t), 2 * (braceletRadius - t));
        }

        // Draw beads with small shadow
        g2.setColor(new Color(255, 215, 0));
        for (int i = 0; i < beadCount; i++) {
            double beadAngle = 2 * Math.PI / beadCount * i + angle;
            int beadX = (int) (centerX + braceletRadius * Math.cos(beadAngle)) - 5;
            int beadY = (int) (centerY + braceletRadius * Math.sin(beadAngle)) - 5;

            // Shadow
            g2.setColor(new Color(100, 100, 0, 120));
            g2.fillOval(beadX + 2, beadY + 2, 10, 10);

            // Bead
            g2.setColor(new Color(255, 215, 0));
            g2.fillOval(beadX, beadY, 10, 10);
        }
    }
}
