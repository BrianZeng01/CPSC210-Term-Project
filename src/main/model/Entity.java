package model;

public abstract class Entity {

    protected int attackMultiplier;
    public String name;
    public int health;
    public int strength;

    public abstract Boolean takeDamage(int damage);

    public abstract int basicAttack();

    // EFFECTS: Checks if Character is dead, returns true if dead, otherwise false
    public Boolean isDead() {
        return this.getHealth() <= 0;
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
