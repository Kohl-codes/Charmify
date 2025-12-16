package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import models.Charm;
import models.User;

public class CharmDisplayPage extends JFrame {

    private final BraceletPanel braceletPanel;
    private final User currentUser;

    public CharmDisplayPage(User user) {
        this.currentUser = user;

        setTitle("Charmify");
        setSize(900, 780);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the user's current charm collection
        braceletPanel = new BraceletPanel(user.getCharms());

        add(createHeader(), BorderLayout.NORTH);
        add(braceletPanel, BorderLayout.CENTER);
        add(createBottomControls(), BorderLayout.SOUTH);

        setVisible(true);
    }

    /* ================= HEADER ================= */
    private JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(900, 60));
        panel.setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Your Charm Bracelet");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

         // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBackground(new Color(200, 0, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        logoutBtn.addActionListener(e -> {
            new LoginPage(); // Go back to login page
            dispose();       // Close current window
        });

        panel.add(title, BorderLayout.WEST);
        panel.add(logoutBtn, BorderLayout.EAST);

        return panel;
      
    }

    /* ================= BOTTOM CONTROLS ================= */
    private JPanel createBottomControls() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(900, 95));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JLabel userLabel = new JLabel("User: " + currentUser.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        center.setOpaque(false);

        JToggleButton toggleNames = createToggle("Names", true, braceletPanel::toggleNames);
        JToggleButton toggleDesc = createToggle("Meanings", true, braceletPanel::toggleDescriptions);

        JButton colorBtn = createSecondaryButton("Bead Color");
        colorBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(
                    this,
                    "Choose Bead Color",
                    braceletPanel.getBeadColor()
            );
            if (chosen != null) {
                braceletPanel.setBeadColor(chosen);
            }
        });

        center.add(toggleNames);
        center.add(toggleDesc);
        center.add(colorBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        right.setOpaque(false);
        right.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JButton gallery = createSecondaryButton("Gallery");
        JButton quiz = createPrimaryButton("Take Quiz Again");

        gallery.addActionListener(e -> new GalleryPage(currentUser));
        quiz.addActionListener(e -> {
            new QuizPage(currentUser);
            dispose();
        });

        right.add(gallery);
        right.add(quiz);

        panel.add(userLabel, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(right, BorderLayout.EAST);

        return panel;
    }

    /* ================= UI HELPERS ================= */
    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 230, 230));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    /* ================= TOGGLE (ON / OFF) ================= */
    private JToggleButton createToggle(String label, boolean selected, Runnable action) {
        JToggleButton toggle = new JToggleButton();
        toggle.setSelected(selected);
        toggle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        toggle.setFocusPainted(false);
        toggle.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        updateToggle(toggle, label);

        toggle.addActionListener(e -> {
            action.run();
            updateToggle(toggle, label);
        });

        return toggle;
    }

    private void updateToggle(JToggleButton toggle, String label) {
        if (toggle.isSelected()) {
            toggle.setText(label + ": ON");
            toggle.setBackground(new Color(76, 175, 80));
            toggle.setForeground(Color.WHITE);
        } else {
            toggle.setText(label + ": OFF");
            toggle.setBackground(new Color(200, 200, 200));
            toggle.setForeground(Color.BLACK);
        }
    }

    /* ===================== BRACELET PANEL ===================== */
    private static class BraceletPanel extends JPanel {

        private final List<Charm> charms;
        private double angle = 0;

        private boolean showNames = true;
        private boolean showDescriptions = true;

        private final int BASE_RADIUS_X = 210;
        private final int BASE_RADIUS_Y = 155;
        private final int BEADS = 36;
        private final int BEAD_SIZE = 18;
        private final int MAX_CHARM_SIZE = 96;

        private Color beadColor = new Color(180, 140, 80);

        BraceletPanel(List<Charm> charms) {
            this.charms = charms;
            setBackground(Color.WHITE);

            new Timer(16, e -> {
                angle += 0.01;
                repaint();
            }).start();
        }

        void toggleNames() {
            showNames = !showNames;
            repaint();
        }

        void toggleDescriptions() {
            showDescriptions = !showDescriptions;
            repaint();
        }

        Color getBeadColor() {
            return beadColor;
        }

        void setBeadColor(Color color) {
            beadColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            drawBeads(g2);
            drawCharms(g2);
        }

        private void drawBeads(Graphics2D g2) {
            int cx = getWidth() / 2;
            int cy = getHeight() / 2 - 20;

            for (int i = 0; i < BEADS; i++) {
                double a = angle + (2 * Math.PI / BEADS) * i;
                int x = (int) (cx + BASE_RADIUS_X * Math.cos(a));
                int y = (int) (cy + BASE_RADIUS_Y * Math.sin(a));
                g2.setColor(beadColor);
                g2.fillOval(x - BEAD_SIZE / 2, y - BEAD_SIZE / 2, BEAD_SIZE, BEAD_SIZE);
            }
        }

        private void drawCharms(Graphics2D g2) {
            if (charms.isEmpty()) return;

            int cx = getWidth() / 2;
            int cy = getHeight() / 2 - 20;

            for (int i = 0; i < charms.size(); i++) {
                Charm c = charms.get(i);
                double a = angle + (2 * Math.PI / charms.size()) * i;

                int radiusX = BASE_RADIUS_X + 85;
                int radiusY = BASE_RADIUS_Y + 85;

                ImageIcon icon = new ImageIcon(c.getImagePath());
                Image img = icon.getImage();

                int w = icon.getIconWidth();
                int h = icon.getIconHeight();

                if (w > MAX_CHARM_SIZE || h > MAX_CHARM_SIZE) {
                    float scale = Math.min((float) MAX_CHARM_SIZE / w, (float) MAX_CHARM_SIZE / h);
                    w *= scale;
                    h *= scale;
                }

                int x = (int) (cx + radiusX * Math.cos(a));
                int y = (int) (cy + radiusY * Math.sin(a));

                g2.drawImage(img, x - w / 2, y - h / 2, w, h, this);

                if (showNames) {
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    g2.setColor(Color.BLACK);
                    g2.drawString(c.getName(), x - w / 2, y + h + 16);
                }

                if (showDescriptions) {
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString(c.getDescription(), x - w / 2, y + h + 32);
                }
            }
        }
    }
}
