package model;


// Archer hero
public class ArcherHero extends Hero {

    // EFFECTS: Creates new Archer hero with base agility 5
    public ArcherHero(String name) {
        super(name);
        this.agility = 5;
    }
}
