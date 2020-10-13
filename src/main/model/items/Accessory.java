package model.items;

// Abstract class for accessories
public abstract class Accessory {
    public String accessoryName;
    public int addedStrength;
    public int addedDefence;
    public int addedAgility;
    public int addedIntelligence;

    // EFFECTS: Constructs accessory
    public Accessory(String name, int strength,
                     int defence, int agility, int intelligence) {
        this.accessoryName = name;
        this.addedStrength = strength;
        this.addedDefence = defence;
        this.addedAgility = agility;
        this.addedIntelligence = intelligence;
    }

}
