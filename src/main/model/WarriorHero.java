package model;


// Warrior hero
public class WarriorHero extends Hero {

    // EFFECTS: Creates new warrior hero with base defence 5
    //          ,warrior class, and given name
    public WarriorHero(String name) {
        super(name);
        this.defence = SPECIAL_BASE_STAT;
        this.heroClass = "warrior";
    }
}
