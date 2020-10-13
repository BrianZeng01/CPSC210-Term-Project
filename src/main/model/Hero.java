package model;

// Abstract for possible Characters
public abstract class Hero {

    protected String name;
    protected Inventory inventory;
    protected int health;
    protected int mana;
    protected int level;
    protected int skillPoints;
    protected int strength;
    protected int defence;
    protected int agility;
    protected int intelligence;

    public Hero(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.level = 1;
        this.skillPoints = 0;
        this.strength = 1;
        this.defence = 1;
        this.agility = 1;
        this.intelligence = 1;
    }

    // MODIFIES: this
    // EFFECTS: Increase level by one and grants 5 skill points
    public void levelUp() {
        this.level++;
        this.skillPoints += 5;
    }

    // EFFECTS: Checks if Character is dead, returns true if dead, otherwise false
    public Boolean isDead() {
        return this.health > 0;
    }

    // EFFECTS: Returns true if character has skill points, else false.
    public Boolean hasSkillPoints() {
        return this.skillPoints > 0;
    }

    public void increaseStrength() {
        this.strength++;
    }

    public void increaseDefence() {
        this.defence++;
    }

    public void increaseAgility() {
        this.agility++;
    }

    public void increaseIntelligence() {
        this.intelligence++;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getMana() {
        return this.mana;
    }

    public int getHealth() {
        return this.health;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public int getStrenght() {
        return this.strength;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getAgility() {
        return this.agility;
    }

    public int getIntelligence() {
        return this.intelligence;
    }
}
