package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// A monster in the game
public class Monster extends Entity implements Writable {
    public int lootChance;
    public int exp;

    // EFFECTS: Creates a monster with given name, health,
    //          strength, and sets attackMultiplier.
    public Monster(String name, int health, int strength, int lootChance, int exp) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.attackMultiplier = 3;
        this.lootChance = lootChance;
        this.exp = exp;
    }

    // MODIFIES: this
    // EFFECTS: Decrease health by based on damage
    // and return true if character still alive, otherwise false
    public Boolean takeDamage(int damage) {
        this.health -= damage;
        return !this.isDead();
    }

    // EFFECTS: returns a value calculated by it's strength
    public int basicAttack() {
        return this.getAttackMultiplier() * this.getStrength();
    }

    // EFFECTS: [Hp Potion, Mp Potion, accessoryId] In order, the integers will represent
    //          how many hp potions, mp potions, and accessory id dropped.
    //          note that a -1 accessoryId means no accessory dropped.
    public List<Integer> dropLoot() {
        Integer hpPotions = (int) Math.round(Math.random() * (1 + getLootChance()));
        Integer mpPotions = (int) Math.round(Math.random() * (1 + getLootChance()));
        int highestAccessoryId = 5;
        int lowestAccessoryId = 1;
        Integer accessoryId;
        // AccessoryIds are subject to change when new items added
        if ((Math.random() * 10) < getLootChance()) {
            accessoryId = (int) Math.round(Math.random()
                    * (highestAccessoryId - lowestAccessoryId) + lowestAccessoryId);
        } else {
            accessoryId = -1;
        }
        List<Integer> arr = new ArrayList<>();
        arr.add(hpPotions);
        arr.add(mpPotions);
        arr.add(accessoryId);
        return arr;
    }

    public int getExp() {
        return this.exp;
    }

    public int getLootChance() {
        return lootChance;
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
