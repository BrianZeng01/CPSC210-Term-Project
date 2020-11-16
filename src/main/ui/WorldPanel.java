package ui;

import model.*;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents the main screen when entering world
public class WorldPanel extends JPanel {
    private static final String MONSTERS_FILE = "./data/monsters.json";
    private World world;
    private Hero hero;
    private MyGame frame;
    protected GridBagConstraints constraints;
    protected int selectedRound;
    protected JLabel heroImage;

    // Creates a new world Panel
    public WorldPanel(World world, int width, int height, MyGame frame) {
        this.world = world;
        this.selectedRound = 1;
        this.hero = world.getHero();
        this.frame = frame;
        this.constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(width, height));

        init();
    }

    // MODIFIES: this
    // EFFECTS: Intializes the panel's components
    public void init() {
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
        constraints.gridx = 8;
        constraints.gridy = 0;
        constraints.insets = new Insets(10,40,10,10);
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
            BufferedImage fileImage = ImageIO.read(new File("./res/images/heroes/"
                    + hero.getHeroClass() + ".png"));
            ImageIcon icon = new ImageIcon(fileImage);
            Image rescaledImage = icon.getImage().getScaledInstance(600,400, Image.SCALE_SMOOTH);
            heroImage = new JLabel(new ImageIcon(rescaledImage));
            heroImage.setBackground(new Color(161, 95, 227));
            heroImage.setOpaque(true);
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
        constraints.gridx += 1;
        JLabel hp = new JLabel("HP Potions:" + inv.getHealthPotions());
        hp.setForeground(Color.WHITE);
        add(hp, constraints);
        constraints.gridx += 1;
        JLabel mp = new JLabel("MP Potions:" + inv.getManaPotions());
        mp.setForeground(Color.WHITE);
        add(mp,constraints);

    }

    // MODIFIES: this
    // EFFECTS: Displays a Jlabel for the slots in inventory
    public void inventoryLabel(String label) {
        JLabel slot = new JLabel(label);
        slot.setFont(new Font("slots", Font.BOLD, 24));
        slot.setForeground(Color.WHITE);
        add(slot,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays Jbutton accessories
    public void inventorySlots(Inventory inv) {
        for (Accessory a : inv.getInventorySlots()) {
            JButton b = generateAccessory(a.accessoryId);
            add(formattedEquipmentButton(b,a, false),constraints);
            constraints.gridx += 1;
        }
    }

    // MODIFIES: this
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
            BufferedImage fileImage = ImageIO.read(new File("./res/images/accessories/"
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
        constraints.gridwidth = 2;
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
        constraints.gridwidth = 1;
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
        mapHeader();
        ButtonGroup reachedRounds = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            try {
                Monster m = frame.getJsonReader().reconstructMonster(i, world.getDifficulty());
                if (i <= world.getRound()) {
                    JRadioButton battleButton = generateBattleButton(i, m);
                    if (i == 1) {
                        battleButton.setSelected(true);
                    }
                    add(battleButton, constraints);
                    reachedRounds.add(battleButton);
                } else {
                    add(generateBattleLabel(i, m), constraints);
                }
                constraints.gridy += 1;
            } catch (IOException e) {
                System.out.println("Failed to load Monster for round" + i);
                e.printStackTrace();
            }
        }
        enterBattleButton();
    }

    // MODIFIES: this
    // EFFECTS: Displays a header for the map portion of panel
    public void mapHeader() {
        JLabel title = new JLabel("Battle Grounds");
        JLabel difficulty = new JLabel("Difficulty: " + world.getDifficulty());
        JLabel round = new JLabel("Round: " + world.getRound() + "/5");

        constraints.gridy = 0;
        add(formattedMapHeaderLabel(title),constraints);
        constraints.gridy += 1;
        add(formattedMapHeaderLabel(difficulty),constraints);
        constraints.gridy += 1;
        add(formattedMapHeaderLabel(round),constraints);
        constraints.gridy += 1;
    }

    // EFFECTS: Returns a JLabel reformatted
    public JLabel formattedMapHeaderLabel(JLabel label) {
        label.setForeground(new Color(154, 66, 234));
        label.setFont(new Font("mapHeader", Font.PLAIN, 24));

        return label;
    }

    // MODIFIES: this
    // EFFECTS: Displays the button to enter a battle
    public void enterBattleButton() {
        JButton b = new JButton("Fight!");
        b.setBackground(Color.WHITE);
        b.setForeground(Color.red);
        b.setFont(new Font("fight", Font.BOLD, 24));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBattle();
            }
        });

        add(b,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Begins the battle at selected round
    public void startBattle() {
        hero.recover();
        Monster monster = null;
        try {
            monster = frame.getJsonReader().reconstructMonster(selectedRound,world.getDifficulty());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load monster");
        }
        JLabel monsterImage = generateMonsterImage(monster.getName());
        frame.remove(this);
        frame.add(new BattlePanel(world, getWidth(), getHeight(),hero, monster, monsterImage,
                heroImage, selectedRound, frame));
        frame.pack();
    }

    // EFFECTS: Returns an image of the given monster as a JLabel
    public JLabel generateMonsterImage(String name) {
        JLabel monster = null;
        try {
            BufferedImage fileImage = ImageIO.read(new File("./res/images/monsters/"
                    + name + ".png"));
            monster = new JLabel(new ImageIcon(fileImage));
            monster.setBackground(new Color(161, 95, 227));
            monster.setOpaque(true);
        } catch (IOException e) {
            System.out.println("Monster image not loading");
            e.printStackTrace();
        }
        return monster;
    }

    // MODIFIES: this
    // EFFECTS: Returns a radio button for given round
    public JRadioButton generateBattleButton(int round, Monster m) {
        JRadioButton b = new JRadioButton(m.getName());
        b.setBackground(Color.DARK_GRAY);
        b.setForeground(Color.WHITE);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRound = round;
            }
        });
        return b;
    }

    // MODIFIES: this
    // EFFECTS: Returns a Label for given round
    public JLabel generateBattleLabel(int round, Monster m) {
        JLabel battleLabel = new JLabel(m.getName());
        battleLabel.setBackground(Color.DARK_GRAY);
        battleLabel.setForeground(Color.lightGray);

        return battleLabel;
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
        frame.add(new MainMenuPanel(frame.getWorlds(), getWidth(), getHeight(),frame));
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: Reloads the world panel with changed data
    public void updatePanel() {
        frame.remove(this);
        frame.add(new WorldPanel(world, getWidth(),getHeight(),frame));
        frame.pack();
    }

}
