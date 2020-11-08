package ui;

import model.World;
import model.Worlds;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MyGame extends JFrame {
    private Worlds worlds;
    private MainMenuPanel panel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String DATA_SOURCE = "./data/application.json";
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 900;

    // EFFECTS: Starts the application
    public MyGame() {
        setTitle("Cool name");
        setMinimumSize(new Dimension(600,450));
        jsonWriter = new JsonWriter(DATA_SOURCE);
        jsonReader = new JsonReader(DATA_SOURCE);
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: Acts as a user interface in the console to
    //          process inputs.
    public void runGame() {
        try {
            worlds = jsonReader.read();
        } catch (IOException e) {
            System.out.print("Data files could not be read");
            e.printStackTrace();
            return;
        }

        mainMenu();

    }

    public void mainMenu() {
        panel = new MainMenuPanel(worlds,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        add(panel.getPanel());
        setVisible(true);
        pack();

    }


    public void addGamePanel() {
    }
}
