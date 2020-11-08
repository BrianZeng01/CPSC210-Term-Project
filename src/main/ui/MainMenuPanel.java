package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The main menu panel for this game
public class MainMenuPanel {
    // https://digitalmoons.itch.io/parallax-forest-background?download
    // Image made by: Digital Moons
    private static final String BACKGROUND_FILE = "./data/images/mainMenuBackground.png";
    protected Image backgroundImage;
    private JPanel panel;
    private GridBagConstraints constraints = new GridBagConstraints();
    private Worlds worlds;

    public MainMenuPanel(Worlds worlds, int width, int height) {
        backgroundImage = Toolkit.getDefaultToolkit().getImage(BACKGROUND_FILE);
        this.worlds = worlds;
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
        init();
        panel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the display of the main menu
    public void init() {
        constraints.gridwidth = 4;
        addTitle();
        constraints.gridwidth = 1;
        constraints.insets = new Insets(10,10,10,10);
        addWorldSelection();
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        addWorldCreation();
        test();
        addApplicationExit();


    }

    public void test() {
        JButton b = new JButton("Test");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.add(new CreateWorldPanel(worlds, panel.getWidth(), panel.getHeight()));
                panel.updateUI();
            }
        });
        panel.add(b);
    }

    // MODIFIES: this
    // EFFECTS: Displays the main menu title
    public void addTitle() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel label = new JLabel("Game Title");
        label.setFont(new Font("title",3,96));
        label.setForeground(new Color(178,102,255));
        panel.add(label,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays all the saved worlds
    public void addWorldSelection() {
        int height = 2;
        for (World w: worlds.getWorlds()) {
            constraints.gridx = 0;
            constraints.gridy = height;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.EAST;
            constraints.weightx = 1;
            height++;
            panel.add(selectWorldButton(w),constraints);

            constraints.gridx = 2;
            constraints.gridwidth = 1;
            constraints.anchor = GridBagConstraints.WEST;
            panel.add(deleteWorldButton(w.getWorldNumber()), constraints);
        }
    }

    // EFFECTS: Returns a formatted button for world selection options
    public JButton selectWorldButton(World w) {
        JButton b = new JButton(w.getWorldName());
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setForeground(new Color(153,51,255));
        b.setFont(new Font(w.getWorldName(), Font.PLAIN,40));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        // Hover effect copied from :https://stackoverflow.com/questions/22638926/how-to-put-hover-effect-on-jbutton
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setFont(new Font(w.getWorldName(), 3,40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setFont(new Font(w.getWorldName(), Font.PLAIN,40));
            }
        });

        return b;
    }

    // MODIFIES: this
    // EFFECTS: Displays a delete button beside each world
    public JButton deleteWorldButton(int worldNumber) {
        JButton b = new JButton("X");
        b.setForeground(Color.gray);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("delete" + worldNumber, Font.BOLD, 44));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setForeground(new Color(255,51,51));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setForeground(Color.gray);
            }
        });
        return b;
    }

    // MODIFIES this:
    // EFFECTS: Displays the correct message/button for world creation
    public void addWorldCreation() {
        constraints.gridy = 5;
        if (!worlds.worldsIsFull()) {
            JButton create = new JButton("Create new World");
            create.setForeground(Color.WHITE);
            create.setBorderPainted(false);
            create.setFocusPainted(false);
            create.setContentAreaFilled(false);
            create.setFont(new Font("createWorld", Font.BOLD, 44));
            panel.add(create,constraints);
        } else {
            JLabel create = new JLabel("Worlds at max Capacity, delete a world to create more!");
            create.setFont(new Font("createWorldFull", Font.BOLD, 22));
            create.setForeground(Color.BLACK);
            panel.add(create,constraints);
        }
    }



    // MODIFIES: this
    // EFFECTS: Closes and saves the application
    public void addApplicationExit() {
        constraints.gridy = 6;
        JButton b = new JButton("Save and Exit");
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("exit", Font.BOLD, 44));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("exit", 3, 44));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("exit", Font.BOLD, 44));
            }
        });
        panel.add(b,constraints);
    }

    public JPanel getPanel() {
        return panel;
    }
}
