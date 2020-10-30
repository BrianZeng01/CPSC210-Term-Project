package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents all the worlds that user has
public class Worlds implements Writable {
    private static final int MAX_WORLDS = 3;

    protected List<World> worlds;
    protected List<Integer> usedWorldNumbers;

    // EFFECTS: Constructs a new worlds object without
    //          any worlds or used world numbers
    public Worlds() {
        this.worlds = new ArrayList<World>();
        this.usedWorldNumbers = new ArrayList<Integer>();
    }

    // REQUIRES: worlds list to not be full
    // MODIFIES: this
    // EFFECTS: Creates a new world with given attributes, returns true
    //          if world created, otherwise it's full and return false.
    //          The world will have a unique worldNumber.
    public Boolean createWorld(String worldName, String heroName,
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
    public Boolean deleteWorld(int worldNumber) {
        World deletedWorld = this.getWorld(worldNumber);
        if (deletedWorld == null) {
            return false;
        }
        this.usedWorldNumbers.remove(Integer.valueOf(worldNumber));
        this.worlds.remove(deletedWorld);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Adds given world to worlds, world generated from saved files
    public void addWorld(World w) {
        this.worlds.add(w);
        this.usedWorldNumbers.add(Integer.valueOf(w.getWorldNumber()));
    }

    // EFFECTS: Checks if worlds list is full returns true if full
    //          otherwise false
    public Boolean worldsIsFull() {
        return this.getNumberOfWorlds() >= this.MAX_WORLDS;
    }

    // REQUIRES: world with given number must exist
    // EFFECTS: Returns the world with given world number
    public World getWorld(int worldNumber) {
        World world = null;
        for (World w :this.worlds) {
            if (w.getWorldNumber() == worldNumber) {
                world = w;
                break;
            }
        }
        return world;
    }

    public List<World> getWorlds() {
        return this.worlds;
    }

    public List<Integer> getUsedWorldNumbers() {
        return this.usedWorldNumbers;
    }

    public int getNumberOfWorlds() {
        return this.worlds.size();
    }

    public int getMaxWorlds() {
        return this.MAX_WORLDS;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonWorlds = new JSONObject();
        for (World w: worlds) {
            String worldNumber = Integer.toString(w.getWorldNumber());
            JSONObject jsonWorld = w.toJson();
            jsonWorlds.put(worldNumber, jsonWorld);
        }

        return jsonWorlds;
    }
}
