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
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        inventory();
        constraints.gridx = 0;
        constraints.gridy += 1;
        backToMainMenuButton();
        constraints.anchor = GridBagConstraints.WEST;
        heroInformation();
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 10;
        map();
    }

    // MODIFIES: this
    // EFFECTS: Generates the image of the hero based off it's class
    public void generateHeroImage() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 11;
        constraints.gridwidth = 6;
        try {
            // Buffering method from: https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
            BufferedImage fileImage = ImageIO.read(new File("./data/images/heroes/"
                    + hero.getHeroClass() + ".png"));
            heroImage = new JLabel(new ImageIcon(fileImage));
            heroImage.setBackground(Color.WHITE);
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

        constraints.gridx = 0;
        constraints.gridy = 11;
        inventoryLabel("Equipped:");
        constraints.gridx = 1;
        equipmentSlots(inv);
        constraints.gridx = 0;
        constraints.gridy = 12;
        inventoryLabel("Inventory:");
        constraints.gridx = 1;
        inventorySlots(inv);

    }

    // MODIFIES: this
    // EFFECTS: Displays a Jlabel for the slots in inventory
    public void inventoryLabel(String label) {
        JLabel slot = new JLabel(label);
        slot.setFont(new Font("slots", Font.BOLD, 24));
        slot.setForeground(Color.WHITE);
        add(slot,constraints);
    }

    // EFFECTS: Displays Jbutton accessories
    public void inventorySlots(Inventory inv) {
        for (Accessory a : inv.getInventorySlots()) {
            JButton b = generateAccessory(a.accessoryId);
            add(formattedEquipmentButton(b,a, false),constraints);
            constraints.gridx += 1;
        }

    }

    // EFFECTS: Displays Jbutton accessories
    public void equipmentSlots(Inventory inv) {
        for (Accessory a : inv.getEquipmentSlots()) {
            JButton b = generateAccessory(a.accessoryId);
            add(formattedEquipmentButton(b,a, true),constraints);
            constraints.gridx += 1;
        }
    }

    // EFFECTS: Returns a equipment JButton with necessary actions as
    public JButton formattedEquipmentButton(JButton b, Accessory a, Boolean equipped) {
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (equipped) {
                    unequip(a);
                } else {
                    equip(a);
                }
            }
        });
        return b;
    }

    // MODIFIES: this
    // EFFECTS: Equips this item if slots are not full
    public void equip(Accessory a) {
        if (hero.equipAccessory(a)) {
            updatePanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: Unequips this item if slots are not full
    public void unequip(Accessory a) {
        if (hero.unequipAccessory(a)) {
            updatePanel();
        }
    }

    // Image resizing method from: https://stackoverflow.com/questions/29011036/how-to-set-an-imageicon-to-a-jbutton-and-resize-the-picture-according-to-the-but
    // MODIFIES: this
    // EFFECTS: Renders the given accessory and returns it as a JButton
    public JButton generateAccessory(int id) {
        JButton b = new JButton("MissingImage");
        try {
            BufferedImage fileImage = ImageIO.read(new File("./data/images/accessories/"
                    + "accessory" + id + ".png"));
            ImageIcon icon = new ImageIcon(fileImage);
            Image rescaledImage = icon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
            b = new JButton(new ImageIcon(rescaledImage));
        } catch (IOException e) {
            System.out.println("accessory image not loading");
            e.printStackTrace();
        }
        return b;
    }

    // MODIFIES: this
    // EFFECTS: Displays all the hero information
    public void heroInformation() {
        constraints.gridx = 6;
        constraints.gridy = 0;
        statsLabel("Name: " + hero.getName());
        constraints.gridy = 1;
        statsLabel("Level: " + hero.getLevel());
        constraints.gridy = 2;
        statsLabel("Experience: " + hero.getExperience() + "/" + hero.getExperienceRequiredToLevel());
        constraints.gridy = 3;
        statsLabel("Class: " + hero.getHeroClass());
        constraints.gridy = 4;
        statsLabel("Health: " + hero.getMaxHealth());
        constraints.gridy = 5;
        statsLabel("Mana: " + hero.getMaxMana());
        heroAttributes();

    }

    // MODIFIES: this
    // EFFECTS: Displays the heros attributes with a button to increase
    public void heroAttributes() {
        constraints.gridy = 6;
        statsLabel("Strength: " + hero.getStrength());
        constraints.gridx += 1;
        formattedAttributeButton("strength");
        constraints.gridy = 7;
        constraints.gridx -= 1;
        statsLabel("Defence: " + hero.getDefence());
        constraints.gridx += 1;
        formattedAttributeButton("defence");
        constraints.gridy = 8;
        constraints.gridx -= 1;
        statsLabel("Agility: " + hero.getAgility());
        constraints.gridx += 1;
        formattedAttributeButton("agility");
        constraints.gridy = 9;
        constraints.gridx -= 1;
        statsLabel("Intelligence: " + hero.getIntelligence());
        constraints.gridx += 1;
        formattedAttributeButton("intelligence");
        constraints.gridx -= 1;
        constraints.gridy = 10;
        statsLabel("Skill Points: " + hero.getSkillPoints());
    }

    // MODIFIES: this
    // EFFECTS: Displays the button to increase strength
    public void increaseStrengthButton() {
    }

    // EFFECTS: Returns a formatted attribute button for given attribute
    public void formattedAttributeButton(String att) {
        JButton b = new JButton("+");
        b.setBackground(Color.LIGHT_GRAY);
        if (!hero.hasSkillPoints()) {
            add(b,constraints);
            return;
        }
        b.setBackground(Color.GREEN);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (att.equals("strength")) {
                    hero.increaseStrength();
                } else if (att.equals("defence")) {
                    hero.increaseDefence();
                } else if (att.equals("agility")) {
                    hero.increaseAgility();
                } else {
                    hero.increaseIntelligence();
                }
                updatePanel();
            }
        });
        add(b,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Reloads the world panel with changed data
    public void updatePanel() {
        frame.remove(this);
        frame.add(new WorldPanel(world, getWidth(),getHeight(),frame));
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays a label with the given text
    public void statsLabel(String text) {
        JLabel stat = new JLabel(text);
        stat.setForeground(Color.WHITE);
        stat.setFont(new Font("stats", Font.PLAIN, 18));
        add(stat,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the map options
    public void map() {

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
        frame.save();
        MainMenuPanel main = new MainMenuPanel(frame.getWorlds(), getWidth(), getHeight(),frame);
        frame.add(main.getPanel());
        frame.pack();
    }
}
