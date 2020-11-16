package ui;

import model.*;
import org.json.JSONArray;
import persistence.JsonReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

// Represents the battlefield
public class BattlePanel extends JPanel {
    private static final String MONSTERS_FILE = "./data/monsters.json";
    private World world;
    private Hero hero;
    private Monster monster;
    private MyGame frame;
    protected GridBagConstraints constraints;
    protected int selectedRound;
    protected JLabel heroImage;
    protected JLabel monsterImage;

    // EFFECTS: Constructs the battlefield with given inputs
    public BattlePanel(World world, int width, int height,Hero hero, Monster monster, JLabel monsterImage,
                       JLabel heroImage, int round, MyGame frame) {
        this.world = world;
        this.heroImage = heroImage;
        this.monsterImage = monsterImage;
        this.hero = hero;
        this.monster = monster;
        this.frame = frame;
        this.constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = constraints.BOTH;
        this.selectedRound = round;
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        init();
    }

    // MODIFIES: this
    // EFFECTS: Initialzies the battlefield with components
    public void init() {
        headerDisplay();
        battleDisplay();
        actionsDisplay();
    }

    // MODIFIES: this
    // EFFECTS: Displays health,mana, and name of both entities, also displays battle log
    public void headerDisplay() {
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(formattedHeaderLabel(hero.getName()),constraints);
        constraints.gridy = 1;
        add(formattedHeaderLabel("HP: " + Integer.toString(hero.getHealth())),constraints);
        constraints.gridy = 2;
        add(formattedHeaderLabel("MP: " + Integer.toString(hero.getMana())),constraints);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(formattedHeaderLabel(monster.getName()),constraints);
        constraints.gridy = 1;
        add(formattedHeaderLabel(Integer.toString(monster.getHealth()) + " :HP"),constraints);
        constraints.gridy = 2;
        add(formattedHeaderLabel("0 :MP"),constraints);
    }

