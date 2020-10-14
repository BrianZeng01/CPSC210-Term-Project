package model;

import java.util.ArrayList;
import java.util.List;

public class Worlds {
    private static final int MAX_WORLDS = 3;

    protected List<World> worlds;
    protected List<Integer> usedWorldNumbers;

    public Worlds() {
        this.worlds = new ArrayList<World>();
        this.usedWorldNumbers = new ArrayList<Integer>();
    }

    // REQUIRES: worlds list to not be full
    // MODIFIES: this
    // EFFECTS: Creates a new world with given attributes, returns true
    //          if world created, otherwise it's full and return false
    protected Boolean createWorld(String worldName, String heroName,
                                String heroClass, String difficulty) {
        if (worldsIsFull()) {
            return false;
        }

        int worldNumber = 1;
        while (this.usedWorldNumbers.contains(worldNumber)) {
            worldNumber++;
        }

        this.usedWorldNumbers.add(Integer.valueOf(worldNumber));
        World newWorld = new World(worldName, heroName, heroClass, difficulty, worldNumber);
        worlds.add(newWorld);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Deletes the given world permenantly and removes it's
    //          world number from usedWorldNumbers, returns true if deleted
    //          otherwise false.
    protected Boolean deleteWorld(int worldNumber) {
        World deletedWorld = this.getWorld(worldNumber);
        if (deletedWorld == null) {
            return false;
        }
        this.usedWorldNumbers.remove(Integer.valueOf(2));
        this.worlds.remove(deletedWorld);
        return true;
    }

    // EFFECTS: Checks if worlds list is full returns true if full
    //          otherwise false
    protected Boolean worldsIsFull() {
        return this.getNumberOfWorlds() >= this.MAX_WORLDS;
    }

    // REQUIRES: world with given number must exist
    // EFFECTS: Returns the world with given world number
    protected World getWorld(int worldNumber) {
        World world = null;
        for (World w :this.worlds) {
            if (w.getWorldNumber() == worldNumber) {
                world = w;
                break;
            }
        }
        return world;
    }

    protected int getNumberOfWorlds() {
        return this.worlds.size();
    }

    protected int getMaxWorlds() {
        return this.MAX_WORLDS;
    }
}
