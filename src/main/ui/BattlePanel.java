package ui;

import model.*;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents the battlefield
public class BattlePanel extends JPanel {
    private static final String MONSTERS_FILE = "./data/monsters.json";
    private World world;
    private JsonReader jsonReader;
    private Hero hero;
    private MyGame frame;
    protected GridBagConstraints constraints;
    protected int selectedRound;
    protected JLabel heroImage;

    // EFFECTS: Constructs the battlefield with given inputs
    public BattlePanel(World world, int width, int height, JLabel heroImage,int round, MyGame frame) {
        this.world = world;
        this.heroImage = heroImage;
        this.hero = world.getHero();
        this.frame = frame;
        this.jsonReader = new JsonReader(MONSTERS_FILE);
        this.constraints = new GridBagConstraints();
        this.selectedRound = round;
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        init();
    }

    // MODIFIES: this
    // EFFECTS: Generates the background of the battlefield
    @Override
    protected void paintComponent(Graphics g) {
        try {
            BufferedImage fileImage = ImageIO.read(new File("./data/images/battlefieldBackground.png"));
            ImageIcon background = new ImageIcon(fileImage);
            Image backgroundImage = background.getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
            super.paintComponent(g);
            g.drawImage(backgroundImage,0,0,null);

        } catch (IOException e) {
            System.out.println("Background image not loading");
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Initialzies the battlefield with components
    public void init() {

    }

    // MODIFIES: this
    // EFFECTS: Returns back to the world panel
    public void backToWorldPanel() {
        frame.remove(this);
        frame.add(new WorldPanel(world,getWidth(),getHeight(),frame));
        frame.pack();
    }
}
