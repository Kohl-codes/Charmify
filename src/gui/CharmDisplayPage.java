package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;
import models.Charm;
import models.User;

public class CharmDisplayPage extends JFrame {

    private final BraceletPanel braceletPanel;
    private final User currentUser;
    private Color currentThemeColor = new Color(59, 89, 152); // Facebook blue theme
    private Timer pulseTimer;
    private float pulseScale = 1.0f;

    public CharmDisplayPage(User user) {
        this.currentUser = user;

        setTitle("✨ Charmify - Your Digital Bracelet");
        setSize(1000, 850);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 249, 250));

        // Initialize bracelet panel
        braceletPanel = new BraceletPanel(user.getCharms());

        // Add components
        add(createHeader(), BorderLayout.NORTH);
        add(braceletPanel, BorderLayout.CENTER);
        add(createBottomControls(), BorderLayout.SOUTH);

        // Add sidebar for charm details
        add(createSidebar(), BorderLayout.EAST);

        // Start pulse animation
        startPulseAnimation();

        setVisible(true);
    }

    /* ================= ENHANCED HEADER WITH ANIMATION ================= */
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1000, 70));
        panel.setBackground(currentThemeColor);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, 
            currentThemeColor.darker()));

        // Left side: Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);
        
        // Logo/Icon
        JLabel logo = new JLabel("✨");
        logo.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        
        JLabel title = new JLabel("CHARMIFY");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        leftPanel.add(logo);
        leftPanel.add(title);
        
        // Right side: User info and logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setOpaque(false);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(200, 230, 255));
        
        JButton logoutBtn = createModernButton("🚪 Logout", new Color(231, 76, 60));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            new LoginPage();
            dispose();
        });
        
        rightPanel.add(welcomeLabel);
        rightPanel.add(logoutBtn);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }

    /* ================= SIDEBAR FOR CHARM DETAILS ================= */
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(245, 247, 249));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, 
            new Color(230, 230, 230)));
        sidebar.setLayout(new BorderLayout());
        
        // Title
        JLabel sidebarTitle = new JLabel("Charm Collection", SwingConstants.CENTER);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sidebarTitle.setForeground(currentThemeColor);
        sidebarTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        
        // Stats panel
        JPanel statsPanel = createStatsPanel();
        
        // Charm list
        JScrollPane charmListScroll = createCharmList();
        
        sidebar.add(sidebarTitle, BorderLayout.NORTH);
        sidebar.add(statsPanel, BorderLayout.CENTER);
        sidebar.add(charmListScroll, BorderLayout.SOUTH);
        
        return sidebar;
    }
    
    private JPanel createStatsPanel() {
        JPanel stats = new JPanel(new GridLayout(4, 1, 0, 5));
        stats.setBackground(Color.WHITE);
        stats.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        List<Charm> charms = currentUser.getCharms();
        int uniqueCount = (int) charms.stream()
            .map(Charm::getName)
            .distinct()
            .count();
        
        JLabel totalLabel = createStatLabel("📊 Total Charms", String.valueOf(charms.size()));
        JLabel uniqueLabel = createStatLabel("⭐ Unique", String.valueOf(uniqueCount));
        JLabel completionLabel = createStatLabel("🎯 Collection", 
            String.format("%.0f%%", (uniqueCount / 12.0) * 100));
        JLabel lastQuizLabel = createStatLabel("📅 Last Quiz", "Today");
        
        stats.add(totalLabel);
        stats.add(uniqueLabel);
        stats.add(completionLabel);
        stats.add(lastQuizLabel);
        
        return stats;
    }
    
    private JScrollPane createCharmList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<Charm> charms = currentUser.getCharms();
        
        // Get unique charms for display
        charms.stream()
            .map(Charm::getName)
            .distinct()
            .forEach(model::addElement);
        
        JList<String> charmList = new JList<>(model);
        charmList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        charmList.setBackground(new Color(250, 250, 250));
        charmList.setSelectionBackground(currentThemeColor.brighter());
        charmList.setSelectionForeground(Color.WHITE);
        charmList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        charmList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = charmList.getSelectedValue();
                if (selected != null) {
                    braceletPanel.highlightCharm(selected);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(charmList);
        scrollPane.setPreferredSize(new Dimension(250, 200));
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, 
            new Color(230, 230, 230)));
        
        return scrollPane;
    }
    
    private JLabel createStatLabel(String title, String value) {
        JPanel stat = new JPanel(new BorderLayout());
        stat.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(currentThemeColor);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        stat.add(titleLabel, BorderLayout.WEST);
        stat.add(valueLabel, BorderLayout.EAST);
        
        JLabel container = new JLabel();
        container.setLayout(new BorderLayout());
        container.add(stat);
        return container;
    }

    /* ================= ENHANCED BOTTOM CONTROLS ================= */
    private JPanel createBottomControls() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1000, 100));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Left: Controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        controlPanel.setOpaque(false);
        
        // Speed control
        JLabel speedLabel = new JLabel("Speed:");
        speedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JSlider speedSlider = new JSlider(1, 10, 5);
        speedSlider.setPreferredSize(new Dimension(120, 30));
        speedSlider.addChangeListener(e -> {
            braceletPanel.setRotationSpeed(speedSlider.getValue() / 10.0);
        });
        
        // Toggle buttons - FIXED: Pass state to bracelet panel
        JToggleButton namesToggle = createModernToggle("Names", true);
        namesToggle.addActionListener(e -> {
            braceletPanel.setShowNames(namesToggle.isSelected());
        });
        
        JToggleButton descToggle = createModernToggle("Descriptions", true);
        descToggle.addActionListener(e -> {
            braceletPanel.setShowDescriptions(descToggle.isSelected());
        });
        
        JToggleButton pulseToggle = createModernToggle("Pulse", true);
        pulseToggle.addActionListener(e -> {
            togglePulseAnimation();
        });
        
        // Color picker button
        JButton colorBtn = createModernButton("🎨 Bead Color", 
            new Color(103, 58, 183));
        colorBtn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(
                this, "Choose Bead Color", braceletPanel.getBeadColor());
            if (chosen != null) {
                braceletPanel.setBeadColor(chosen);
            }
        });
        
        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(namesToggle);
        controlPanel.add(descToggle);
        controlPanel.add(pulseToggle);
        controlPanel.add(colorBtn);
        
        // Right: Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        
        JButton galleryBtn = createModernButton("🖼️ Gallery", 
            new Color(76, 175, 80));
        JButton quizBtn = createModernButton("✨ New Quiz", 
            new Color(255, 193, 7));
        
        galleryBtn.addActionListener(e -> new GalleryPage(currentUser));
        quizBtn.addActionListener(e -> {
            new QuizPage(currentUser);
            dispose();
        });
        
        actionPanel.add(galleryBtn);
        actionPanel.add(quizBtn);
        
        panel.add(controlPanel, BorderLayout.WEST);
        panel.add(actionPanel, BorderLayout.EAST);
        
        return panel;
    }

    /* ================= MODERN UI COMPONENTS ================= */
    private JButton createModernButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    private JToggleButton createModernToggle(String label, boolean selected) {
        JToggleButton toggle = new JToggleButton();
        toggle.setSelected(selected);
        toggle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        toggle.setFocusPainted(false);
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        updateModernToggle(toggle, label);
        
        toggle.addActionListener(e -> {
            updateModernToggle(toggle, label);
        });
        
        return toggle;
    }
    
    private void updateModernToggle(JToggleButton toggle, String label) {
        if (toggle.isSelected()) {
            toggle.setText("✓ " + label);
            toggle.setBackground(new Color(76, 175, 80));
            toggle.setForeground(Color.WHITE);
            toggle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(56, 155, 70), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
            ));
        } else {
            toggle.setText("✗ " + label);
            toggle.setBackground(new Color(230, 230, 230));
            toggle.setForeground(Color.BLACK);
            toggle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
            ));
        }
    }
    
    /* ================= ANIMATION & THEME ================= */
    private void startPulseAnimation() {
        pulseTimer = new Timer(50, e -> {
            pulseScale = 0.95f + (float) Math.sin(System.currentTimeMillis() * 0.003) * 0.05f;
            braceletPanel.setPulseScale(pulseScale);
        });
        pulseTimer.start();
    }
    
    private void togglePulseAnimation() {
        if (pulseTimer.isRunning()) {
            pulseTimer.stop();
            braceletPanel.setPulseScale(1.0f);
        } else {
            pulseTimer.start();
        }
    }

    /* ================= ENHANCED BRACELET PANEL ================= */
    private class BraceletPanel extends JPanel {

        private final List<Charm> charms;
        private double angle = 0;
        private double rotationSpeed = 0.5;
        private float pulseScale = 1.0f;
        private Color beadColor = new Color(180, 140, 80);
        private Color themeColor = new Color(59, 89, 152);
        private String highlightedCharm = null;
        
        // FIXED: Add state variables for toggles
        private boolean showNames = true;
        private boolean showDescriptions = true;
        
        private final int BASE_RADIUS_X = 220;
        private final int BASE_RADIUS_Y = 165;
        private final int BEADS = 40;
        private final int BEAD_SIZE = 14;
        private final int CHARM_SIZE = 80;
        
        BraceletPanel(List<Charm> charms) {
            this.charms = charms;
            setBackground(new Color(250, 252, 255));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Animation timer
            new Timer(16, e -> {
                angle += 0.01 * rotationSpeed;
                repaint();
            }).start();
            
            // Mouse wheel for zoom
            addMouseWheelListener(e -> {
                rotationSpeed = Math.max(0.1, Math.min(2.0, 
                    rotationSpeed + e.getWheelRotation() * 0.1));
            });
        }
        
        // FIXED: Setter methods for toggle states
        void setShowNames(boolean show) { 
            this.showNames = show; 
            repaint();
        }
        
        void setShowDescriptions(boolean show) { 
            this.showDescriptions = show; 
            repaint();
        }
        
        void setRotationSpeed(double speed) { rotationSpeed = speed; }
        void setPulseScale(float scale) { pulseScale = scale; repaint(); }
        void setBeadColor(Color color) { beadColor = color; repaint(); }
        void setThemeColor(Color color) { themeColor = color; repaint(); }
        Color getBeadColor() { return beadColor; }
        
        void highlightCharm(String charmName) {
            highlightedCharm = charmName;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            // Enable anti-aliasing for smooth rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_QUALITY);
            
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            drawBraceletShadow(g2, centerX, centerY);
            drawBeads(g2, centerX, centerY);
            drawCharms(g2, centerX, centerY);
            
            // Draw instruction text if no charms
            if (charms.isEmpty()) {
                drawEmptyState(g2, centerX, centerY);
            }
        }
        
        private void drawBraceletShadow(Graphics2D g2, int cx, int cy) {
            // Outer glow
            g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), 
                themeColor.getBlue(), 20));
            for (int i = 0; i < 10; i++) {
                g2.drawOval(cx - BASE_RADIUS_X - 5 + i, cy - BASE_RADIUS_Y - 5 + i,
                    2 * (BASE_RADIUS_X + 5 - i), 2 * (BASE_RADIUS_Y + 5 - i));
            }
            
            // Bracelet chain
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int i = 0; i < 360; i += 15) {
                double rad = Math.toRadians(i + angle * 10);
                int x = (int) (cx + BASE_RADIUS_X * Math.cos(rad));
                int y = (int) (cy + BASE_RADIUS_Y * Math.sin(rad));
                
                GradientPaint gradient = new GradientPaint(
                    x - 2, y - 2, beadColor.darker(),
                    x + 2, y + 2, beadColor.brighter(),
                    true
                );
                g2.setPaint(gradient);
                g2.fillOval(x - 2, y - 2, 4, 4);
            }
        }
        
        private void drawBeads(Graphics2D g2, int cx, int cy) {
            for (int i = 0; i < BEADS; i++) {
                double a = angle + (2 * Math.PI / BEADS) * i;
                int x = (int) (cx + BASE_RADIUS_X * Math.cos(a));
                int y = (int) (cy + BASE_RADIUS_Y * Math.sin(a));
                
                // Create shiny bead effect
                RadialGradientPaint gradient = new RadialGradientPaint(
                    x, y, BEAD_SIZE / 2,
                    new float[]{0.0f, 1.0f},
                    new Color[]{beadColor.brighter(), beadColor.darker()}
                );
                
                g2.setPaint(gradient);
                g2.fillOval(x - BEAD_SIZE / 2, y - BEAD_SIZE / 2, BEAD_SIZE, BEAD_SIZE);
                
                // Bead highlight
                g2.setColor(new Color(255, 255, 255, 150));
                g2.fillOval(x - BEAD_SIZE / 4, y - BEAD_SIZE / 4, 
                    BEAD_SIZE / 2, BEAD_SIZE / 2);
            }
        }
        
        private void drawCharms(Graphics2D g2, int cx, int cy) {
            if (charms.isEmpty()) return;
            
            for (int i = 0; i < charms.size(); i++) {
                Charm charm = charms.get(i);
                double a = angle + (2 * Math.PI / charms.size()) * i;
                
                // Elliptical orbit with vertical offset for 3D effect
                int radiusX = BASE_RADIUS_X + 100;
                int radiusY = BASE_RADIUS_Y + 100;
                int x = (int) (cx + radiusX * Math.cos(a));
                int y = (int) (cy + radiusY * Math.sin(a) + Math.sin(a * 2) * 10);
                
                // Draw charm with pulse effect
                drawCharmImage(g2, charm, x, y, i);
                
                // Draw labels if enabled
                drawCharmLabels(g2, charm, x, y);
            }
        }
        
        private void drawCharmImage(Graphics2D g2, Charm charm, int x, int y, int index) {
            try {
                ImageIcon icon = new ImageIcon(charm.getImagePath());
                Image img = icon.getImage();
                
                // Calculate size with pulse effect
                int size = (int) (CHARM_SIZE * pulseScale);
                if (charm.getName().equals(highlightedCharm)) {
                    size = (int) (CHARM_SIZE * 1.2); // Enlarge highlighted charm
                }
                
                // Apply rotation based on position
                AffineTransform oldTransform = g2.getTransform();
                double rotation = angle + index * 0.5;
                g2.rotate(rotation, x, y);
                
                // Draw glow for highlighted charm
                if (charm.getName().equals(highlightedCharm)) {
                    g2.setColor(new Color(themeColor.getRed(), themeColor.getGreen(), 
                        themeColor.getBlue(), 100));
                    g2.fillOval(x - size/2 - 5, y - size/2 - 5, size + 10, size + 10);
                }
                
                // Draw charm image
                g2.drawImage(img, x - size/2, y - size/2, size, size, this);
                g2.setTransform(oldTransform);
                
                // Draw decorative ring
                g2.setColor(themeColor);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(x - size/2 - 2, y - size/2 - 2, size + 4, size + 4);
                
            } catch (Exception e) {
                // Fallback charm visualization
                g2.setColor(themeColor);
                g2.fillOval(x - CHARM_SIZE/2, y - CHARM_SIZE/2, CHARM_SIZE, CHARM_SIZE);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                g2.drawString("✨", x - 10, y + 8);
            }
        }
        
        private void drawCharmLabels(Graphics2D g2, Charm charm, int x, int y) {
            // FIXED: Only draw labels if they're enabled
            if (!showNames && !showDescriptions) {
                return;
            }
            
            int labelY = y + CHARM_SIZE/2 + 25;
            
            // Draw name if enabled
            if (showNames) {
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                String name = charm.getName();
                
                // Shadow
                g2.setColor(new Color(0, 0, 0, 100));
                g2.drawString(name, x - g2.getFontMetrics().stringWidth(name)/2 + 1, 
                    labelY + 1);
                
                // Text
                g2.setColor(charm.getName().equals(highlightedCharm) ? 
                    themeColor.darker() : Color.BLACK);
                g2.drawString(name, x - g2.getFontMetrics().stringWidth(name)/2, labelY);
                
                // Move position down for description
                labelY += 18;
            }
            
            // Draw description if enabled
            if (showDescriptions) {
                g2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
                String desc = charm.getDescription();
                if (desc.length() > 30) {
                    desc = desc.substring(0, 27) + "...";
                }
                
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(desc, x - g2.getFontMetrics().stringWidth(desc)/2, 
                    labelY);
            }
        }
        
        private void drawEmptyState(Graphics2D g2, int cx, int cy) {
            g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
            g2.setColor(new Color(200, 200, 200));
            
            String message = "✨ No Charms Yet ✨";
            int textWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, cx - textWidth/2, cy - 50);
            
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            message = "Take the quiz to discover your first charm!";
            textWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, cx - textWidth/2, cy);
            
            // Draw a ghost bracelet
            g2.setColor(new Color(230, 230, 230));
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 1, new float[]{5, 5}, 0));
            g2.drawOval(cx - BASE_RADIUS_X, cy - BASE_RADIUS_Y, 
                2 * BASE_RADIUS_X, 2 * BASE_RADIUS_Y);
        }
    }
}