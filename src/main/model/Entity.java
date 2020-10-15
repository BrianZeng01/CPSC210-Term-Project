package model;

// Abstraction of entities in the game such as monster and hero
public abstract class Entity {

    protected int attackMultiplier;
    public String name;
    public int health;
    public int strength;

    // EFFECTS: Entity takes damage, calculated differently
    //          for different entities
    public abstract Boolean takeDamage(int damage);

    // EFFECTS: The basic attack of entites, different calculations
    public abstract int basicAttack();

    // EFFECTS: Checks if Character is dead, returns true if dead, otherwise false
    public Boolean isDead() {
        return this.getHealth() <= 0;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getAttackMultiplier() {
        return this.attackMultiplier;
    }
}