    // EFFECTS: Formats the header labels and returns them
    public JLabel formattedHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.DARK_GRAY);
        label.setOpaque(true);
        label.setFont(new Font("headerLabel", Font.PLAIN, 24));
        return label;
    }

    // EFFECTS: Displays the 2 entities;
    public void battleDisplay() {
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridheight = 12;
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(heroImage,constraints);
        constraints.gridx = 4;
        add(monsterImage, constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays all actions hero can take
    public void actionsDisplay() {
        constraints.gridx = 0;
        constraints.gridy = 15;
        constraints.gridheight = 3;
        constraints.gridwidth = 1;
        add(attackButton("0","basic attack"),constraints);
        constraints.gridx = 1;
        add(attackButton("1","1st skill:" + hero.getFirstSkillManaCost()
                + "MP CD(" + hero.getFirstSkillCoolDown() + ")"),constraints);
        constraints.gridx = 2;
        add(attackButton("2","2nd skill:" + hero.getSecondSkillManaCost()
                + "MP CD(" + hero.getSecondSkillCoolDown() + ")"),constraints);
        constraints.gridx = 3;
        add(attackButton("3","3rd skill:" + hero.getThirdSkillManaCost()
                + "MP CD(" + hero.getThirdSkillCoolDown() + ")"),constraints);
        constraints.gridx = 4;
        add(drinkHealthButton(),constraints);
        constraints.gridx = 5;
        add(drinkManaButton(),constraints);
        constraints.gridwidth = 2;
        constraints.gridx = 6;
        add(fleeButton(), constraints);
    }

    // EFFECTS: Returns a button corresponding to the given attack input
    public JButton attackButton(String attack, String text) {
        JButton b = new JButton(text);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.lightGray);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (attack.equals("0")) {
                    basicAttack();
                } else if (attack.equals("1") && tryFirstSkill()) {
                    firstSkill();
                } else if (attack.equals("2") && trySecondSkill()) {
                    secondSkill();
                } else if (attack.equals("3") && tryThirdSkill()) {
                    thirdSkill();
                }
            }
        });

        return b;
    }

    // EFFECTS: Uses the basic attack then calls nextTurn
    public void basicAttack() {
        int damage = hero.basicAttack();
        nextTurn(damage);
    }

    // EFFECTS: Uses the first skill then calls nextTurn
    public void firstSkill() {
        int damage = hero.firstSkill();
        nextTurn(damage);
    }

    // EFFECTS: Uses the second skill then calls nextTurn
    public void secondSkill() {
        int damage = hero.secondSkill();
        nextTurn(damage);
    }

    // EFFECTS: Uses the third skill then calls nextTurn
    public void thirdSkill() {
        int damage = hero.thirdSkill();
        nextTurn(damage);
    }

    // EFFECTS: Returns JButton that Drinks a health potion if available
    public JButton drinkHealthButton() {
        int numPotions = hero.getInventory().getHealthPotions();
        JButton b = new JButton("HP Potion: " + numPotions);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numPotions > 0) {
                    hero.drinkHealthPotion();
                    nextTurn(0);
                }
            }
        });
        return  b;
    }

    // EFFECTS: Returns JButton that Drinks a mana potion if available
    public JButton drinkManaButton() {
        int numPotions = hero.getInventory().getManaPotions();
        JButton b = new JButton("MP Potion: " + numPotions);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numPotions > 0) {
                    hero.drinkManaPotion();
                    nextTurn(0);
                }
            }
        });
        return  b;
    }

    // EFFECTS: Returns a JButton that flees the battle
    public JButton fleeButton() {
        JButton b = new JButton("Flee!");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToWorld();
            }
        });

        return b;
    }

    // EFFECTS: Returns true if user can use firstSkill
    public Boolean tryFirstSkill() {
        return hero.getLevel() >= hero.getFirstSkillLevelRequirement()
                && hero.getFirstSkillCoolDown() == 0
                && hero.getMana() >= hero.getFirstSkillManaCost();
    }

    // EFFECTS: Returns true if user can use secondSkill
    public Boolean trySecondSkill() {
        return hero.getLevel() >= hero.getSecondSkillLevelRequirement()
                && hero.getSecondSkillCoolDown() == 0
                && hero.getMana() >= hero.getSecondSkillManaCost();
    }

    // EFFECTS: Returns true if user can use thirdSkill
    public Boolean tryThirdSkill() {
        return hero.getLevel() >= hero.getThirdSkillLevelRequirement()
                && hero.getThirdSkillCoolDown() == 0
                && hero.getMana() >= hero.getThirdSkillManaCost();
    }

    // MODIFIES: this
    // EFFECTS: Reloads panel after a turn;
    public void nextTurn(int heroDamage) {
        monster.takeDamage(heroDamage);
        if (monster.isDead()) {
            victory();
            return;
        }
        hero.takeDamage(monster.basicAttack());
        takeDamageSound();
        if (hero.isDead()) {
            backToWorld();
        } else {
            hero.decreaseCoolDowns();
            reloadPanel();
        }
    }

    // Tutorial from:  https://www.youtube.com/watch?v=qPVkRtuf9CQ
    // EFFECTS: Plays the sound of hero taking damage
    public void takeDamageSound() {
        try {
            String filePath = "./res/sounds/jab.wav";
            File file = new File(filePath);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch (Exception e) {
            System.out.println("sound error");
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Picks up loot from monster and experience, then returns to world menu
    public void victory() {
        nextRound();
        Inventory inv = hero.getInventory();
        List<Integer> loot = monster.dropLoot();
        hero.gainExp(monster.getExp());
        inv.setHealthPotions(inv.getHealthPotions() + loot.get(0));
        inv.setManaPotions(inv.getManaPotions() + loot.get(1));
        if (loot.get(2) != -1) {
            if (inv.inventorySlotsIsFull()) {
                backToWorld();
                return;
            }
            JSONArray arr = new JSONArray();
            arr.put(Integer.toString(loot.get(2)));
            try {
                Accessory a = frame.getJsonReader().convertIds(arr).get(0);
                inv.pickUpAccessory(a);
            } catch (IOException e) {
                System.out.println("Failed to generate an accessory upon victory.");
                e.printStackTrace();
            }
        }
        backToWorld();
        return;
    }

    // MODIFIES: this
    // EFFECTS: Increases round progress if this is the correct round
    public void nextRound() {
        if (selectedRound == world.getRound()) {
            world.nextRound();
        }
    }

    // MODIFIES: this
    // EFFECTS: Refreshes the panel with new data
    public void reloadPanel() {
        frame.remove(this);
        frame.add(new BattlePanel(world,getWidth(),getHeight(),hero,monster,
                monsterImage,heroImage,selectedRound, frame));
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: Returns back to the world panel
    public void backToWorld() {
        frame.remove(this);
        frame.add(new WorldPanel(world,getWidth(),getHeight(),frame));
        frame.save();
        frame.pack();
    }
}
