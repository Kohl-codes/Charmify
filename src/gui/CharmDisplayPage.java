package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import models.Charm;
import models.User;

public class CharmDisplayPage extends JFrame {

    private User currentUser;
    private List<Charm> charms;
    private Map<Charm, CharmDisplay> charmDisplays;
    private boolean showNames = true;
    private boolean showDescriptions = true;
    private boolean isAnimating = true;

    private double angle = 0;
    private int centerX = 325, centerY = 250;
    private int braceletRadius = 140;
    private final int BEAD_COUNT = 24;
    
    // Colors
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color BRACELET_COLOR = new Color(184, 134, 11); // Gold
    private final Color BRACELET_SHADOW = new Color(139, 101, 8);
    private final Color BEAD_COLOR = new Color(255, 215, 0);
    private final Color PANEL_COLOR = new Color(255, 255, 255);

    public CharmDisplayPage(User user) {
        this.currentUser = user;
        this.charmDisplays = new HashMap<>();
        
        // Get unique charms
        this.charms = getUniqueCharms(user.getCharms());
        
        setTitle("Your 3D Charm Bracelet");
        setSize(700, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);

        setupHeader();
        setupControlPanel();
        setupCharmDisplays();
        setupInfoPanel();
        
        if (charms.isEmpty()) {
            showEmptyState();
        } else {
            startAnimation();
        }
        
        setVisible(true);
    }
    
    private List<Charm> getUniqueCharms(List<Charm> allCharms) {
        List<Charm> unique = new ArrayList<>();
        Map<String, Charm> seen = new HashMap<>();
        
        for (Charm charm : allCharms) {
            if (!seen.containsKey(charm.getName())) {
                seen.put(charm.getName(), charm);
                unique.add(charm);
            }
        }
        return unique;
    }
    
