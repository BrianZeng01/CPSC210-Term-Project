package model;


// Warrior hero
public class WarriorHero extends Hero {

    // EFFECTS: Creates new warrior hero with base defence 5
    public WarriorHero(String name) {
        super(name);
        this.defence = 5;
    }
}
