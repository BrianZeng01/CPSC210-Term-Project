package model;


// Mage hero
public class MageHero extends Hero {

    // EFFECTS: Creates new mage hero with base intelligence 5
    public MageHero(String name) {
        super(name);
        this.intelligence = SPECIAL_BASE_STAT;
    }
}
