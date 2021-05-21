package ui;

import model.Worlds;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// The frame and main container of the game
public class MyGame extends JFrame {
    private Worlds worlds;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    private static final String DATA_SOURCE = "./data/application.json";
    private static final int DEFAULT_WIDTH = 1400;
    private static final int DEFAULT_HEIGHT = 900;

    // EFFECTS: Starts the application
    public MyGame() {
        setTitle("Fractured Forest");
        setMinimumSize(new Dimension(1400,750));
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
        JPanel panel = new MainMenuPanel(worlds,DEFAULT_WIDTH,DEFAULT_HEIGHT,this);
        add(panel);
        pack();
        setVisible(true);
    }

    // EFFECTS: Exits the game after saving
    public void saveAndExit() {
        save();
        System.exit(0);

    }

    // MODIFIES: this
    // EFFECTS: Saves the state of the game
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(worlds);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Data could not be saved!");
            e.printStackTrace();
        }
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public Worlds getWorlds() {
        return worlds;
    }
}
