package model;

import com.sun.org.apache.bcel.internal.generic.RETURN;

// Abstract for possible Characters
public abstract class Hero {

    // Checkstyle confusion on final field
    private final int baseStats = 1;
    private final int baseHealthAndMana = 100;
    private final int healthAndManaPotionValue = 25;
    private final int maxHealthAndManaIncrement = 10;
    private final int skillPointsGrantedPerLevel = 5;
    protected final int specialBaseStat = 5;
    protected String name;
    protected Inventory inventory;
    protected int maxHealth;
    protected int health;
    protected int maxMana;
    protected int mana;
    protected int level;
    protected int skillPoints;
    protected int strength;
    protected int defence;
    protected int agility;
    protected int intelligence;

    // EFFECTS: Construct a hero with given name and base stats
    public Hero(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.maxHealth = baseHealthAndMana;
        this.maxMana = baseHealthAndMana;
        this.health = baseHealthAndMana;
        this.mana = baseHealthAndMana;
        this.level = 1;
        this.skillPoints = 0;
        this.strength = baseStats;
        this.defence = baseStats;
        this.agility = baseStats;
        this.intelligence = baseStats;
    }

    // MODIFIES: this
    // EFFECTS: Increase level by one and grants 5 skill points
    public void levelUp() {
        this.level++;
        this.skillPoints += this.skillPointsGrantedPerLevel;
        this.maxHealth += this.maxHealthAndManaIncrement;
        this.maxMana += this.maxHealthAndManaIncrement;
    }

    // MODIFIES: this
    // EFFECTS: Decrease health by damage and return true if character
    //          still alive, otherwise false
    public Boolean takeDamage(int damage) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Decrease mana by manaNeeded if sufficient mana and return true,
    //          otherwise false.
    public Boolean spendMana(int manaNeeded) {
        return false;
    }

    // REQUIRES: Must have mana potion in inventory
    // MODIFIES: this
    // EFFECTS: Increments mana by healthAndManaPotion value, removes one mana
    //          potion from inventory, and returns true. If no mana potions
    //          in inventory returns false.
    public Boolean drinkManaPotion() {
        return true;
    }

    // REQUIRES: Must have health potion in inventory
    // MODIFIES: this
    // EFFECTS: Increments health by healthAndManaPotion value, removes one health
    //          potion from inventory, and returns true. If no health potions
    //          in inventory returns false.
    public Boolean drinkHealthPotion() {
        return true;
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

    public int getSkillPoints() {
        return this.skillPoints;
    }

    public int getSkillPointsGrantedPerLevel() {
        return this.skillPointsGrantedPerLevel;
    }

    public int getSpecialBaseStat() {
        return this.specialBaseStat;
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

    public int getStrength() {
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

    public int getBaseStats() {
        return this.baseStats;
    }

    public int getBaseHealthAndMana() {
        return this.baseHealthAndMana;
    }

    public int getHealthAndManaPotionValue() {
        return this.healthAndManaPotionValue;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getMaxHealthAndManaIncrement() {
        return this.maxHealthAndManaIncrement;
    }
}
