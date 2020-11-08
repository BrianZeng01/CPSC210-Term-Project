package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel {
    // https://digitalmoons.itch.io/parallax-forest-background?download
    // Image made by: Digital Moons
    private static final String BACKGROUND_FILE = "./data/images/mainMenuBackground.png";
    protected Image backgroundImage;
    private JPanel panel;

    public MainMenuPanel(Worlds worlds, int width, int height) {
        backgroundImage = Toolkit.getDefaultToolkit().getImage(BACKGROUND_FILE);
        // Paint methods from:https://www.tutorialspoint.com/how-to-add-background-image-to-jframe-in-java
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(width,height));
        init(worlds);
        panel.setVisible(true);
    }


    public void init(Worlds worlds) {
        panel.add(new JTextField("Select a world"));
        for (World w: worlds.getWorlds()) {
            JButton b = new JButton("World" + w.getWorldNumber());
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.add(new JTextField(w.getWorldName()));
                    panel.remove(b);
                }
            });
            panel.add(b);
        }
        if (!worlds.worldsIsFull()) {
            panel.add(new JTextField("Create new World"));
        } else {
            panel.add(new JTextField("Worlds at max capacity, delete a world to open space"));
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
