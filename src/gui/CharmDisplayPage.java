package gui;

import java.util.List;
import javax.swing.*;

public class CharmDisplayPage extends JFrame {

    private List<String> charms;  // list of charm image names
    private JLabel[] charmLabels;
    private double angle = 0;

    private int centerX = 250;
    private int centerY = 250;
    private int radius = 100;

    public CharmDisplayPage(List<String> charms) {
        this.charms = charms;

        setTitle("Your Charm Bracelet");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        charmLabels = new JLabel[charms.size()];

        // Add all charms as JLabels
        for (int i = 0; i < charms.size(); i++) {
            ImageIcon icon = new ImageIcon("assets/charms/" + charms.get(i));
            JLabel lbl = new JLabel(icon);
            lbl.setSize(60, 60);
            add(lbl);
            charmLabels[i] = lbl;
        }

        startAnimation();

        setVisible(true);
    }

    private void startAnimation() {
        Timer timer = new Timer(30, e -> {
            double angleStep = 2 * Math.PI / charms.size();  // spacing each charm evenly

            for (int i = 0; i < charms.size(); i++) {
                int x = (int) (centerX + radius * Math.cos(angle + i * angleStep) - charmLabels[i].getWidth() / 2);
                int y = (int) (centerY + radius * Math.sin(angle + i * angleStep) - charmLabels[i].getHeight() / 2);

                charmLabels[i].setLocation(x, y);
            }

            angle += 0.02; // speed of rotation
        });

        timer.start();
    }
}
