package gui;

import javax.swing.*;

public class GalleryPage extends JFrame {

    public GalleryPage() {
        setTitle("Charm Gallery");
        setSize(400, 400);
        setLayout(null);

        JLabel label = new JLabel("Your Charm Collection");
        label.setBounds(100, 50, 200, 30);
        add(label);

        setVisible(true);
    }
}
