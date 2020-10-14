package model;

// Abstract class for accessories
public class Accessory {
    public String accessoryName;
    public int addedStrength;
    public int addedDefence;
    public int addedAgility;
    public int addedIntelligence;

    // EFFECTS: Constructs accessory with name and given attributes
    public Accessory(String name, int strength,
                     int defence, int agility, int intelligence) {
        this.accessoryName = name;
        this.addedStrength = strength;
        this.addedDefence = defence;
        this.addedAgility = agility;
        this.addedIntelligence = intelligence;
    }

    public String getAccessoryName() {
        return this.accessoryName;
    }

    public int getAddedStrength() {
        return this.addedStrength;
    }

    public int getAddedDefence() {
        return this.addedDefence;
    }

    public int getAddedAgility() {
        return this.addedAgility;
    }

    public int getAddedIntelligence() {
        return this.addedIntelligence;
    }

}
