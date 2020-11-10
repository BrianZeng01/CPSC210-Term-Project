package ui;

import model.World;
import model.Worlds;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// The frame and main container of the game
public class MyGame extends JFrame {
    private Worlds worlds;
    private MainMenuPanel panel;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    private static final String DATA_SOURCE = "./data/application.json";
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 900;

    // EFFECTS: Starts the application
    public MyGame() {
        setTitle("Cool name");
        setMinimumSize(new Dimension(600,450));
        setLayout(new BorderLayout());
        jsonWriter = new JsonWriter(DATA_SOURCE);
        jsonReader = new JsonReader(DATA_SOURCE);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: Loads up saved data and starts menu
    public void runGame() {
        try {
            worlds = jsonReader.read();
        } catch (IOException e) {
            System.out.print("Data files could not be read");
            e.printStackTrace();
            return;
        }

        init();

    }

    // MODIFIES: this
    // EFFECTS: Displays the main menu
    public void init() {
        panel = new MainMenuPanel(worlds,DEFAULT_WIDTH,DEFAULT_HEIGHT,this);
        add(panel.getPanel());
        pack();
        setVisible(true);
    }

    public void saveAndExit() {
        try {
            jsonWriter.open();
            jsonWriter.write(worlds);
            jsonWriter.close();
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Data could not be saved!");
            e.printStackTrace();
        }
    }

    public Worlds getWorlds() {
        return worlds;
    }
}
