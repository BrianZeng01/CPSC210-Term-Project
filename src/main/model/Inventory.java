package model;

import model.items.Accessory;
import model.items.Bracelet;
import model.items.Necklace;
import model.items.Ring;

// Basic Inventory system for character
public class Inventory {
    public int healthPotions;
    public int manaPotions;
    public Accessory slot1;
    public Accessory slot2;
    public Accessory slot3;
    public Necklace necklace;
    public Ring ring;
    public Bracelet bracelet;

    // EFFECTS: Creates a new inventory with all empty slots except for
    //          5 health and mana potions
    public Inventory() {
        this.healthPotions = 5;
        this.manaPotions = 5;
        this.slot1 = null;
        this.slot2 = null;
        this.slot3 = null;
        this.necklace = null;
        this.bracelet = null;
        this.ring = null;
    }

}
