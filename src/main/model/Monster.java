package model;

public class Monster extends Entity {

    public Monster(String name, int health, int strength) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.attackMultiplier = 3;
    }

    // MODIFIES: this
    // EFFECTS: Decrease health by based on damage
    // and return true if character still alive, otherwise false
    public Boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.isDead()) {
            return false;
        }
        return true;
    }

    // EFFECTS: returns a value calculated by it's strength
    public int basicAttack() {
        return this.getAttackMultiplier() * this.getStrength();
    }


}
