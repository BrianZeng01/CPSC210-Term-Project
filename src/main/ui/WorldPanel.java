package ui;

import model.Accessory;
import model.Hero;
import model.Inventory;
import model.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WorldPanel extends JPanel {
    private World world;
    private Hero hero;
    private MyGame frame;
    protected GridBagConstraints constraints;
    protected JLabel heroImage;

    public WorldPanel(World world, int width, int height, MyGame frame) {
        this.world = world;
        this.hero = world.getHero();
        this.frame = frame;
        this.constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(width, height));

        constraints.insets = new Insets(10,10,10,10);
        generateHeroImage();
        constraints.gridy = 1;
        inventory();
        constraints.gridx = 2;
        backToMainMenuButton();
        constraints.gridx = 1;
        constraints.gridy = 0;
        heroInformation();
        constraints.gridy = 1;
        map();
    }

    // MODIFIES: this
    // EFFECTS: Generates the image of the hero based off it's class
    public void generateHeroImage() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        try {
            // Buffering method from: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
            BufferedImage fileImage = ImageIO.read(new File("./data/images/heroes/"
                    + hero.getHeroClass() + ".png"));
            heroImage = new JLabel(new ImageIcon(fileImage));
            add(heroImage,constraints);
        } catch (IOException e) {
            System.out.println("Hero image not loading");
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the inventory
    public void inventory() {
        Inventory inv = hero.getInventory();
        constraints.gridy = 1;
        ArrayList<JButton> inventorySlots = inventorySlots(inv);
//        constraints.gridy = 2;
//        ArrayList<JButton> equipmentSlots = equipmentSlots(inv);

    }

    // EFFECTS: Returns a list of Jbutton accessories
    public ArrayList<JButton> inventorySlots(Inventory inv) {
        ArrayList<JButton> buttons = new ArrayList<>();
        for (Accessory a : inv.getInventorySlots()) {
            JButton b = generateAccessory(a.accessoryId);
            add(b,constraints);
            constraints.gridx += 1;
        }

        return buttons;
    }

    // EFFECTS: Returns a list of Jbutton accessories
    public ArrayList<JButton> equipmentSlots(Inventory inv) {
        ArrayList<JButton> buttons = new ArrayList<>();
        for (Accessory a : inv.getEquipmentSlots()) {
            JButton b = generateAccessory(a.accessoryId);
            buttons.add(formattedEquipmentButton(b));
        }

        return buttons;
    }

    // EFFECTS: Returns a equipment JButton with necessary actions as
    public JButton formattedEquipmentButton(JButton b) {
        return b;
    }

    // MODIFIES: this
    // EFFECTS: Renders the given accessory and returns it as a JButton
    public JButton generateAccessory(int id) {
        JButton b = new JButton("MissingImage");
        try {
            BufferedImage fileImage = ImageIO.read(new File("./data/images/accessories/"
                    + "accessory" + id + ".png"));
            b = new JButton(new ImageIcon(fileImage));
        } catch (IOException e) {
            System.out.println("accessory image not loading");
            e.printStackTrace();
        }
        return b;
    }

    // MODIFIES: this
    // EFFECTS: Displays all the hero information
    public void heroInformation() {
        return;
    }

    // MODIFIES: this
    // EFFECTS: Displays the map options
    public void map() {
        return;
    }


    // MODIFIES: this
    // EFFECTS: Displays the button to go back to main menu
    public void backToMainMenuButton() {
        JButton b = new JButton("Back to Main Menu");
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("main", Font.BOLD, 32));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainMenu();
            }
        });
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setForeground(Color.WHITE);
            }
        });
        add(b,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Goes back to the main menu by changing panels on frame
    public void backToMainMenu() {
        frame.remove(this);
        MainMenuPanel main = new MainMenuPanel(frame.getWorlds(), getWidth(), getHeight(),frame);
        frame.add(main.getPanel());
        frame.pack();
    }
}
