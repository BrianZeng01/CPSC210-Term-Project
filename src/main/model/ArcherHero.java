package model;


// Archer hero
public class ArcherHero extends Hero {

    // EFFECTS: Creates new Archer hero with base agility 5
    //          ,archer class, and given name
    public ArcherHero(String name) {
        super(name);
        this.agility = SPECIAL_BASE_STAT;
        this.heroClass = "archer";
    }
}
