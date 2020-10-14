package ui;

import model.Hero;
import model.World;

import java.util.ArrayList;
import java.util.List;

// Main interface of the application
public class App {
    private List<World> worlds = new ArrayList<World>();

    public App() {
        // Sample world for purpose of console application
        World testWorld = new World("Test World",
                "TestHero", "warrior", "easy", 1);
        Hero testHero = testWorld.getHero();
        testHero.levelUp();
        this.worlds.add(testWorld);
        runApp();
    }

    public void runApp() {

        while (true) {
            mainMenu();
            break;
        }

        System.out.println("Exiting Application");
    }

    public void mainMenu() {

    }
}