    private void setupHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 10, 700, 60);
        headerPanel.setBackground(new Color(63, 81, 181));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("✨ Your Charm Bracelet ✨", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        headerPanel.add(title, BorderLayout.CENTER);
        
        add(headerPanel);
    }
    
    private void setupControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(50, 520, 600, 50);
        controlPanel.setBackground(PANEL_COLOR);
        controlPanel.setLayout(new GridLayout(1, 4, 10, 0));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Toggle Names Button
        JButton toggleNamesBtn = createControlButton("👁️ Toggle Names", new Color(63, 81, 181));
        toggleNamesBtn.addActionListener(e -> {
            showNames = !showNames;
            updateCharmVisibility();
            toggleNamesBtn.setText(showNames ? "👁️ Hide Names" : "👁️ Show Names");
        });
        
        // Toggle Descriptions Button
        JButton toggleDescBtn = createControlButton("📝 Toggle Desc", new Color(76, 175, 80));
        toggleDescBtn.addActionListener(e -> {
            showDescriptions = !showDescriptions;
            updateCharmVisibility();
            toggleDescBtn.setText(showDescriptions ? "📝 Hide Desc" : "📝 Show Desc");
        });
        
        // Animation Toggle
        JButton animationBtn = createControlButton("⏸️ Pause", new Color(255, 152, 0));
        animationBtn.addActionListener(e -> {
            isAnimating = !isAnimating;
            animationBtn.setText(isAnimating ? "⏸️ Pause" : "▶️ Play");
        });
        
        // View Gallery Button
        JButton galleryBtn = createControlButton("🖼️ Gallery", new Color(156, 39, 176));
        galleryBtn.addActionListener(e -> new GalleryPage(currentUser));
        
        controlPanel.add(toggleNamesBtn);
        controlPanel.add(toggleDescBtn);
        controlPanel.add(animationBtn);
        controlPanel.add(galleryBtn);
        
        add(controlPanel);
    }
    
    private JButton createControlButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void setupCharmDisplays() {
        for (int i = 0; i < charms.size(); i++) {
            Charm charm = charms.get(i);
            CharmDisplay display = new CharmDisplay(charm);
            charmDisplays.put(charm, display);
            
            // Add components to frame
            add(display.imageLabel);
            add(display.nameLabel);
            add(display.descLabel);
        }
    }
    
    private void setupInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(50, 580, 600, 60);
        infoPanel.setBackground(PANEL_COLOR);
        infoPanel.setLayout(new GridLayout(2, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // Unique Charms Count
        JLabel uniqueLabel = new JLabel("Unique Charms: " + charms.size());
        uniqueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        uniqueLabel.setForeground(new Color(63, 81, 181));
        
        // Total Charms Count
        JLabel totalLabel = new JLabel("Total Earned: " + currentUser.getCharms().size());
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalLabel.setForeground(Color.DARK_GRAY);
        
        // Username
        JLabel userLabel = new JLabel("User: " + currentUser.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(76, 175, 80));
        
        // Take Quiz Button
        JButton quizBtn = new JButton("🔄 Take Quiz Again");
        quizBtn.setBackground(new Color(92, 107, 192));
        quizBtn.setForeground(Color.WHITE);
        quizBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        quizBtn.setFocusPainted(false);
        quizBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        quizBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quizBtn.addActionListener(e -> {
            new QuizPage(currentUser);
            dispose();
        });
        
        infoPanel.add(uniqueLabel);
        infoPanel.add(userLabel);
        infoPanel.add(totalLabel);
        infoPanel.add(quizBtn);
        
        add(infoPanel);
    }
    
    private void showEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBounds(150, 200, 400, 200);
        emptyPanel.setBackground(PANEL_COLOR);
        emptyPanel.setLayout(new BorderLayout());
        emptyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel emptyLabel = new JLabel("<html><center>✨ No Charms Yet! ✨<br><br>" +
                                      "Take the personality quiz to earn<br>" +
                                      "your first magical charm!</center></html>", 
                                      SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emptyLabel.setForeground(new Color(100, 100, 100));
        
        JButton startQuizBtn = new JButton("Start Quiz Now");
        startQuizBtn.setBackground(new Color(76, 175, 80));
        startQuizBtn.setForeground(Color.WHITE);
        startQuizBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startQuizBtn.setFocusPainted(false);
        startQuizBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        startQuizBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startQuizBtn.addActionListener(e -> {
            new QuizPage(currentUser);
            dispose();
        });
        
        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        emptyPanel.add(startQuizBtn, BorderLayout.SOUTH);
        
        add(emptyPanel);
    }
    
    private void updateCharmVisibility() {
        for (CharmDisplay display : charmDisplays.values()) {
            display.nameLabel.setVisible(showNames);
            display.descLabel.setVisible(showDescriptions);
        }
    }
    
    private void startAnimation() {
        Timer timer = new Timer(30, e -> {
            if (!isAnimating) return;
            
            angle += 0.02;
            
            for (int i = 0; i < charms.size(); i++) {
                Charm charm = charms.get(i);
                CharmDisplay display = charmDisplays.get(charm);
                
                // Calculate position with smooth orbiting
                double angleOffset = (2 * Math.PI / charms.size()) * i;
                double currentAngle = angle + angleOffset;
                
                // Add slight vertical movement for 3D effect
                double verticalOffset = Math.sin(currentAngle * 2) * 10;
                
                int x = (int) (centerX + braceletRadius * Math.cos(currentAngle));
                int y = (int) (centerY + braceletRadius * Math.sin(currentAngle) + verticalOffset);
                
                // Update positions
                display.imageLabel.setLocation(x - 40, y - 40);
                display.nameLabel.setLocation(x - 50, y + 25);
                display.descLabel.setLocation(x - 75, y + 45);
                
                // Add subtle scaling effect
                double scale = 0.9 + 0.1 * Math.sin(currentAngle);
                display.imageLabel.setSize((int)(80 * scale), (int)(80 * scale));
            }
            
            repaint();
        });
        timer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (charms.isEmpty()) return;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawBracelet(g2);
        drawBeads(g2);
    }
    
    private void drawBracelet(Graphics2D g2) {
        // Draw bracelet shadow
        g2.setColor(new Color(0, 0, 0, 30));
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(centerX - braceletRadius + 3, centerY - braceletRadius + 3, 
                    2 * braceletRadius, 2 * braceletRadius);
        
        // Draw 3D bracelet with gradient
        for (int i = 0; i < 15; i++) {
            float intensity = 0.7f - (i / 15f) * 0.4f;
            Color shadeColor = new Color(
                (int)(BRACELET_COLOR.getRed() * intensity),
                (int)(BRACELET_COLOR.getGreen() * intensity),
                (int)(BRACELET_COLOR.getBlue() * intensity)
            );
            
            g2.setColor(shadeColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(centerX - braceletRadius + i, centerY - braceletRadius + i, 
                        2 * (braceletRadius - i), 2 * (braceletRadius - i));
        }
    }
    
    private void drawBeads(Graphics2D g2) {
        for (int i = 0; i < BEAD_COUNT; i++) {
            double beadAngle = (2 * Math.PI / BEAD_COUNT) * i + angle;
            int beadX = (int) (centerX + braceletRadius * Math.cos(beadAngle));
            int beadY = (int) (centerY + braceletRadius * Math.sin(beadAngle));
            
            // Draw bead shadow
            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillOval(beadX - 3, beadY + 2, 10, 10);
            
            // Draw bead with gradient
            GradientPaint gradient = new GradientPaint(
                beadX - 5, beadY - 5, BEAD_COLOR.brighter(),
                beadX + 5, beadY + 5, BEAD_COLOR.darker(),
                true
            );
            g2.setPaint(gradient);
            g2.fillOval(beadX - 5, beadY - 5, 10, 10);
            
            // Draw bead highlight
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillOval(beadX - 3, beadY - 3, 4, 4);
        }
    }
    
    // Inner class to manage charm display components
    private class CharmDisplay {
        JLabel imageLabel;
        JLabel nameLabel;
        JLabel descLabel;
        
        CharmDisplay(Charm charm) {
            // Create image label
            try {
                ImageIcon originalIcon = new ImageIcon(charm.getImagePath());
                Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                imageLabel = new JLabel(icon);
            } catch (Exception e) {
                imageLabel = new JLabel("✨");
                imageLabel.setFont(new Font("Arial", Font.BOLD, 24));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageLabel.setOpaque(true);
                imageLabel.setBackground(new Color(240, 240, 240));
                imageLabel.setForeground(new Color(180, 180, 180));
            }
            
            imageLabel.setSize(80, 80);
            imageLabel.setToolTipText("<html><b>" + charm.getName() + "</b><br>" + 
                                     charm.getDescription() + "</html>");
            
            // Create name label
            nameLabel = new JLabel(charm.getName(), SwingConstants.CENTER);
            nameLabel.setSize(100, 25);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
            nameLabel.setForeground(new Color(50, 50, 50));
            
            // Create description label
            descLabel = new JLabel("<html><center>" + charm.getDescription() + "</center></html>", 
                                  SwingConstants.CENTER);
            descLabel.setSize(150, 40);
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            descLabel.setForeground(new Color(100, 100, 100));
        }
    }
}