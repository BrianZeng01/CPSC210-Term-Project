package model;


// Mage hero
public class MageHero extends Hero {

    // EFFECTS: Creates new mage hero with base intelligence 5
    //          ,mage class, and given name
    public MageHero(String name) {
        super(name);
        this.intelligence = SPECIAL_BASE_STAT;
        this.heroClass = "mage";
    }
}