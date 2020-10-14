package model;

// A world for the application
public class World {
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
    protected void nextRound() {
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
}