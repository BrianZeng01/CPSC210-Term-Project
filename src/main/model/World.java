package model;

import org.json.JSONObject;
import persistence.Writable;

// A world for the application
public class World implements Writable {
    protected int worldNumber;
    protected Hero hero;
    protected String difficulty;
    protected int round;
    protected final String worldName;

    // EFFECTS: Constructs a world with a name, a hero of given heroClass,
    //          and heroName. Sets given difficulty and starts at round 1.
    public World(String worldName, String heroName,
                 String heroClass, String difficulty, int worldNumber) {
        if (heroClass.equals("warrior")) {
            this.hero = new WarriorHero(heroName);
        } else if (heroClass.equals("archer")) {
            this.hero = new ArcherHero(heroName);
        } else {
            this.hero = new MageHero(heroName);
        }
        this.worldNumber = worldNumber;
        this.difficulty = difficulty;
        this.worldName = worldName;
        this.round = 1;
    }

    // MODIFIES: this
    // EFFECTS: Change's the difficulty of the World
    public void changeDifficulty(String newDifficulty) {
        this.difficulty = newDifficulty;
    }

    // MODIFIES: this
    // EFFECTS: Moves onto next round by incrementing round by one
    public void nextRound() {
        this.round++;
    }

    public Hero getHero() {
        return this.hero;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getRound() {
        return this.round;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public int getWorldNumber() {
        return this.worldNumber;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonWorld = new JSONObject();
        JSONObject worldData = new JSONObject();
        JSONObject heroData = this.getHero().toJson();
        JSONObject inventoryData = this.getHero().getInventory().toJson();

        worldData.put("worldNumber", this.getWorldNumber());
        worldData.put("worldName", this.getWorldName());
        worldData.put("difficulty", this.getDifficulty());
        worldData.put("round", this.getRound());

        jsonWorld.put("worldData", worldData);
        jsonWorld.put("heroData", heroData);
        jsonWorld.put("inventoryData", inventoryData);
        return jsonWorld;
    }
}
