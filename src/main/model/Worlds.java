package model;

import java.util.ArrayList;
import java.util.List;

public class Worlds {
    private static int max_worlds = 3;

    protected List<World> worlds;

    public Worlds() {
        this.worlds = new ArrayList<World>();
    }

    // REQUIRES: worlds list to not be full
    // MODIFIES: this
    // EFFECTS: Creates a new world with given attributes
    protected void createWorld(String worldName, String heroName,
                                String heroClass, String difficulty) {
        World newWorld = new World(worldName, heroName, heroClass, difficulty);
        worlds.add(newWorld);
    }

    // MODIFIES: this
    // EFFECTS: Deletes the given world permenantly
    protected void deleteWorld(World w) {
        //stub
    }

    // EFFECTS: Checks if worlds list is full returns true if full
    //          othewise false
    protected Boolean worldsFull() {
        return this.worlds.size() >= this.max_worlds;
    }

    // MODIFIES: this
    // EFFECTS: Changes the difficulty of given world
    protected void changeDifficulty(World w, String newDifficulty) {
        w.changeDifficulty(newDifficulty);
    }
}
